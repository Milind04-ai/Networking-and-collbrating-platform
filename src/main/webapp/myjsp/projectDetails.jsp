<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="myclasses.Project"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.ProjectMember"%>


<%
Project p =
(Project) request.getAttribute("project");

ArrayList<ProjectMember> members =
(ArrayList<ProjectMember>) request.getAttribute("members");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project Details</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/projectDetails.css">
<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">

</head>
<body>

<div class="layout">

<aside id="sidebar"></aside>

<main class="main">
    
    <script>
document.addEventListener('DOMContentLoaded', () => {

    renderSidebar('projects');

    renderTopbar('Project Details');

    loadNotificationCounts();
});
</script>

<div class="page-content">

<div class="project-header-card">

    <div class="project-title-row">

        <div class="project-emoji">
            <%= p.getEmoji() %>
        </div>

        <div>

            <h1 class="project-title">
                <%= p.getTitle() %>
            </h1>

            <div class="project-meta">

                <span>
                    Owner:
                    <b><%= p.getOwnerName() %></b>
                </span>

                <span class="status-badge">
                    <%= p.getStatus() %>
                </span>

            </div>

        </div>

    </div>

</div>

<div class="project-grid">

    <div class="project-main-card">

        <h3>Description</h3>

        <p>
            <%= p.getDescription() %>
        </p>

        <h3>Skills</h3>

        <div class="skills-container">

            <%
            if(p.getTags()!=null){

                String[] tags =
                        p.getTags().split(",");

                for(String tag : tags){
            %>

            <span class="skill-badge">
                <%= tag.trim() %>
            </span>

            <%
                }
            }
            %>

        </div>

    </div>

    <div class="project-side-card">

        <h3>Team Members</h3>

        <ul class="member-list">

        <%
        if(members != null){

            for(ProjectMember member : members){
        %>

            <li>

    <a href="<%=request.getContextPath()%>/userProfile?id=<%=member.getUserId()%>">

        <%=member.getFullName()%>

    </a>

</li>

        <%
            }
        }
        %>

        </ul>

    </div>

</div>


<br>
<%
Boolean isOwner =
(Boolean) request.getAttribute("isOwner");

Boolean isMember =
(Boolean) request.getAttribute("isMember");

Boolean alreadyApplied =
(Boolean) request.getAttribute("alreadyApplied");

String applicationStatus =
(String) request.getAttribute("applicationStatus");

if(isOwner == null) isOwner = false;
if(isMember == null) isMember = false;
if(alreadyApplied == null) alreadyApplied = false;
%>

<br>

<div class="project-actions-card">

<% if(isOwner){ %>

<a class="btn btn-primary"
href="<%=request.getContextPath()%>/manageApplications">

Manage Applications

</a>

<% } else if(isMember){ %>

<button class="btn btn-success">

You are a Team Member

</button>

<% } else if(alreadyApplied){ %>

<button class="btn btn-secondary">

Application
<%= applicationStatus %>

</button>

<% } else { %>

<form method="post"
      action="<%=request.getContextPath()%>/applyProject">

<input type="hidden"
       name="projectId"
       value="<%= p.getId() %>">

<button type="submit"
        class="btn btn-primary">

Apply To Project

</button>

</form>

<% } %>

</div>

<br><br>

<a class="btn btn-primary"
href="<%=request.getContextPath()%>/projects">

Back To Projects

</a>

</div>

</main>

</div>

<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>

</body>
</html>