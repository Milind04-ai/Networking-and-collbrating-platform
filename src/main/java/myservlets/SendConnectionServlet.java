package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sendConnection")
public class SendConnectionServlet extends HttpServlet {

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

        String requesterId =
                session.getAttribute("userId")
                        .toString();

        String receiverId =
                request.getParameter("receiverId");
        
        if(requesterId.equals(receiverId)){

    response.sendRedirect(
        request.getContextPath()
        + "/explore");

    return;
}

        try{

            Connection con =
                    DBConnection.getConnection();

            String checkSql =
"SELECT * FROM connections " +
"WHERE (requester_id=? AND receiver_id=?) " +
"OR (requester_id=? AND receiver_id=?)";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setString(1, requesterId);
checkPs.setString(2, receiverId);
checkPs.setString(3, receiverId);
checkPs.setString(4, requesterId);

            ResultSet rs =
                    checkPs.executeQuery();

            if(rs.next()){

                response.sendRedirect(
    request.getContextPath()
    + "/explore");

                return;
            }

            String insertSql =
                "INSERT INTO connections " +
                "(requester_id,receiver_id,status) " +
                "VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(insertSql);

            ps.setString(1, requesterId);
            ps.setString(2, receiverId);
            ps.setString(3, "pending");

            ps.executeUpdate();
            
            String notifySql =
"INSERT INTO notifications " +
"(user_id,type,message,is_read) " +
"VALUES(?,?,?,0)";

PreparedStatement notifyPs =
        con.prepareStatement(
                notifySql);

notifyPs.setString(
        1,
        receiverId);

notifyPs.setString(
        2,
        "connection");

notifyPs.setString(
        3,
        "You received a connection request");

notifyPs.executeUpdate();

            response.sendRedirect(
    request.getContextPath()
    + "/explore");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}