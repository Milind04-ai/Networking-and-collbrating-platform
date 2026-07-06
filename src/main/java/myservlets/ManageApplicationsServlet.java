package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.ArrayList;
import myclasses.Application;

@WebServlet("/manageApplications")
public class ManageApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(
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

        String ownerId =
                session.getAttribute("userId")
                        .toString();

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "SELECT a.id, a.status, " +
                "p.title, u.full_name " +
                "FROM applications a " +
                "JOIN projects p " +
                "ON a.project_id = p.id " +
                "JOIN users u " +
                "ON a.applicant_id = u.id " +
                "WHERE p.owner_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, ownerId);

            ResultSet rs =
        ps.executeQuery();

ArrayList<Application> apps =
        new ArrayList<>();

while(rs.next()){

    Application a =
            new Application();

    a.setId(
        rs.getString("id"));

    a.setProjectTitle(
        rs.getString("title"));

    a.setApplicantName(
        rs.getString("full_name"));

    a.setStatus(
        rs.getString("status"));

    apps.add(a);
}

request.setAttribute(
        "applications",
        apps);

request.getRequestDispatcher(
        "/myjsp/manageApplications.jsp")
        .forward(request, response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}