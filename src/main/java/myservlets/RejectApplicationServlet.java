package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/rejectApplication")
public class RejectApplicationServlet extends HttpServlet {

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
                    "SET status='rejected' " +
                    "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, applicationId);

            ps.executeUpdate();
            
            String getApplicantSql =
"SELECT applicant_id " +
"FROM applications " +
"WHERE id=?";

PreparedStatement getPs =
        con.prepareStatement(
                getApplicantSql);

getPs.setString(1, applicationId);

ResultSet rs =
        getPs.executeQuery();

if(rs.next()){

    String applicantId =
            rs.getString(
                    "applicant_id");

    String notifySql =
    "INSERT INTO notifications " +
    "(user_id,type,ref_id,ref_table,message,is_read) " +
    "VALUES(?,?,?,?,?,0)";

    PreparedStatement notifyPs =
            con.prepareStatement(
                    notifySql);

    notifyPs.setString(
            1,
            applicantId);

    notifyPs.setString(
            2,
            "application");

    notifyPs.setString(
            3,
            applicationId);

    notifyPs.setString(
            4,
            "applications");

    notifyPs.setString(
            5,
            "Your application has been rejected");

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