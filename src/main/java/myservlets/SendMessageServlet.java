package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {

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

        String receiverId =
                request.getParameter("receiverId");

        String content =
                request.getParameter("content");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "INSERT INTO direct_messages " +
                "(sender_id, receiver_id, content) " +
                "VALUES (?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, senderId);
            ps.setString(2, receiverId);
            ps.setString(3, content);

            ps.executeUpdate();

            response.sendRedirect(
                request.getContextPath()
                + "/chat?receiverId="
                + receiverId);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}