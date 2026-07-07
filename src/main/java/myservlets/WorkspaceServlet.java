package myservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import myclasses.Message;
import myclasses.Project;
import myclasses.Task;
import myclasses.WorkspaceMember;
import myclasses.Activity;
import myclasses.ProjectFile;
import myutil.DBConnection;

@WebServlet("/workspace")
public class WorkspaceServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String projectId =
                request.getParameter("id");
        
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
        
        ArrayList<WorkspaceMember> members =
new ArrayList<>();
        
        ArrayList<Message> messages =
        new ArrayList<>();
        
        ArrayList<Task> tasks =
        new ArrayList<>();
        
        ArrayList<Activity> activities =
        new ArrayList<>();
        
        ArrayList<ProjectFile> files =
        new ArrayList<>();
        
        try {
        
        Connection con =
        DBConnection.getConnection();
        
        String accessSql =
"SELECT COUNT(*) " +
"FROM projects p " +
"LEFT JOIN project_members pm " +
"ON p.id=pm.project_id " +
"WHERE p.id=? " +
"AND (" +
"p.owner_id=? " +
"OR pm.user_id=?" +
")";
        
        PreparedStatement accessPs =
        con.prepareStatement(
                accessSql);

accessPs.setString(
        1,
        projectId);

accessPs.setString(
        2,
        currentUserId);

accessPs.setString(
        3,
        currentUserId);

ResultSet accessRs =
        accessPs.executeQuery();

boolean hasAccess = false;

if(accessRs.next()){

    hasAccess =
        accessRs.getInt(1) > 0;
}

if(!hasAccess){

    response.sendRedirect(
        request.getContextPath()
        + "/projects");

    return;
}

String sql =
"SELECT p.*,u.full_name " +
"FROM projects p " +
"JOIN users u " +
"ON p.owner_id=u.id " +
"WHERE p.id=?";

PreparedStatement ps =
        con.prepareStatement(sql);

        
            ps.setString(1, projectId);

ResultSet rs =
        ps.executeQuery();

Project project = null;

if(rs.next()){

    project = new Project();

    project.setTitle(
    rs.getString("title") == null
    ? ""
    : rs.getString("title"));

project.setDescription(
    rs.getString("description") == null
    ? ""
    : rs.getString("description"));

project.setEmoji(
    rs.getString("emoji") == null
    ? "📁"
    : rs.getString("emoji"));

project.setStatus(
    rs.getString("status") == null
    ? "Unknown"
    : rs.getString("status"));

project.setTags(
    rs.getString("tags") == null
    ? ""
    : rs.getString("tags"));

project.setOwnerName(
    rs.getString("full_name") == null
    ? "Unknown"
    : rs.getString("full_name"));
}

String groupChatId = null;

String groupSql =
"SELECT id " +
"FROM group_chats " +
"WHERE project_id=?";

PreparedStatement groupPs =
        con.prepareStatement(groupSql);

groupPs.setString(
        1,
        projectId);

ResultSet groupRs =
        groupPs.executeQuery();

if(groupRs.next()){

    groupChatId =
        groupRs.getString("id");
}

if(groupChatId != null){

String msgSql =
"SELECT gm.*,u.full_name " +
"FROM group_messages gm " +
"JOIN users u " +
"ON gm.sender_id=u.id " +
"WHERE gm.chat_id=? " +
"ORDER BY gm.sent_at";

PreparedStatement msgPs =
        con.prepareStatement(msgSql);

msgPs.setString(
        1,
        groupChatId);

ResultSet msgRs =
        msgPs.executeQuery();

while(msgRs.next()){

    Message m =
            new Message();

    m.setSenderId(
            msgRs.getString(
                    "sender_id"));

    m.setSenderName(
            msgRs.getString(
                    "full_name"));

    m.setContent(
            msgRs.getString(
                    "content"));

    m.setSentAt(
            msgRs.getString(
                    "sent_at"));

    messages.add(m);
}
}

String memberSql =
"SELECT u.id,u.full_name " +
"FROM project_members pm " +
"JOIN users u " +
"ON pm.user_id=u.id " +
"WHERE pm.project_id=?";

PreparedStatement memberPs =
        con.prepareStatement(
                memberSql);

memberPs.setString(
        1,
        projectId);

ResultSet memberRs =
        memberPs.executeQuery();

while(memberRs.next()){

    WorkspaceMember wm =
            new WorkspaceMember();

    wm.setId(
        memberRs.getString("id"));

    wm.setName(
        memberRs.getString(
                "full_name"));

    members.add(wm);
}

String taskSql =
"SELECT pt.*,u.full_name " +
"FROM project_tasks pt " +
"LEFT JOIN users u " +
"ON pt.assigned_to=u.id " +
"WHERE pt.project_id=? " +
"ORDER BY pt.created_at DESC";

PreparedStatement taskPs =
        con.prepareStatement(taskSql);

taskPs.setString(1, projectId);

ResultSet taskRs =
        taskPs.executeQuery();

while(taskRs.next()){

    Task t =
            new Task();

    t.setId(
        taskRs.getString("id"));

    t.setProjectId(
        taskRs.getString("project_id"));

    t.setTitle(
        taskRs.getString("title"));

    t.setDescription(
        taskRs.getString("description"));

    t.setAssignedTo(
    taskRs.getString("full_name"));

    t.setStatus(
        taskRs.getString("status"));

    tasks.add(t);
}

String activitySql =
"SELECT pa.activity," +
"u.full_name," +
"pa.created_at " +
"FROM project_activities pa " +
"LEFT JOIN users u " +
"ON pa.user_id=u.id " +
"WHERE pa.project_id=? " +
"ORDER BY pa.created_at DESC " +
"LIMIT 3";

PreparedStatement activityPs =
        con.prepareStatement(
                activitySql);

activityPs.setString(
        1,
        projectId);

ResultSet activityRs =
        activityPs.executeQuery();

while(activityRs.next()){

    Activity a =
            new Activity();

    a.setActivity(
            activityRs.getString(
                    "activity"));

    a.setUserName(
            activityRs.getString(
                    "full_name"));

    a.setCreatedAt(
            activityRs.getString(
                    "created_at"));

    activities.add(a);
}

int todoCount = 0;
int progressCount = 0;
int completedCount = 0;

for(Task t : tasks){

    if("todo".equals(t.getStatus())){

        todoCount++;

    }else if("in_progress".equals(
            t.getStatus())){

        progressCount++;

    }else if("completed".equals(
            t.getStatus())){

        completedCount++;
    }
}

String fileSql =
"SELECT pf.*,u.full_name " +
"FROM project_files pf " +
"JOIN users u " +
"ON pf.uploaded_by=u.id " +
"WHERE pf.project_id=? " +
"ORDER BY pf.uploaded_at DESC";

PreparedStatement filePs =
        con.prepareStatement(fileSql);

filePs.setString(1, projectId);

ResultSet fileRs =
        filePs.executeQuery();

while(fileRs.next()){

    ProjectFile f =
            new ProjectFile();

    f.setId(
        fileRs.getString("id"));

    f.setProjectId(
        fileRs.getString("project_id"));

    f.setUploadedBy(
        fileRs.getString("uploaded_by"));

    f.setUploaderName(
        fileRs.getString("full_name"));

    f.setOriginalName(
        fileRs.getString("original_name"));

    f.setStoredName(
        fileRs.getString("stored_name"));

    f.setFilePath(
        fileRs.getString("file_path"));

    f.setFileSize(
        fileRs.getLong("file_size"));

    f.setUploadedAt(
        fileRs.getString("uploaded_at"));

    files.add(f);
}

request.setAttribute(
        "project",
        project);

request.setAttribute(
                "projectId",
                projectId);

request.setAttribute(
        "members",
        members);

request.setAttribute(
        "messages",
        messages);

request.setAttribute(
        "groupChatId",
        groupChatId);

request.setAttribute(
        "currentUserId",
        currentUserId);

request.setAttribute(
        "tasks",
        tasks);

request.setAttribute(
        "todoCount",
        todoCount);

request.setAttribute(
        "progressCount",
        progressCount);

request.setAttribute(
        "completedCount",
        completedCount);

request.setAttribute(
        "activities",
        activities);

request.setAttribute(
        "files",
        files);

} catch (SQLException ex) {
            Logger.getLogger(WorkspaceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher(
                "/myjsp/workspace.jsp")
                .forward(request, response);
    }
}