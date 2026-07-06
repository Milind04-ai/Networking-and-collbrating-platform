package myservlets;

import myclasses.Notification;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/notificationsPage")
public class NotificationsPageServlet
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

            response.sendRedirect(
                request.getContextPath()
                + "/myhtml/login.html");

            return;
        }

        String userId =
                session.getAttribute("userId")
                       .toString();

        try{

            Connection con =
                    DBConnection.getConnection();
            
            String updateSql =
    "UPDATE notifications " +
    "SET is_read = 1 " +
    "WHERE user_id=?";

PreparedStatement updatePs =
    con.prepareStatement(updateSql);

updatePs.setString(1, userId);

updatePs.executeUpdate();

            String sql =
                "SELECT * " +
                "FROM notifications " +
                "WHERE user_id=? " +
                "ORDER BY created_at DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            ArrayList<Notification> list =
                    new ArrayList<>();

            while(rs.next()){

                Notification n =
                        new Notification();

                n.setId(
                    rs.getString("id"));

                n.setType(
                    rs.getString("type"));

                n.setMessage(
                    rs.getString("message"));

                n.setRead(
                    rs.getBoolean("is_read"));

                n.setCreatedAt(
                    rs.getString("created_at"));

                list.add(n);
            }

            request.setAttribute(
                    "notifications",
                    list);

            request.getRequestDispatcher(
                    "/myjsp/notifications.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}