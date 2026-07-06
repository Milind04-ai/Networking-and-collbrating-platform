/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package myservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myclasses.Project;
import myclasses.ProjectMember;
import myutil.DBConnection;

/**
 *
 * @author asus
 */
@WebServlet("/projectDetails")
public class ProjectDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String projectId =
                request.getParameter("id");
        
        String currentUserId = null;

if(request.getSession(false) != null){

    Object obj =
        request.getSession(false)
               .getAttribute("userId");

    if(obj != null){

        currentUserId =
                obj.toString();
    }
}

boolean isOwner = false;
boolean isMember = false;
boolean alreadyApplied = false;
String applicationStatus = null;

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "SELECT p.*, u.full_name " +
                "FROM projects p " +
                "JOIN users u " +
                "ON p.owner_id=u.id " +
                "WHERE p.id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);

            ResultSet rs =
                    ps.executeQuery();

            Project p = null;

            if(rs.next()){
                
                String ownerId =
        rs.getString("owner_id");
                
                if(currentUserId != null &&
   currentUserId.equals(ownerId)){

    isOwner = true;
}
                
                p = new Project();

                p.setId(
                    rs.getString("id"));

                p.setTitle(
                    rs.getString("title"));

                p.setDescription(
                    rs.getString("description"));

                p.setEmoji(
                    rs.getString("emoji"));

                p.setStatus(
                    rs.getString("status"));

                p.setTags(
                    rs.getString("tags"));

                p.setOwnerName(
                    rs.getString("full_name"));
                
                ArrayList<ProjectMember> members =
        new ArrayList<>();

String memberSql =
"SELECT u.id,u.full_name " +
"FROM project_members pm " +
"JOIN users u " +
"ON pm.user_id=u.id " +
"WHERE pm.project_id=?";

PreparedStatement memberPs =
        con.prepareStatement(memberSql);

memberPs.setString(1, projectId);

ResultSet memberRs =
        memberPs.executeQuery();

while(memberRs.next()){

    ProjectMember pm =
            new ProjectMember();

    pm.setUserId(
        memberRs.getString("id"));

    pm.setFullName(
        memberRs.getString("full_name"));

    members.add(pm);
}

request.setAttribute(
        "members",
        members);

String appSql =
"SELECT status " +
"FROM applications " +
"WHERE project_id=? " +
"AND applicant_id=?";

PreparedStatement appPs =
        con.prepareStatement(appSql);

appPs.setString(1, projectId);
appPs.setString(2, currentUserId);

ResultSet appRs =
        appPs.executeQuery();

if(appRs.next()){

    alreadyApplied = true;

    applicationStatus =
            appRs.getString("status");
}
            }

            request.setAttribute(
                    "project",
                    p);
            
            request.setAttribute(
        "isOwner",
        isOwner);

request.setAttribute(
        "isMember",
        isMember);

request.setAttribute(
        "alreadyApplied",
        alreadyApplied);

request.setAttribute(
        "applicationStatus",
        applicationStatus);

            request.getRequestDispatcher(
                    "/myjsp/projectDetails.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}