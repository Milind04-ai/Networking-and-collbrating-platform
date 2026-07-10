package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import myclasses.Project;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

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
        request.getContextPath() + "/login");

            return;
        }

        String userId =
                session.getAttribute("userId")
                        .toString();
        
        ArrayList<Project> portfolioProjects =
        new ArrayList<>();
        
        HashSet<String> skills =
        new HashSet<>();
        
        int projectCount = 0;
int connectionCount = 0;
int applicationCount = 0;

        try{

            Connection con =
                    DBConnection.getConnection();
            
String projectSql =
"SELECT * " +
"FROM projects " +
"WHERE owner_id=? " +
"ORDER BY created_at DESC " +
"LIMIT 6";

PreparedStatement projectPs =
        con.prepareStatement(projectSql);

projectPs.setString(1, userId);

ResultSet projectRs =
        projectPs.executeQuery();

while(projectRs.next()){

    Project p = new Project();

    p.setId(
            projectRs.getString("id"));

    p.setTitle(
            projectRs.getString("title"));

    p.setDescription(
            projectRs.getString("description"));

    p.setEmoji(
            projectRs.getString("emoji"));

    p.setStatus(
            projectRs.getString("status"));

    p.setTags(
            projectRs.getString("tags"));

    portfolioProjects.add(p);
    
    String tags =
projectRs.getString("tags");

if(tags != null){

    String[] arr =
            tags.split(",");

    for(String s : arr){

        skills.add(
            s.trim());

    }
}
}

String connectionSql =
"SELECT COUNT(*) " +
"FROM connections " +
"WHERE status='accepted' " +
"AND (requester_id=? OR receiver_id=?)";

PreparedStatement connectionPs =
        con.prepareStatement(connectionSql);

connectionPs.setString(1, userId);
connectionPs.setString(2, userId);

ResultSet connectionRs =
        connectionPs.executeQuery();

if(connectionRs.next()){

    connectionCount =
            connectionRs.getInt(1);
}

String applicationSql =
"SELECT COUNT(*) " +
"FROM applications " +
"WHERE applicant_id=?";

PreparedStatement applicationPs =
        con.prepareStatement(applicationSql);

applicationPs.setString(1, userId);

ResultSet applicationRs =
        applicationPs.executeQuery();

if(applicationRs.next()){

    applicationCount =
            applicationRs.getInt(1);
}

            String sql =
                    "SELECT * FROM users WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                request.setAttribute(
                        "fullName",
                        rs.getString("full_name"));

                request.setAttribute(
                        "email",
                        rs.getString("email"));

                request.setAttribute(
                        "headline",
                        rs.getString("headline"));

                request.setAttribute(
                        "bio",
                        rs.getString("bio"));

                request.setAttribute(
                        "location",
                        rs.getString("location"));
                
                request.setAttribute(
        "skills",
        skills);
                
                request.setAttribute(
        "projectCount",
        projectCount);

request.setAttribute(
        "connectionCount",
        connectionCount);

request.setAttribute(
        "applicationCount",
        applicationCount);

request.setAttribute(
        "portfolioProjects",
        portfolioProjects);

                request.getRequestDispatcher("/myjsp/profile.jsp")
        .forward(request,response);

            }else {

    response.getWriter().println(
            "User not found");

}

        }catch(Exception e){

    e.printStackTrace();

    response.getWriter().println(
            "ERROR : " + e.getMessage());

}

    }
}