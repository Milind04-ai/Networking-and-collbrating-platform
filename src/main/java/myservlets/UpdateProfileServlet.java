package myservlets;

import myutil.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/profile/update")
public class UpdateProfileServlet extends HttpServlet {

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

        String userId =
                session.getAttribute("userId")
                        .toString();

        String fullName =
                request.getParameter("full_name");

        String headline =
                request.getParameter("headline");

        String bio =
                request.getParameter("bio");

        String location =
                request.getParameter("location");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "UPDATE users "
              + "SET full_name=?, "
              + "headline=?, "
              + "bio=?, "
              + "location=? "
              + "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, headline);
            ps.setString(3, bio);
            ps.setString(4, location);
            ps.setString(5, userId);

response.sendRedirect(
        request.getContextPath()
        + "/profile");

        }catch(Exception e){

            e.printStackTrace();

            response.getWriter().println(
                    "ERROR : " + e.getMessage());
        }
    }
}