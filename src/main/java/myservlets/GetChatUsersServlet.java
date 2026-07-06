package myservlets;

import myclasses.ChatUser;
import myutil.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/getChatUsers")
public class GetChatUsersServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if(session == null ||
           session.getAttribute("userId") == null){

            response.setStatus(401);
            return;
        }

        String userId =
                session.getAttribute("userId")
                       .toString();

        response.setContentType(
                "application/json");

        PrintWriter out =
                response.getWriter();

        try{

            Connection con =
                    DBConnection.getConnection();

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

            PreparedStatement ps =
                    con.prepareStatement(
                            sidebarSql);

            ps.setString(1, userId);
            ps.setString(2, userId);

            ResultSet rs =
                    ps.executeQuery();

            HashSet<String> added =
                    new HashSet<>();

            out.print("[");

            boolean first = true;

            while(rs.next()){

                String otherUserId =
                        rs.getString("id");
                
                String unreadSql =
"SELECT COUNT(*) " +
"FROM direct_messages " +
"WHERE sender_id=? " +
"AND receiver_id=? " +
"AND is_read=false";

PreparedStatement unreadPs =
        con.prepareStatement(
                unreadSql);

unreadPs.setString(
        1,
        otherUserId);

unreadPs.setString(
        2,
        userId);

ResultSet unreadRs =
        unreadPs.executeQuery();

int unreadCount = 0;

if(unreadRs.next()){

    unreadCount =
        unreadRs.getInt(1);
}

                if(added.contains(
                        otherUserId)){

                    continue;
                }

                added.add(
                        otherUserId);

                if(!first){
                    out.print(",");
                }

                first = false;

                String name =
                        rs.getString(
                                "full_name");

                String lastMessage =
                        rs.getString(
                                "content");

                lastMessage =
                        lastMessage
                        .replace("\\","\\\\")
                        .replace("\"","\\\"")
                        .replace("\n","\\n")
                        .replace("\r","");

                out.print("{");

                out.print("\"userId\":\""
                        + otherUserId
                        + "\",");

                out.print("\"fullName\":\""
                        + name
                        + "\",");
                
                out.print("\"unreadCount\":"
        + unreadCount
        + ",");

                out.print("\"lastMessage\":\""
                        + lastMessage
                        + "\"");

                out.print("}");
            }

            out.print("]");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}