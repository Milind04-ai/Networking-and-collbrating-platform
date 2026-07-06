package myservlets;

import myclasses.User;
import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.ArrayList;
import myclasses.Project;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userId =
                request.getParameter("id");
        
        HttpSession session =
        request.getSession(false);

String currentUserId =
        session.getAttribute("userId")
               .toString();

int connectionCount = 0;

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                "SELECT * " +
                "FROM users " +
                "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            User user = null;

            if(rs.next()){

                user = new User(){};

                user.setId(
                    rs.getString("id"));

                user.setFullName(
                    rs.getString("full_name"));

                user.setHeadline(
                    rs.getString("headline"));

                user.setLocation(
                    rs.getString("location"));

                user.setBio(
                    rs.getString("bio"));
            }
            
            int projectCount = 0;

String projectSql =
    "SELECT COUNT(*) " +
    "FROM projects " +
    "WHERE owner_id=?";

PreparedStatement projectPs =
    con.prepareStatement(projectSql);

projectPs.setString(1, userId);

ResultSet projectRs =
    projectPs.executeQuery();

if(projectRs.next()){

    projectCount =
            projectRs.getInt(1);
}

ArrayList<Project> projects =
        new ArrayList<>();

String projectListSql =
    "SELECT * " +
    "FROM projects " +
    "WHERE owner_id=?";

PreparedStatement projectListPs =
    con.prepareStatement(projectListSql);

projectListPs.setString(1, userId);

ResultSet projectListRs =
    projectListPs.executeQuery();

while(projectListRs.next()){

    Project p =
            new Project();

    p.setId(
        projectListRs.getString("id"));

    p.setTitle(
        projectListRs.getString("title"));

    p.setDescription(
        projectListRs.getString("description"));

    p.setEmoji(
        projectListRs.getString("emoji"));

    projects.add(p);
}

String connectionStatus =
        "none";

String connectionSql =
    "SELECT status " +
    "FROM connections " +
    "WHERE " +
    "((requester_id=? AND receiver_id=?) " +
    "OR " +
    "(requester_id=? AND receiver_id=?))";

PreparedStatement connectionPs =
    con.prepareStatement(connectionSql);

connectionPs.setString(1, currentUserId);
connectionPs.setString(2, userId);

connectionPs.setString(3, userId);
connectionPs.setString(4, currentUserId);

ResultSet connectionRs =
        connectionPs.executeQuery();

String connectioncSql =
"SELECT COUNT(*) " +
"FROM connections " +
"WHERE status='accepted' " +
"AND (requester_id=? OR receiver_id=?)";

PreparedStatement connectioncPs =
con.prepareStatement(connectioncSql);

connectioncPs.setString(1, userId);
connectioncPs.setString(2, userId);

ResultSet connectioncRs =
connectioncPs.executeQuery();

if(connectioncRs.next()){

    connectionCount =
            connectioncRs.getInt(1);
}

if(connectionRs.next()){

    connectionStatus =
        connectionRs.getString("status");
}

request.setAttribute(
    "connectionCount",
    connectionCount);

request.setAttribute(
        "connectionStatus",
        connectionStatus);

request.setAttribute(
        "isOwnProfile",
        currentUserId.equals(userId));

request.setAttribute(
        "projects",
        projects);

request.setAttribute(
        "projectCount",
        projectCount);

            request.setAttribute(
                    "profileUser",
                    user);

            request.getRequestDispatcher(
                    "/myjsp/userProfile.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}