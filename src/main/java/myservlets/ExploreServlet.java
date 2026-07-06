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
import javax.servlet.http.HttpSession;
import myclasses.Project;
import myclasses.User;
import myutil.DBConnection;


@WebServlet("/explore")
public class ExploreServlet extends HttpServlet {

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
        
        String search =
        request.getParameter("search");

if(search == null){
    search = "";
}

        try{

            Connection con =
                    DBConnection.getConnection();

            ArrayList<User> users =
                    new ArrayList<>();

            String userSql =
    "SELECT * FROM users " +
    "WHERE is_active = 1 " +
    "AND (" +
    "full_name LIKE ? " +
    "OR headline LIKE ? " +
    "OR location LIKE ?)";
            
            PreparedStatement userPs =
        con.prepareStatement(userSql);

String pattern =
        "%" + search + "%";

userPs.setString(1, pattern);
userPs.setString(2, pattern);
userPs.setString(3, pattern);

            ResultSet userRs =
                    userPs.executeQuery();

            while(userRs.next()){

                User u = new User();

                u.setId(
                    userRs.getString("id"));

                u.setFullName(
                    userRs.getString("full_name"));

                u.setHeadline(
                    userRs.getString("headline"));

                u.setLocation(
                    userRs.getString("location"));

                u.setRole(
                    userRs.getString("role"));

                users.add(u);
            }

            ArrayList<Project> projects =
                    new ArrayList<>();

            String projectSql =
    "SELECT p.*, u.full_name " +
    "FROM projects p " +
    "JOIN users u " +
    "ON p.owner_id = u.id " +
    "WHERE p.title LIKE ? " +
    "OR p.description LIKE ? " +
    "OR p.tags LIKE ?";
            
            PreparedStatement projectPs =
        con.prepareStatement(projectSql);

projectPs.setString(1, pattern);
projectPs.setString(2, pattern);
projectPs.setString(3, pattern);

            ResultSet projectRs =
                    projectPs.executeQuery();

            while(projectRs.next()){

                Project p =
                        new Project();

                p.setId(
                    projectRs.getString("id"));

                p.setTitle(
                    projectRs.getString("title"));

                p.setDescription(
                    projectRs.getString("description"));

                p.setOwnerName(
                    projectRs.getString("full_name"));

                p.setTags(
                    projectRs.getString("tags"));

                projects.add(p);
            }

            request.setAttribute(
                    "users",
                    users);

            request.setAttribute(
                    "projects",
                    projects);

            request.getRequestDispatcher(
                    "/myjsp/explore.jsp")
                    .forward(request,response);

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}

