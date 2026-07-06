package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/notificationDropdown")
public class NotificationDropdownServlet
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

            response.setStatus(401);
            return;
        }

        String userId =
                session.getAttribute("userId")
                       .toString();

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
"SELECT message, created_at " +
"FROM notifications " +
"WHERE user_id=? " +
"AND is_read=0 " +
"ORDER BY created_at DESC " +
"LIMIT 5";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            response.setContentType(
                    "application/json");

            PrintWriter out =
                    response.getWriter();

            out.print("[");

            boolean first = true;

            while(rs.next()){

                if(!first){
                    out.print(",");
                }

                out.print(
                "{"
                + "\"message\":\""
                + rs.getString("message")
                + "\","
                + "\"time\":\""
                + rs.getString("created_at")
                + "\""
                + "}");

                first = false;
            }

            out.print("]");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}