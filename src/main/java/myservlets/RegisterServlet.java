package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fullName =
                request.getParameter("full_name");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        String role =
                request.getParameter("role");

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO users(full_name,email,password_hash,role)"
                    + " VALUES(?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);

            int row = ps.executeUpdate();

            if(row > 0){

                response.getWriter().println(
                        "Registration Successful");

            } else {

                response.getWriter().println(
                        "Registration Failed");

            }

        } catch(Exception e){

            e.printStackTrace();

    response.getWriter().println(
            "ERROR : " + e.getMessage()
    );

        }
    }
}