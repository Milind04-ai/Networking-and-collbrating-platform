package myservlets;

import myclasses.GroupMessage;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/teamChat")
public class TeamChatServlet extends HttpServlet {

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
                    + "/myhtml/login.html");

            return;
        }

        String projectId =
                request.getParameter(
                        "projectId");
        
        String userId =
        session.getAttribute("userId")
               .toString();

        try{

            Connection con =
                    DBConnection.getConnection();

            String chatSql =
                "SELECT * FROM group_chats " +
                "WHERE project_id=?";

            PreparedStatement chatPs =
                    con.prepareStatement(chatSql);

            chatPs.setString(1, projectId);

            ResultSet chatRs =
                    chatPs.executeQuery();

            String chatId = "";
            String chatName = "";

            if(chatRs.next()){

                chatId =
                    chatRs.getString("id");

                chatName =
                    chatRs.getString("name");
            }
            
            String memberSql =
    "SELECT COUNT(*) " +
    "FROM project_members " +
    "WHERE project_id=? " +
    "AND user_id=?";

PreparedStatement memberPs =
        con.prepareStatement(memberSql);

memberPs.setString(1, projectId);
memberPs.setString(2, userId);

ResultSet memberRs =
        memberPs.executeQuery();

boolean allowed = false;

if(memberRs.next()){

    allowed =
        memberRs.getInt(1) > 0;
}

if(!allowed){

    response.getWriter().println(
        "Access Denied");

    return;
}

            String msgSql =
                "SELECT gm.*, u.full_name " +
                "FROM group_messages gm " +
                "JOIN users u " +
                "ON gm.sender_id=u.id " +
                "WHERE gm.chat_id=? " +
                "ORDER BY gm.sent_at";

            PreparedStatement msgPs =
                    con.prepareStatement(msgSql);

            msgPs.setString(1, chatId);

            ResultSet rs =
                    msgPs.executeQuery();

            ArrayList<GroupMessage> messages =
                    new ArrayList<>();

            while(rs.next()){

                GroupMessage gm =
                        new GroupMessage();

                gm.setId(
                    rs.getString("id"));

                gm.setChatId(
                    rs.getString("chat_id"));

                gm.setSenderId(
                    rs.getString("sender_id"));

                gm.setSenderName(
                    rs.getString("full_name"));

                gm.setContent(
                    rs.getString("content"));

                gm.setSentAt(
                    rs.getString("sent_at"));

                messages.add(gm);
            }

            request.setAttribute(
                    "chatId",
                    chatId);

            request.setAttribute(
                    "chatName",
                    chatName);

            request.setAttribute(
                    "messages",
                    messages);

            request.getRequestDispatcher(
                    "/myjsp/teamChat.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}