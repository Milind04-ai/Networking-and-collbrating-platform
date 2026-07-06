package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/rejectConnection")
public class RejectConnectionServlet extends HttpServlet {

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
                "SET status='blocked', " +
                "responded_at=NOW() " +
                "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, connectionId);

            ps.executeUpdate();

            response.sendRedirect(
                request.getContextPath()
                + "/network");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}