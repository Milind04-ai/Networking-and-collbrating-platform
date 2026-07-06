package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/acceptConnection")
public class AcceptConnectionServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String connectionId =
                request.getParameter(
                        "connectionId");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "UPDATE connections " +
                "SET status='accepted', " +
                "responded_at=NOW() " +
                "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, connectionId);

            ps.executeUpdate();
            
            String getSql =
"SELECT requester_id " +
"FROM connections " +
"WHERE id=?";

PreparedStatement getPs =
        con.prepareStatement(
                getSql);

getPs.setString(1, connectionId);

ResultSet rs =
        getPs.executeQuery();

if(rs.next()){

    String requesterId =
            rs.getString(
                    "requester_id");

    String notifySql =
    "INSERT INTO notifications " +
    "(user_id,type,message,is_read) " +
    "VALUES(?,?,?,0)";

    PreparedStatement notifyPs =
            con.prepareStatement(
                    notifySql);

    notifyPs.setString(
            1,
            requesterId);

    notifyPs.setString(
            2,
            "connection");

    notifyPs.setString(
            3,
            "Your connection request was accepted");

    notifyPs.executeUpdate();
}

            response.sendRedirect(
                request.getContextPath()
                + "/network");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}