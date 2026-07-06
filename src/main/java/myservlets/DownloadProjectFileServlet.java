package myservlets;

import myutil.DBConnection;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/downloadProjectFile")
public class DownloadProjectFileServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Download servlet called");
        
        String fileId =
        request.getParameter("id");

if(fileId == null){

    response.sendRedirect(
        request.getContextPath()
        + "/projects");

    return;
}

try{

    Connection con =
            DBConnection.getConnection();

    String sql =
    "SELECT * " +
    "FROM project_files " +
    "WHERE id=?";

    PreparedStatement ps =
            con.prepareStatement(sql);

    ps.setString(1, fileId);

    ResultSet rs =
            ps.executeQuery();

    if(!rs.next()){

        response.sendError(404);

        return;
    }

    String originalName =
            rs.getString("original_name");

    String filePath =
            rs.getString("file_path");
    
    System.out.println("File Path = " + filePath);

File file = new File(filePath);

System.out.println("Exists = " + file.exists());

System.out.println("Absolute = " + file.getAbsolutePath());

if(!file.exists()){

    response.sendError(404);

    return;
}

response.setContentType(
        "application/octet-stream");

response.setHeader(
        "Content-Disposition",
        "attachment; filename=\"" +
        originalName +
        "\"");

response.setContentLengthLong(
        file.length());

FileInputStream fis =
        new FileInputStream(file);

OutputStream os =
        response.getOutputStream();

byte[] buffer =
        new byte[4096];

int bytesRead;

while((bytesRead = fis.read(buffer))
        != -1){

    os.write(
        buffer,
        0,
        bytesRead);
}

fis.close();
os.flush();

}catch(Exception e){

    e.printStackTrace();

    response.sendError(500);
}
    }

}