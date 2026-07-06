package myservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myutil.DBConnection;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if(session != null) {
            
            String userId =
    session.getAttribute(
        "userId").toString();
            try {

Connection con =
    DBConnection.getConnection();

String offlineSql =
    "UPDATE user_status " +
    "SET is_online=false, " +
    "last_seen=NOW() " +
    "WHERE user_id=?";

PreparedStatement ps;

                ps = con.prepareStatement(
                        offlineSql);
            

ps.setString(1,userId);

ps.executeUpdate();
            }

catch (SQLException ex) {
                Logger.getLogger(LogoutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            session.invalidate();

        }

        response.sendRedirect(
                "myhtml/login.html"
        );
    }
}