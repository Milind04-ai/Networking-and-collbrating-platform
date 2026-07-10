package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteProject")
public class DeleteProjectServlet extends HttpServlet {

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

        String projectId =
                request.getParameter("projectId");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM projects "
                  + "WHERE id=? "
                  + "AND owner_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, userId);

            ps.executeUpdate();

            response.sendRedirect(
                    request.getContextPath()
                    + "/projects");

        }catch(Exception e){

            e.printStackTrace();

            response.getWriter().println(
                    "ERROR : " + e.getMessage());
        }
    }
}