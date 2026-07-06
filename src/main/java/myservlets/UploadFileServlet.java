package myservlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import myutil.DBConnection;

@WebServlet("/uploadFile")
@MultipartConfig
public class UploadFileServlet
        extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        
        HttpSession session =
        request.getSession(false);

String senderId =
        session.getAttribute(
                "userId")
               .toString();

String receiverId =
        request.getParameter(
                "receiverId");

Part filePart =
        request.getPart("file");

String fileName =
        filePart.getSubmittedFileName();

System.out.println(
        "File = " + fileName);

    }
}