package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/projects/create")
public class CreateProjectServlet extends HttpServlet {

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
        
        String projectId =
        UUID.randomUUID().toString();

        String ownerId =
                session.getAttribute("userId")
                        .toString();

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String tags =
                request.getParameter("skills");

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
        "INSERT INTO projects "
      + "(id,owner_id,title,description,tags,"
      + "emoji,status,is_public) "
      + "VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, ownerId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, tags);
            ps.setString(6, "🚀");
            ps.setString(7, "active");
            ps.setBoolean(8, true);

ps.executeUpdate();

String memberSql =
        "INSERT INTO project_members "
      + "(project_id,user_id,permission) "
      + "VALUES(?,?,?)";

PreparedStatement memberPs =
        con.prepareStatement(memberSql);

memberPs.setString(1, projectId);
memberPs.setString(2, ownerId);
memberPs.setString(3, "owner");

memberPs.executeUpdate();

String chatSql =
        "INSERT INTO group_chats "
      + "(id,project_id,name) "
      + "VALUES(?,?,?)";

PreparedStatement chatPs =
        con.prepareStatement(chatSql);

String chatId =
        UUID.randomUUID()
                    .toString();

chatPs.setString(1, chatId);
chatPs.setString(2, projectId);
chatPs.setString(
        3,
        title + " Team");

chatPs.executeUpdate();

response.sendRedirect(
        request.getContextPath()
        + "/projects");

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}