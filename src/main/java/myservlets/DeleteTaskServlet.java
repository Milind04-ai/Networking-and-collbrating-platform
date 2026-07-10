package myservlets;

import myutil.DBConnection;
import myutil.ActivityLogger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {

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

        String currentUserId =
                session.getAttribute("userId")
                       .toString();

        String taskId =
                request.getParameter("taskId");

        String projectId =
                request.getParameter("projectId");

        try{

            Connection con =
                    DBConnection.getConnection();

            // Get task title before deleting
            String title = "";

            String getSql =
            "SELECT title " +
            "FROM project_tasks " +
            "WHERE id=?";

            PreparedStatement getPs =
                    con.prepareStatement(getSql);

            getPs.setString(1, taskId);

            java.sql.ResultSet rs =
                    getPs.executeQuery();

            if(rs.next()){

                title = rs.getString("title");
            }

            // Delete task
            String sql =
            "DELETE FROM project_tasks " +
            "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, taskId);

            ps.executeUpdate();

            ActivityLogger.log(
                    projectId,
                    currentUserId,
                    "deleted task \"" +
                    title +
                    "\"");

        }catch(Exception e){

            e.printStackTrace();
        }

        response.sendRedirect(
                request.getContextPath()
                + "/workspace?id="
                + projectId);
    }
}