package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import myutil.ActivityLogger;
import javax.servlet.http.*;

@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {

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

        String projectId =
                request.getParameter(
                        "projectId");

        String title =
                request.getParameter(
                        "title");

        String description =
                request.getParameter(
                        "description");

        String assignedTo =
                request.getParameter(
                        "assignedTo");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "INSERT INTO project_tasks " +
                "(project_id,title,description,assigned_to) " +
                "VALUES(?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, assignedTo);

            ps.executeUpdate();
            
            ActivityLogger.log(
        projectId,
        currentUserId,
        "created task \"" + title + "\"");

            response.sendRedirect(
                request.getContextPath()
                + "/workspace?id="
                + projectId);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}