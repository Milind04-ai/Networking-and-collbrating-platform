package myservlets;

import myclasses.TeamMember;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/teamMembers")
public class TeamMembersServlet extends HttpServlet {

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
                    "SELECT u.id, " +
                    "u.full_name, " +
                    "pm.permission, " +
                    "p.title " +
                    "FROM project_members pm " +
                    "JOIN users u " +
                    "ON pm.user_id = u.id " +
                    "JOIN projects p " +
                    "ON pm.project_id = p.id " +
                    "WHERE pm.project_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);

            ResultSet rs =
                    ps.executeQuery();

            ArrayList<TeamMember> members =
                    new ArrayList<>();

            while(rs.next()) {

                TeamMember tm =
                        new TeamMember();

                tm.setUserId(
                        rs.getString("id"));

                tm.setFullName(
                        rs.getString("full_name"));

                tm.setPermission(
                        rs.getString("permission"));

                tm.setProjectTitle(
                        rs.getString("title"));

                members.add(tm);
            }

            request.setAttribute(
                    "members",
                    members);

            request.getRequestDispatcher(
                    "/myjsp/teamMembers.jsp")
                    .forward(request,response);

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}