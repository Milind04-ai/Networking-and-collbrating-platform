package myservlets;

import myclasses.Project;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {

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
                    + "/login");

            return;
        }
        
        String currentUserId =
    session.getAttribute("userId")
           .toString();

request.setAttribute(
    "currentUserId",
    currentUserId);

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT p.*, u.full_name " +
                    "FROM projects p " +
                    "JOIN users u ON p.owner_id = u.id " +
                    "ORDER BY p.created_at DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            ArrayList<Project> projects =
                    new ArrayList<>();

            while(rs.next()){

                Project p = new Project();

                p.setId(
                        rs.getString("id"));

                p.setOwnerId(
                        rs.getString("owner_id"));

                p.setOwnerName(
                        rs.getString("full_name"));

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
                
                String projectId =
                        rs.getString("id");
                
                String roleSql =
"SELECT role_title, slots_available " +
"FROM project_roles " +
"WHERE project_id=? " +
"AND is_open=1";

PreparedStatement rolePs =
        con.prepareStatement(roleSql);

rolePs.setString(
        1,
        projectId);

ResultSet roleRs =
        rolePs.executeQuery();

StringBuilder rolesHtml =
        new StringBuilder();

while(roleRs.next()){

    rolesHtml.append(
        "<div class='role-req'>")
        .append(
        "<b>")
        .append(
        roleRs.getString(
            "role_title"))
        .append(
        "</b>")
        .append(
        " (")
        .append(
        roleRs.getInt(
            "slots_available"))
        .append(
        " slot(s))")
        .append(
        "</div>");
}

if(rolesHtml.length() == 0){

    rolesHtml.append(
        "<p>No open roles</p>");
}

p.setOpenRolesHtml(
        rolesHtml.toString());

String memberSql =
"SELECT " +
"u.full_name, " +
"r.role_title, " +
"pm.permission " +
"FROM project_members pm " +
"JOIN users u " +
"ON pm.user_id=u.id " +
"LEFT JOIN project_roles r " +
"ON pm.role_id=r.id " +
"WHERE pm.project_id=?";

PreparedStatement memberPs =
        con.prepareStatement(
                memberSql);

memberPs.setString(
        1,
        projectId);

ResultSet memberRs =
        memberPs.executeQuery();

StringBuilder teamHtml =
        new StringBuilder();

while(memberRs.next()){

    teamHtml.append(
        "<div class='team-member-row'>")
        .append(
        "<div class='member-info'>")
        .append(
        "<div class='member-name'>")
        .append(
        memberRs.getString(
            "full_name"))
        .append(
        "</div>")
        .append(
        "<div class='member-role'>")
        .append(
        memberRs.getString(
            "role_title") == null
            ? memberRs.getString(
                "permission")
            : memberRs.getString(
                "role_title"))
        .append(
        "</div>")
        .append(
        "</div>")
        .append(
        "</div>");
}

p.setTeamHtml(
        teamHtml.toString());

                projects.add(p);
            }

            request.setAttribute(
                    "projects",
                    projects);

            request.getRequestDispatcher(
                    "/myjsp/projects.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}