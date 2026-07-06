package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/applyProject")
public class ApplyProjectServlet extends HttpServlet {

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
                    + "/myhtml/login.html");

            return;
        }

        String applicantId =
                session.getAttribute("userId")
                        .toString();

        String projectId =
                request.getParameter("projectId");

        try {

            Connection con =
                    DBConnection.getConnection();
            
            String checkSql =
    "SELECT * FROM applications " +
    "WHERE project_id=? " +
    "AND applicant_id=?";

PreparedStatement checkPs =
    con.prepareStatement(checkSql);

checkPs.setString(1, projectId);
checkPs.setString(2, applicantId);

ResultSet checkRs =
    checkPs.executeQuery();

if(checkRs.next()){

    response.getWriter().println(
        "You have already applied to this project.");

    return;
}

            String sql =
                    "INSERT INTO applications "
                  + "(project_id, applicant_id) "
                  + "VALUES (?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, applicantId);

            ps.executeUpdate();

            response.sendRedirect(
                    request.getContextPath()
                    + "/projects");

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}