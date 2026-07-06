package myservlets;

import myclasses.UserConnection;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/myConnections")
public class MyConnectionsServlet extends HttpServlet {

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

            String sql =
                "SELECT u.id, u.full_name " +
                "FROM connections c " +
                "JOIN users u " +
                "ON (u.id = c.requester_id " +
                "OR u.id = c.receiver_id) " +
                "WHERE c.status='accepted' " +
                "AND (c.requester_id=? " +
                "OR c.receiver_id=?) " +
                "AND u.id<>?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);
            ps.setString(2, userId);
            ps.setString(3, userId);

            ResultSet rs =
                    ps.executeQuery();

            ArrayList<UserConnection> list =
                    new ArrayList<>();

            while(rs.next()){

                UserConnection uc =
                        new UserConnection();

                uc.setConnectionId(
    rs.getString("id"));

uc.setConnectionName(
    rs.getString("full_name"));

                list.add(uc);
            }

            request.setAttribute(
                    "connections",
                    list);

            request.getRequestDispatcher(
                    "/myjsp/myConnections.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}