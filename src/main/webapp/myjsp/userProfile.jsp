<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="myclasses.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Project"%>

<%
User user =
(User) request.getAttribute("profileUser");

Integer projectCount =
(Integer) request.getAttribute("projectCount");

Boolean isOwnProfile =
(Boolean)request.getAttribute(
        "isOwnProfile");

String connectionStatus =
(String)request.getAttribute(
        "connectionStatus");

Integer connectionCount =
(Integer)request.getAttribute(
        "connectionCount");

if(connectionCount == null)
    connectionCount = 0;

if(projectCount == null){
    projectCount = 0;
}

if(isOwnProfile == null)
    isOwnProfile = false;

if(connectionStatus == null)
    connectionStatus = "none";
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>User Profile</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/userProfile.css">
<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">

</head>

<body>

<div class="layout">

<aside id="sidebar"></aside>

<main class="main">

<script>

document.addEventListener(
'DOMContentLoaded',
() => {

    renderSidebar('');

    renderTopbar('User Profile');

    loadNotificationCounts();
});

</script>

<div class="page-content">

<div class="profile-header-card">

    <div class="profile-avatar">

        <%= user.getFullName()
                .substring(0,1)
                .toUpperCase() %>

    </div>

    <div class="profile-info">

        <h1>
            <%= user.getFullName() %>
        </h1>

        <p class="profile-headline">

            <%= user.getHeadline() == null
                    ? "Nexus User"
                    : user.getHeadline() %>

        </p>

        <p class="profile-location">

            📍
            <%= user.getLocation() == null
                    ? "Unknown"
                    : user.getLocation() %>

        </p>

    </div>

</div>
                    
                    <div class="stats-grid">

    <div class="stat-card">

        <h2>
            <%= projectCount %>
        </h2>

        <span>Projects</span>

    </div>

    <div class="stat-card">

        <h2>
            0
        </h2>

        <span>Profile Views</span>

    </div>

    <div class="stat-card">

        <h2>
            <%= connectionCount %>
        </h2>

        <span>Connections</span>

    </div>

</div>
<br>

<div class="profile-card">

    <h2>About</h2>

    <p>

        <%= user.getBio() == null
        ? "No bio available"
        : user.getBio() %>

    </p>

</div>

<br>

<h2>Projects</h2>
<br>

<%
ArrayList<Project> projects =
(ArrayList<Project>)
request.getAttribute("projects");

if(projects != null){

for(Project p : projects){
%>

<div class="user-project-card">

<h4>
<%= p.getEmoji() %>
<%= p.getTitle() %>
</h4>

<p>
<%= p.getDescription() %>
</p>

<a href="<%=request.getContextPath()%>/projectDetails?id=<%=p.getId()%>">

View Project

</a>

</div>

<%
}
}
%>

<% if(!isOwnProfile){ %>

    <% if("none".equals(connectionStatus)){ %>

        <form method="post"
              action="<%=request.getContextPath()%>/sendConnection">

            <input type="hidden"
                   name="receiverId"
                   value="<%= user.getId() %>">

            <button class="btn btn-primary">

                Connect

            </button>

        </form>

    <% } else if("pending".equals(connectionStatus)){ %>

        <button class="btn btn-secondary">

            Request Pending

        </button>

    <% } else if("accepted".equals(connectionStatus)){ %>

        <button class="btn btn-success">

            Connected ✓

        </button>
    
        <a class="btn btn-primary"
       href="<%=request.getContextPath()%>/chat?receiverId=<%= user.getId() %>">

        Message

    </a>

    <% } %>

<% } %>

</div>

</main>

</div>

<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>

</body>
</html>