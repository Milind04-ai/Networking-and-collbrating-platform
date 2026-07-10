package myservlets;

import myclasses.UserConnection;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/network")
public class NetworkServlet extends HttpServlet {

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

            // Incoming Requests

            ArrayList<UserConnection>
                    requests =
                    new ArrayList<>();

            String requestSql =
                "SELECT c.id, u.full_name " +
                "FROM connections c " +
                "JOIN users u " +
                "ON c.requester_id=u.id " +
                "WHERE c.receiver_id=? " +
                "AND c.status='pending'";

            PreparedStatement requestPs =
                    con.prepareStatement(
                            requestSql);

            requestPs.setString(
                    1,
                    userId);

            ResultSet requestRs =
                    requestPs.executeQuery();

            while(requestRs.next()){

                UserConnection uc =
                        new UserConnection();

                uc.setId(
                        requestRs.getString(
                                "id"));

                uc.setRequesterName(
                        requestRs.getString(
                                "full_name"));

                requests.add(uc);
            }

            // Accepted Connections

            ArrayList<UserConnection>
                    connections =
                    new ArrayList<>();

            String connectionSql =
                "SELECT u.id, u.full_name " +
                "FROM connections c " +
                "JOIN users u " +
                "ON (u.id=c.requester_id " +
                "OR u.id=c.receiver_id) " +
                "WHERE c.status='accepted' " +
                "AND (c.requester_id=? " +
                "OR c.receiver_id=?) " +
                "AND u.id<>?";

            PreparedStatement connectionPs =
                    con.prepareStatement(
                            connectionSql);

            connectionPs.setString(
                    1,
                    userId);

            connectionPs.setString(
                    2,
                    userId);

            connectionPs.setString(
                    3,
                    userId);

            ResultSet connectionRs =
                    connectionPs.executeQuery();

            while(connectionRs.next()){

                UserConnection uc =
                        new UserConnection();

                uc.setConnectionId(
                        connectionRs.getString(
                                "id"));

                uc.setConnectionName(
                        connectionRs.getString(
                                "full_name"));

                connections.add(uc);
            }

            request.setAttribute(
                    "requests",
                    requests);

            request.setAttribute(
                    "connections",
                    connections);

            request.getRequestDispatcher(
                    "/myjsp/network.jsp")
                    .forward(
                            request,
                            response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}