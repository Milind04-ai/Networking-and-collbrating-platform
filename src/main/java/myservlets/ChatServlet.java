package myservlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import myclasses.Message;
import myutil.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import myclasses.ChatUser;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if(session == null ||
           session.getAttribute("userId") == null){

            response.sendRedirect(
                request.getContextPath()
                + "/login");

            return;
        }
        
        String statusText = "Offline";
        
        String userId =
        session.getAttribute("userId")
               .toString();

        String receiverId =
                request.getParameter(
                        "receiverId");
        
        String groupId =
    request.getParameter("groupId");
        
        String receiverName = "";

        request.setAttribute(
                "receiverId",
                receiverId);
        
        Connection con =
        DBConnection.getConnection();
        
        String userSql =
        "SELECT full_name " +
        "FROM users " +
        "WHERE id=?";

PreparedStatement userPs;
        try {
            
                    if(groupId != null){
            
            String groupName = "";

String groupSql =
    "SELECT name " +
    "FROM group_chats " +
    "WHERE id=?";

PreparedStatement groupPs =
    con.prepareStatement(groupSql);

groupPs.setString(1, groupId);

ResultSet groupRs =
    groupPs.executeQuery();

if(groupRs.next()){

    groupName =
        groupRs.getString("name");
}

request.setAttribute(
    "groupName",
    groupName);

    request.setAttribute(
        "groupId",
        groupId);

request.setAttribute(
        "currentUserId",
        userId);

ArrayList<Message> messages =
        new ArrayList<>();

String msgSql =
"SELECT gm.*,u.full_name " +
"FROM group_messages gm " +
"JOIN users u " +
"ON gm.sender_id=u.id " +
"WHERE gm.chat_id=? " +
"ORDER BY gm.sent_at";

PreparedStatement msgPs =
        con.prepareStatement(msgSql);

msgPs.setString(1, groupId);

ResultSet msgRs =
        msgPs.executeQuery();

while(msgRs.next()){

    Message m =
            new Message();

    m.setSenderId(
            msgRs.getString(
                    "sender_id"));

    m.setSenderName(
            msgRs.getString(
                    "full_name"));

    m.setContent(
            msgRs.getString(
                    "content"));

    m.setSentAt(
            msgRs.getString(
                    "sent_at"));

    messages.add(m);
}

request.setAttribute(
        "messages",
        messages);

request.setAttribute(
        "currentUserId",
        userId);

request.getRequestDispatcher(
        "/myjsp/chat.jsp")
        .forward(request,response);

return;
}
            userPs = con.prepareStatement(userSql);
        

            userPs.setString(1, receiverId);

            ResultSet userRs =
            userPs.executeQuery();

            if(userRs.next()){

                receiverName =
                            userRs.getString(
                        "full_name");
}
} catch (SQLException ex) {
            Logger.getLogger(ChatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

String sql =
    "SELECT * FROM direct_messages " +
    "WHERE (sender_id=? AND receiver_id=?) " +
    "OR (sender_id=? AND receiver_id=?) " +
    "ORDER BY sent_at";

PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);

String updateSql =
    "UPDATE direct_messages " +
    "SET is_read = true " +
    "WHERE sender_id=? " +
    "AND receiver_id=? " +
    "AND is_read=false";

PreparedStatement updatePs =
        con.prepareStatement(updateSql);

updatePs.setString(1, receiverId);
updatePs.setString(2, userId);

updatePs.executeUpdate();

ps.setString(1, userId);
ps.setString(2, receiverId);
ps.setString(3, receiverId);
ps.setString(4, userId);

ResultSet rs =
        ps.executeQuery();

ArrayList<Message> messages =
        new ArrayList<>();

while(rs.next()){

    Message m = new Message();

    m.setId(
        rs.getString("id"));

    m.setSenderId(
        rs.getString("sender_id"));

    m.setReceiverId(
        rs.getString("receiver_id"));

    m.setContent(
        rs.getString("content"));

    m.setSentAt(
        rs.getString("sent_at"));
    
    m.setRead(
    rs.getBoolean("is_read"));

    messages.add(m);
}

request.setAttribute(
        "messages",
        messages);

if(receiverId != null &&
   !receiverId.trim().isEmpty()){

request.setAttribute(
        "receiverId",
        receiverId);

request.setAttribute(
        "currentUserId",
        userId);

request.setAttribute(
        "receiverName",
        receiverName);
}
        
        ArrayList<ChatUser> chatUsers =
        new ArrayList<>();

String sidebarSql =
"SELECT " +
"u.id, " +
"u.full_name, " +
"d.content, " +
"d.sent_at " +
"FROM direct_messages d " +
"JOIN users u ON " +
"(u.id = CASE " +
"WHEN d.sender_id=? " +
"THEN d.receiver_id " +
"ELSE d.sender_id END) " +
"WHERE ? IN (d.sender_id,d.receiver_id) " +
"ORDER BY d.sent_at DESC";

PreparedStatement sidebarPs =
        con.prepareStatement(sidebarSql);

sidebarPs.setString(1, userId);
sidebarPs.setString(2, userId);

ResultSet sidebarRs =
        sidebarPs.executeQuery();

HashSet<String> addedUsers =
        new HashSet<>();

while(sidebarRs.next()){

    String otherUserId =
            sidebarRs.getString("id");

    if(addedUsers.contains(
            otherUserId)){

        continue;
    }

    addedUsers.add(
            otherUserId);

    ChatUser cu =
            new ChatUser();

    cu.setUserId(
            otherUserId);

    cu.setFullName(
            sidebarRs.getString(
                    "full_name"));

    cu.setLastMessage(
            sidebarRs.getString(
                    "content"));
    
    String unreadSql =
    "SELECT COUNT(*) " +
    "FROM direct_messages " +
    "WHERE sender_id=? " +
    "AND receiver_id=? " +
    "AND is_read=false";

PreparedStatement unreadPs =
        con.prepareStatement(unreadSql);

unreadPs.setString(1, otherUserId);
unreadPs.setString(2, userId);

ResultSet unreadRs =
        unreadPs.executeQuery();

if(unreadRs.next()){

    cu.setUnreadCount(
        unreadRs.getInt(1));
}

    cu.setLastMessageTime(
            sidebarRs.getString(
                    "sent_at"));

    chatUsers.add(cu);
}

request.setAttribute(
        "chatUsers",
        chatUsers);

String statusSql =
    "SELECT is_online,last_seen " +
    "FROM user_status " +
    "WHERE user_id=?";

PreparedStatement statusPs =
        con.prepareStatement(
                statusSql);

statusPs.setString(
        1,
        receiverId);

ResultSet statusRs =
        statusPs.executeQuery();

if(statusRs.next()){

    boolean online =
        statusRs.getBoolean(
                "is_online");

    if(online){

        statusText = "Online";

    }else{

        Timestamp lastSeen =
    statusRs.getTimestamp(
            "last_seen");

if(lastSeen == null){

    statusText = "Offline";

}else{

    long diff =
        System.currentTimeMillis()
        - lastSeen.getTime();

    long minutes =
        diff / (1000 * 60);

    long hours =
        diff / (1000 * 60 * 60);

    long days =
        diff / (1000 * 60 * 60 * 24);

    if(minutes < 1){

        statusText =
            "Last seen just now";

    }
    else if(minutes < 60){

        statusText =
            "Last seen "
            + minutes
            + " minute(s) ago";

    }
    else if(hours < 24){

        statusText =
            "Last seen "
            + hours
            + " hour(s) ago";

    }
    else{

        statusText =
            "Last seen "
            + days
            + " day(s) ago";
    }
}
    }
}

request.setAttribute(
        "statusText",
        statusText);

} catch (SQLException ex) {
            Logger.getLogger(ChatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        request.getRequestDispatcher(
                "/myjsp/chat.jsp")
                .forward(request,response);
    }
}