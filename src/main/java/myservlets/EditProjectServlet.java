package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String projectId =
                request.getParameter("projectId");

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM projects WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                request.setAttribute(
                        "projectId",
                        rs.getString("id"));

                request.setAttribute(
                        "title",
                        rs.getString("title"));

                request.setAttribute(
                        "description",
                        rs.getString("description"));

                request.setAttribute(
                        "tags",
                        rs.getString("tags"));

                request.getRequestDispatcher(
                        "/myjsp/editProject.jsp")
                        .forward(request,response);
            }

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}