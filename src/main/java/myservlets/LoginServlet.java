package myservlets;

import myutil.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asus
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM users WHERE email=? AND password_hash=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "userId",
                        rs.getString("id"));

                session.setAttribute(
                        "userName",
                        rs.getString("full_name"));

                session.setAttribute(
                        "role",
                        rs.getString("role"));
                
                session.setAttribute(
    "fullName",
    rs.getString("full_name")
);

String headline =
        rs.getString("headline");

if(headline == null){

    headline = "Nexus Member";
}

session.setAttribute(
        "headline",
        headline);
                
                String onlineSql =
    "UPDATE user_status " +
    "SET is_online=true, " +
    "last_seen=NOW() " +
    "WHERE user_id=?";

PreparedStatement onlinePs =
        con.prepareStatement(
                onlineSql);

onlinePs.setString(
        1,
        rs.getString("id"));

onlinePs.executeUpdate();

                response.sendRedirect(request.getContextPath() + "/dashboard");

            } else {

request.setAttribute(
    "error",
    "Invalid email or password"
);

request.getRequestDispatcher(
    "/myjsp/login.jsp"
).forward(
    request,
    response
);

return;

            }

        } catch(Exception e) {

            e.printStackTrace();

        }
    }
}
