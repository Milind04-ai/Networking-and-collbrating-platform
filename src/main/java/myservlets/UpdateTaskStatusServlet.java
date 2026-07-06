package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import myutil.ActivityLogger;
import javax.servlet.http.*;

@WebServlet("/updateTaskStatus")
public class UpdateTaskStatusServlet
        extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String taskId =
                request.getParameter(
                        "taskId");

        String status =
                request.getParameter(
                        "status");

        String projectId =
                request.getParameter(
                        "projectId");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
            "UPDATE project_tasks " +
            "SET status=? " +
            "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setString(2, taskId);

            ps.executeUpdate();
            
            ActivityLogger.log(
        projectId,
        null,
        "changed a task status to " + status);

            response.sendRedirect(
                request.getContextPath()
                + "/workspace?id="
                + projectId);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}