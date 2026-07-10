package myservlets;

import myutil.DBConnection;
import myutil.ActivityLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/uploadProjectFile")
@MultipartConfig
public class UploadProjectFileServlet extends HttpServlet {

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
                    + "/login");

            return;
        }

        String currentUserId =
                session.getAttribute("userId")
                       .toString();

        String projectId =
                request.getParameter("projectId");

        Part filePart =
                request.getPart("file");

        if(filePart == null ||
           filePart.getSize() == 0){

            response.sendRedirect(
                    request.getContextPath()
                    + "/workspace?id="
                    + projectId);

            return;
        }

        String originalName =
                Paths.get(
                        filePart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        String storedName =
                UUID.randomUUID()
                + "_"
                + originalName;

        String uploadPath =
        "C:\\NexusUploads";

File folder =
        new File(uploadPath);

if(!folder.exists()){
    folder.mkdirs();
}

String filePath =
        uploadPath
        + File.separator
        + storedName;

System.out.println("Upload Path = " + uploadPath);
System.out.println("Saving File = " + filePath);

filePart.write(filePath);

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
            "INSERT INTO project_files " +
            "(project_id,uploaded_by," +
            "original_name,stored_name," +
            "file_path,file_size) " +
            "VALUES(?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, currentUserId);
            ps.setString(3, originalName);
            ps.setString(4, storedName);
            ps.setString(5, filePath);
            ps.setLong(6, filePart.getSize());

            ps.executeUpdate();

            ActivityLogger.log(
                    projectId,
                    currentUserId,
                    "uploaded file \""
                    + originalName
                    + "\"");

        }catch(Exception e){

            e.printStackTrace();
        }

        response.sendRedirect(
                request.getContextPath()
                + "/workspace?id="
                + projectId);
    }
}