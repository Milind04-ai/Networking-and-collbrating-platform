package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/updateProject")
public class UpdateProjectServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String projectId =
                request.getParameter("projectId");

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String tags =
                request.getParameter("tags");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE projects "
                  + "SET title=?, "
                  + "description=?, "
                  + "tags=? "
                  + "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, tags);
            ps.setString(4, projectId);

            ps.executeUpdate();

            response.sendRedirect(
                    request.getContextPath()
                    + "/projects");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}