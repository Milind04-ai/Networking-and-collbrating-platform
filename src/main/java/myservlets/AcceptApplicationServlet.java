package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/acceptApplication")
public class AcceptApplicationServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String applicationId =
                request.getParameter(
                        "applicationId");

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE applications " +
                    "SET status='accepted' " +
                    "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, applicationId);

            ps.executeUpdate();
            
            String getAppSql =
    "SELECT project_id, applicant_id " +
    "FROM applications " +
    "WHERE id=?";

PreparedStatement getPs =
    con.prepareStatement(getAppSql);

getPs.setString(1, applicationId);

ResultSet rs =
    getPs.executeQuery();

if(rs.next()) {

    String projectId =
            rs.getString("project_id");

    String applicantId =
            rs.getString("applicant_id");
    
    String memberSql =
    "INSERT INTO project_members " +
    "(project_id, user_id, permission) " +
    "VALUES (?, ?, ?)";

PreparedStatement memberPs =
    con.prepareStatement(memberSql);

memberPs.setString(1, projectId);
memberPs.setString(2, applicantId);
memberPs.setString(3, "member");

memberPs.executeUpdate();

String notifySql =
"INSERT INTO notifications " +
"(user_id,type,ref_id,ref_table,message,is_read) " +
"VALUES(?,?,?,?,?,0)";

PreparedStatement notifyPs =
        con.prepareStatement(notifySql);

notifyPs.setString(1, applicantId);
notifyPs.setString(2, "application");
notifyPs.setString(3, applicationId);
notifyPs.setString(4, "applications");
notifyPs.setString(
        5,
        "Your application has been accepted");

notifyPs.executeUpdate();
}

            response.sendRedirect(
                    request.getContextPath()
                    + "/manageApplications");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}