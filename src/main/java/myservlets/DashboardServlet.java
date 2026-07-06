package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.time.LocalTime;
import myclasses.Project;
import java.util.ArrayList;
import myclasses.Application;
import javax.servlet.http.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

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

String userId =
        session.getAttribute("userId")
               .toString();

try{

    Connection con =
            DBConnection.getConnection();
    
    String userSql =
    "SELECT full_name " +
    "FROM users " +
    "WHERE id=?";

PreparedStatement userPs =
        con.prepareStatement(userSql);

userPs.setString(1, userId);

ResultSet userRs =
        userPs.executeQuery();

String fullName = "";

if(userRs.next()){

    fullName =
        userRs.getString("full_name");
}

request.setAttribute(
        "fullName",
        fullName);

String greeting;

int hour = LocalTime.now().getHour();

if(hour < 12){

    greeting = " Good Morning";

}
else if(hour < 17){

    greeting = " Good Afternoon";

}
else{

    greeting = " Good Evening";

}

request.setAttribute(
        "greeting",
        greeting);

    int projectCount = 0;
    int connectionCount = 0;
    int applicationCount = 0;
    int unreadMessages = 0;
    
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

String connectionSql =
    "SELECT COUNT(*) " +
    "FROM connections " +
    "WHERE status='accepted' " +
    "AND (requester_id=? " +
    "OR receiver_id=?)";

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

String appSql =
    "SELECT COUNT(*) " +
    "FROM applications a " +
    "JOIN projects p " +
    "ON a.project_id=p.id " +
    "WHERE p.owner_id=? " +
    "AND a.status='pending'";

PreparedStatement appPs =
        con.prepareStatement(appSql);

appPs.setString(1, userId);

ResultSet appRs =
        appPs.executeQuery();

if(appRs.next()){

    applicationCount =
            appRs.getInt(1);
}

String msgSql =
    "SELECT COUNT(*) " +
    "FROM direct_messages " +
    "WHERE receiver_id=? " +
    "AND is_read=false";

PreparedStatement msgPs =
        con.prepareStatement(msgSql);

msgPs.setString(1, userId);

ResultSet msgRs =
        msgPs.executeQuery();

if(msgRs.next()){

    unreadMessages =
            msgRs.getInt(1);
}

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
        "unreadMessages",
        unreadMessages);

String recentProjectSql =
"SELECT id,title,status " +
"FROM projects " +
"WHERE owner_id=? " +
"ORDER BY created_at DESC " +
"LIMIT 3";

PreparedStatement recentPs =
        con.prepareStatement(recentProjectSql);

recentPs.setString(1, userId);

ResultSet recentRs =
        recentPs.executeQuery();

java.util.List<Project> recentProjects =
        new java.util.ArrayList<>();

while(recentRs.next()){

    Project p = new Project();

    p.setId(
        recentRs.getString("id"));

    p.setTitle(
        recentRs.getString("title"));

    p.setStatus(
        recentRs.getString("status"));

    recentProjects.add(p);
}

request.setAttribute(
        "recentProjects",
        recentProjects);

String recentAppSql =
"SELECT a.id, " +
"a.project_id, " +
"a.applicant_id, " +
"u.full_name, " +
"p.title, " +
"a.message, " +
"a.status " +
"FROM applications a " +
"JOIN users u " +
"ON a.applicant_id=u.id " +
"JOIN projects p " +
"ON a.project_id=p.id " +
"WHERE p.owner_id=? " +
"AND a.status='pending' " +
"ORDER BY a.applied_at DESC " +
"LIMIT 3";

PreparedStatement appListPs =
        con.prepareStatement(recentAppSql);

appListPs.setString(1, userId);

ResultSet appListRs =
        appListPs.executeQuery();

ArrayList<Application> recentApplications =
        new ArrayList<>();

while(appListRs.next()){

    Application app =
            new Application();

    app.setId(
        appListRs.getString("id"));

    app.setProjectId(
        appListRs.getString("project_id"));

    app.setApplicantId(
        appListRs.getString("applicant_id"));

    app.setApplicantName(
        appListRs.getString("full_name"));

    app.setProjectTitle(
        appListRs.getString("title"));

    app.setMessage(
        appListRs.getString("message"));

    app.setStatus(
        appListRs.getString("status"));

    recentApplications.add(app);
}

request.setAttribute(
        "recentApplications",
        recentApplications);

request.getRequestDispatcher(
        "/myjsp/dashboard.jsp")
        .forward(request,response);

}catch(Exception e){

    e.printStackTrace();
}

    }
}