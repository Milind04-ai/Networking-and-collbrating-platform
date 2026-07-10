package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.sql.ResultSet;
import javax.servlet.http.*;

@WebServlet("/sendGroupMessage")
public class SendGroupMessageServlet
        extends HttpServlet {

    @Override
    protected void doPost(
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

        String senderId =
                session.getAttribute("userId")
                       .toString();

        String chatId =
                request.getParameter("chatId");

        String content =
                request.getParameter("content");
        
        String fromWorkspace =
        request.getParameter(
                "fromWorkspace");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "INSERT INTO group_messages " +
                "(chat_id,sender_id,content) " +
                "VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, chatId);
            ps.setString(2, senderId);
            ps.setString(3, content);

            ps.executeUpdate();

            if("true".equals(fromWorkspace)){

    String projectId = null;

    String projectSql =
    "SELECT project_id " +
    "FROM group_chats " +
    "WHERE id=?";

    PreparedStatement projectPs =
            con.prepareStatement(
                    projectSql);

    projectPs.setString(
            1,
            chatId);

    ResultSet projectRs =
            projectPs.executeQuery();

    if(projectRs.next()){

        projectId =
            projectRs.getString(
                    "project_id");
    }

    response.sendRedirect(
        request.getContextPath()
        + "/workspace?id="
        + projectId);

}else{

    response.sendRedirect(
        request.getContextPath()
        + "/chat?groupId="
        + chatId);
}

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}