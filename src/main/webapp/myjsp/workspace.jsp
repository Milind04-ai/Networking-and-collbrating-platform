<%@page import="myclasses.WorkspaceMember"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="myclasses.Project"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Message"%>
<%@page import="myclasses.Task"%>
<%@page import="myclasses.Activity"%>
<%@page import="myclasses.ProjectFile"%>

<%
Project project =
(Project)request.getAttribute(
        "project");

String projectId =
(String)request.getAttribute("projectId");

ArrayList<WorkspaceMember> members =
(ArrayList<WorkspaceMember>)
request.getAttribute(
        "members");

ArrayList<Message> messages =
(ArrayList<Message>)
request.getAttribute(
        "messages");

ArrayList<Task> tasks =
(ArrayList<Task>)
request.getAttribute("tasks");

ArrayList<Activity> activities =
(ArrayList<Activity>)
request.getAttribute(
        "activities");

ArrayList<ProjectFile> files =
(ArrayList<ProjectFile>)
request.getAttribute("files");

if(files == null){
    files = new ArrayList<>();
}

if(activities == null){
    activities = new ArrayList<>();
}

if(tasks == null){
    tasks = new ArrayList<>();
}

Integer todoCount =
(Integer)request.getAttribute(
        "todoCount");

Integer progressCount =
(Integer)request.getAttribute(
        "progressCount");

Integer completedCount =
(Integer)request.getAttribute(
        "completedCount");

String currentUserId =
(String)request.getAttribute(
        "currentUserId");

String groupChatId =
(String)request.getAttribute(
        "groupChatId");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Workspace</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/workspace.css">

</head>

<body>

<div class="layout">

<aside id="sidebar"></aside>

<main class="main">

<script>

document.addEventListener(
'DOMContentLoaded',
() => {

    renderSidebar('projects');

    renderTopbar('Workspace');

    loadNotificationCounts();
});

</script>

<div class="page-content">
    <div class="workspace-header">

    <div class="workspace-title">

        <span class="project-emoji">
            <%= project.getEmoji() %>
        </span>

        <span>
            <%= project.getTitle() %>
        </span>

    </div>

    <p class="workspace-description">

        Team collaboration workspace

    </p>

    <div class="workspace-stats">

        <div class="stat-card">

            <div class="stat-icon">👥</div>

            <div>

                <h3><%= members.size() %></h3>

                <span>Members</span>

            </div>

        </div>

        <div class="stat-card">

            <div class="stat-icon">📝</div>

            <div>

                <h3><%= tasks.size() %></h3>

                <span>Total Tasks</span>

            </div>

        </div>

        <div class="stat-card">

            <div class="stat-icon">⚡</div>

            <div>

                <h3><%= progressCount %></h3>

                <span>In Progress</span>

            </div>

        </div>

        <div class="stat-card">

            <div class="stat-icon">✅</div>

            <div>

                <h3><%= completedCount %></h3>

                <span>Completed</span>

            </div>

        </div>

    </div>

</div>
    <div class="workspace-grid">

        <div class="workspace-members">

<h3>

👥 Team Members

(<%= members.size() %>)

</h3>

<br>

<%
if(members != null){

for(WorkspaceMember member : members){
%>

<div class="member-card">

    <div class="member-avatar">
        <%= member.getName().substring(0,1).toUpperCase() %>
    </div>

    <div class="member-details">

        <div class="member-name">

            <%= member.getName() %>

        </div>

        <div class="member-role">

            <%= member.getId().equals(project.getOwnerId())
                ? "👑 Project Owner"
                : "👤 Team Member" %>

        </div>

    </div>

</div>

<%
}
}
%>
</div>
<div class="workspace-center">

<div class="workspace-card">

    <h3>Tasks</h3>

    <form method="post"
          action="<%=request.getContextPath()%>/addTask">

        <input type="hidden"
               name="projectId"
               value="<%= project.getId() %>">

        <input type="text"
               name="title"
               placeholder="Task title"
               required>

        <br><br>

        <textarea name="description"
                  placeholder="Task description"></textarea>

        <br><br>

        <select name="assignedTo">

<%
for(WorkspaceMember m : members){
%>

<option value="<%= m.getId() %>">

    <%= m.getName() %>

</option>

<%
}
%>

</select>

        <br><br>

        <button class="btn btn-primary">

            ➕ Add Task

        </button>

    </form>
</div>

    <hr style="margin:20px 0">
    <div class="workspace-card">
        
        <h3>

📋 Task List

</h3>

    <% for(Task t : tasks){ %>

    <div class="task-card">

        <div class="task-header">

    <h4>

        <%= t.getTitle() %>

    </h4>

    <form method="post"
          action="<%=request.getContextPath()%>/deleteTask"
          onsubmit="return confirm('Delete this task?');">

        <input type="hidden"
               name="taskId"
               value="<%= t.getId() %>">

        <input type="hidden"
               name="projectId"
               value="<%= project.getId() %>">

        <button class="delete-icon">

            🗑

        </button>

    </form>

</div>
        <p>
            <%= t.getDescription() %>
        </p>

        <div class="task-assigned">
    👤
    <span>

        <%= t.getAssignedTo() %>

    </span>

</div>

        <br>

        <form method="post"
      action="<%=request.getContextPath()%>/updateTaskStatus">

    <input type="hidden"
           name="taskId"
           value="<%= t.getId() %>">

    <input type="hidden"
           name="projectId"
           value="<%= project.getId() %>">

    <select name="status"
            onchange="this.form.submit()">

        <option value="todo"
        <%= "todo".equals(
            t.getStatus())
            ? "selected"
            : "" %>>

            Todo
            <%= todoCount %>

        </option>

        <option value="in_progress"
        <%= "in_progress".equals(
            t.getStatus())
            ? "selected"
            : "" %>>

            In Progress
            <%= progressCount %>

        </option>

        <option value="completed"
        <%= "completed".equals(
            t.getStatus())
            ? "selected"
            : "" %>>

            Completed
            <%= completedCount %>

        </option>

    </select>

</form>

    </div>

    <% } %>
    </div>
    

        <div class="workspace-chat">

<div class="chat-header">

    <div>

        <h2>💬 Team Discussion</h2>

        <small>

            Collaborate with your teammates

        </small>

    </div>

</div>

            <div class="workspace-messages">
                <form method="post"
      action="<%=request.getContextPath()%>/sendGroupMessage">

    <input type="hidden"
           name="chatId"
           value="<%= groupChatId %>">
    
    <input type="hidden"
       name="fromWorkspace"
       value="true">

    <div class="workspace-chat-form">

        <input type="text"
               name="content"
               placeholder="Send a message..."
               required>

        <button type="submit"
                class="btn btn-primary">

            Send

        </button>

    </div>

</form>
           
           <%
if(messages == null || messages.isEmpty()){
%>

<div class="empty-workspace-chat">

No discussion yet 🚀

</div>

<%
}
else{
%>

<div class="workspace-chat-feed">
<%
for(Message m : messages){

boolean mine =
    m.getSenderId()
     .equals(currentUserId);
%>

<div class="chat-row
<%= mine ? "mine" : "" %>">

    <div class="chat-bubble">

        <div class="sender-name">

            <%= m.getSenderName() %>

        </div>

        <div class="message-text">

            <%= m.getContent() %>

        </div>

        <div class="message-time">

            <%= m.getSentAt() %>

        </div>

    </div>

</div>

<%
}
%>
</div>
<%
}
%>
</div>

        </div>
</div>

        <div class="workspace-info">

            <h3>Project Info</h3>

<hr><br>

<p>
<b>Title</b><br>
<%= project.getTitle() %>
</p>

<br>

<p>
<b>Owner</b><br>
<%= project.getOwnerName() %>
</p>

<br>

<span class="status-badge
<%= project.getStatus().toLowerCase() %>">

    <%= project.getStatus() %>

</span>

<br>

<div class="skills-container">

<%
String[] tags =
project.getTags().split(",");

for(String tag : tags){
%>

    <span class="skill-tag">

        <%= tag.trim() %>

    </span>

<%
}
%>

</div>

<hr style="margin:25px 0;">
<div class="workspace-card">

<h3>Project Files</h3>

<form method="post"
      action="<%=request.getContextPath()%>/uploadProjectFile"
      enctype="multipart/form-data">

    <input type="hidden"
           name="projectId"
           value="<%= project.getId() %>">

    <input type="file"
           name="file"
           required>

    <br><br>

    <button class="btn btn-primary">

        Upload File

    </button>

</form>
           
<hr style="margin:25px 0;">

<h3>Files List</h3>

<div class="project-files">

<%
if(files.isEmpty()){
%>

<p>No files uploaded.</p>

<%
}else{

for(ProjectFile f : files){
%>

<div class="file-card">

    <%
String fileName =
        f.getOriginalName().toLowerCase();

String icon = "📄";

if(fileName.endsWith(".pdf")){

    icon = "📕";
}
else if(fileName.endsWith(".doc") ||
        fileName.endsWith(".docx")){

    icon = "📘";
}
else if(fileName.endsWith(".xls") ||
        fileName.endsWith(".xlsx")){

    icon = "📗";
}
else if(fileName.endsWith(".ppt") ||
        fileName.endsWith(".pptx")){

    icon = "📙";
}
else if(fileName.endsWith(".zip") ||
        fileName.endsWith(".rar")){

    icon = "🗜️";
}
else if(fileName.endsWith(".jpg") ||
        fileName.endsWith(".jpeg") ||
        fileName.endsWith(".png")){

    icon = "🖼️";
}
else if(fileName.endsWith(".mp4")){

    icon = "🎥";
}
else if(fileName.endsWith(".mp3")){

    icon = "🎵";
}
%>

<div class="file-icon">

    <%= icon %>

</div>

    <div class="file-details">

        <div class="file-name"
     title="<%= f.getOriginalName() %>">
    <%= f.getOriginalName() %>
</div>

        <div class="file-meta">

            <%= f.getUploaderName() %>

            <br>

            <%= f.getUploadedAt() %>

        </div>

    </div>

    <a class="btn btn-primary"
       href="<%=request.getContextPath()%>/downloadProjectFile?id=<%=f.getId()%>">

        Download

    </a>

</div>

<%
}
}
%>

</div>
</div>

<hr style="margin:25px 0;">

<div class="workspace-card">

<h3>Recent Activity</h3>

<div class="activity-feed">

<%
if(activities.isEmpty()){
%>

<div class="activity-item">

    No recent activity.

</div>

<%
}else{

for(Activity a : activities){
%>

<div class="activity-item">

    <%
String activity =
        a.getActivity().toLowerCase();

String icon = "📝";

if(activity.contains("uploaded")){

    icon = "📁";
}
else if(activity.contains("deleted")){

    icon = "🗑️";
}
else if(activity.contains("added")){

    icon = "➕";
}
else if(activity.contains("removed")){

    icon = "➖";
}
else if(activity.contains("joined")){

    icon = "👥";
}
else if(activity.contains("completed")){

    icon = "✅";
}
else if(activity.contains("created")){

    icon = "🚀";
}
else if(activity.contains("updated")){

    icon = "✏️";
}
else if(activity.contains("message")){

    icon = "💬";
}
%>

<div class="activity-icon">

    <%= icon %>

</div>

    <div class="activity-content">

        <strong>

            <%= a.getUserName() == null
                ? "Unknown User"
                : a.getUserName() %>

        </strong>

        <br>

        <%= a.getActivity() %>

        <div class="activity-time">

            <%= a.getCreatedAt() %>

        </div>

    </div>

</div>

<%
}
}
%>

</div>
</div>
        </div>
    </div>
<div class="task-board-section">
<h2>Task Board</h2>

<div class="kanban-board">

    <div class="kanban-column todo">

    <h4>

🟡 Todo

<span>

<%= todoCount %>

</span>

</h4>

    <% for(Task t : tasks){
        if("todo".equals(t.getStatus())){
    %>

    <div class="kanban-task">

    <div class="kanban-task-title">

        📝 <%= t.getTitle() %>

    </div>

    <div class="kanban-assignee">

        👤 <%= t.getAssignedTo() %>

    </div>

<%
if(t.getDescription() != null &&
   !t.getDescription().trim().isEmpty()){
%>

    <div class="kanban-description">

        <%= t.getDescription() %>

    </div>

<%
}
%>

</div>

    <% }} %>

</div>

<div class="kanban-column progress">

    <h4>

🔵 In Progress

<span>

<%= progressCount %>

</span>

</h4>

    <% for(Task t : tasks){
        if("in_progress".equals(t.getStatus())){
    %>

    <div class="kanban-task">

    <div class="kanban-task-title">

        📝 <%= t.getTitle() %>

    </div>

    <div class="kanban-assignee">

        👤 <%= t.getAssignedTo() %>

    </div>

<%
if(t.getDescription() != null &&
   !t.getDescription().trim().isEmpty()){
%>

    <div class="kanban-description">

        <%= t.getDescription() %>

    </div>

<%
}
%>

</div>

    <% }} %>

</div>

    <div class="kanban-column completed">

    <h4>

🟢 Completed

<span>

<%= completedCount %>

</span>

</h4>

    <% for(Task t : tasks){
        if("completed".equals(t.getStatus())){
    %>

<div class="kanban-task">

    <div class="kanban-task-title">

        📝 <%= t.getTitle() %>

    </div>

    <div class="kanban-assignee">

        👤 <%= t.getAssignedTo() %>

    </div>

<%
if(t.getDescription() != null &&
   !t.getDescription().trim().isEmpty()){
%>

    <div class="kanban-description">

        <%= t.getDescription() %>

    </div>

<%
}
%>

</div>

    <% }} %>

</div>

</div>
    </div>


</div>

</main>

</div>
<script>

window.onload = function(){

    const chatArea =
        document.querySelector(
            '.workspace-chat-feed');

    if(chatArea){

        chatArea.scrollTop =
            chatArea.scrollHeight;
    }
};
</script>

<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>

</body>
</html>