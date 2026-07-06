package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if(session == null ||
           session.getAttribute("userId") == null){

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        String userId =
                session.getAttribute("userId")
                       .toString();

        int applicationCount = 0;
        int messageCount = 0;
        int requestCount = 0;

        try{

            Connection con =
                    DBConnection.getConnection();

            // Pending applications
            String appSql =
                "SELECT COUNT(*) " +
                "FROM applications a " +
                "JOIN projects p " +
                "ON a.project_id=p.id " +
                "WHERE p.owner_id=? " +
                "AND a.status='pending'";

            PreparedStatement appPs =
                    con.prepareStatement(appSql);

            appPs.setString(1, userId);

            ResultSet appRs =
                    appPs.executeQuery();

            if(appRs.next()){

                applicationCount =
                        appRs.getInt(1);
            }

            // Unread messages
            String msgSql =
                "SELECT COUNT(*) " +
                "FROM direct_messages " +
                "WHERE receiver_id=? " +
                "AND is_read=false";

            PreparedStatement msgPs =
                    con.prepareStatement(msgSql);

            msgPs.setString(1, userId);

            ResultSet msgRs =
                    msgPs.executeQuery();

            if(msgRs.next()){

                messageCount =
                        msgRs.getInt(1);
            }

            // Connection requests
            String reqSql =
                "SELECT COUNT(*) " +
                "FROM connections " +
                "WHERE receiver_id=? " +
                "AND status='pending'";

            PreparedStatement reqPs =
                    con.prepareStatement(reqSql);

            reqPs.setString(1, userId);

            ResultSet reqRs =
                    reqPs.executeQuery();

            if(reqRs.next()){

                requestCount =
                        reqRs.getInt(1);
            }

            response.setContentType(
                    "application/json");

            PrintWriter out =
                    response.getWriter();

            out.print(
                "{"
                + "\"applications\":"
                + applicationCount
                + ","
                + "\"messages\":"
                + messageCount
                + ","
                + "\"requests\":"
                + requestCount
                + "}"
            );

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}