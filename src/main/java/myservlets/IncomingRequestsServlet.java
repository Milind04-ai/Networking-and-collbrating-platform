package myservlets;

import myclasses.UserConnection;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/incomingRequests")
public class IncomingRequestsServlet extends HttpServlet {

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

        String userId =
                session.getAttribute("userId")
                       .toString();

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "SELECT c.id, " +
                "c.requester_id, " +
                "c.receiver_id, " +
                "c.status, " +
                "u.full_name " +
                "FROM connections c " +
                "JOIN users u " +
                "ON c.requester_id = u.id " +
                "WHERE c.receiver_id=? " +
                "AND c.status='pending'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            ArrayList<myclasses.UserConnection> requests =
                    new ArrayList<>();

            while(rs.next()){

                myclasses.UserConnection c =
                        new myclasses.UserConnection();

                c.setId(
                    rs.getString("id"));

                c.setRequesterId(
                    rs.getString("requester_id"));

                c.setReceiverId(
                    rs.getString("receiver_id"));

                c.setStatus(
                    rs.getString("status"));

                c.setRequesterName(
                    rs.getString("full_name"));

                requests.add(c);
            }

            request.setAttribute(
                    "requests",
                    requests);

            request.getRequestDispatcher(
                    "/myjsp/incomingRequests.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}