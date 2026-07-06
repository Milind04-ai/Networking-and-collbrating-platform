<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Project"%>

<%
ArrayList<Project> projects =
(ArrayList<Project>)
request.getAttribute("projects");

String currentUserId =
(String)request.getAttribute(
    "currentUserId");

if(projects == null){
    projects = new ArrayList<>();
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus — Projects</title>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/global.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/projects.css">
</head>
<body>
<div class="layout">
  <main class="main">
    <div class="page-content">
        
        <div class="projects-hero">

    <div>

        <h1>

            <i class="fa-solid fa-folder-open"></i>

            Projects

        </h1>

        <p>

            Discover projects, collaborate with developers,
            and build something amazing together.

        </p>

    </div>

    <button
        class="btn btn-primary"
        data-modal="post-modal">

        <i class="fa-solid fa-plus"></i>

        Create Project

    </button>

</div>

      <!-- Filter bar -->
      <div class="filter-bar">
        <span style="font-size:12px;color:var(--text-muted);font-weight:600;flex-shrink:0">Filter:</span>
        <button class="filter-chip active" data-filter="all">All Projects</button>
        <button class="filter-chip" data-filter="roles">Open Roles</button>
        <button class="filter-chip" data-filter="mine">My Projects</button>
        <div style="width:1px;height:20px;background:var(--border);flex-shrink:0"></div>
        <button class="filter-chip" data-filter="react">React</button>
        <button class="filter-chip" data-filter="python">Python</button>
        <button class="filter-chip" data-filter="mobile">Mobile</button>
        <button class="filter-chip" data-filter="ai">AI / ML</button>
        <div style="margin-left:auto;flex-shrink:0">
          <select class="input" style="width:auto;padding:6px 12px;font-size:13px">
            <option>Newest first</option>
            <option>Most members</option>
            <option>Most open roles</option>
          </select>
        </div>
      </div>

      <div class="projects-layout">
        <!-- Cards column -->
        <div>
          <div style="margin-bottom:14px">
            <div class="search-bar" style="width:100%">
              <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
              <input type="text" id="project-search" placeholder="Search by title, skill or technology…">
            </div>
          </div>
          <div id="project-cards">

<%
for(Project p : projects){
%>

<div class="project-card"
     data-id="<%= p.getId() %>">

    <div class="project-card-head">

        <div class="project-emoji">
            <%= p.getEmoji() %>
        </div>

        <div>

            <div class="project-title">
                <%= p.getTitle() %>
            </div>

            <div class="project-owner">
                <%= p.getOwnerName() %>
            </div>
            
            <div class="project-status">

    <span class="status-chip <%= p.getStatus().toLowerCase() %>">

    <i class="fa-solid fa-circle"></i>

    <%= p.getStatus() %>

</span>

</div>
            
        </div>

    </div>

    <div class="project-desc">
        <%= p.getDescription() %>
    </div>
    
    <hr class="project-divider">
    
    <div class="skill-tags">

<%
if(p.getTags()!=null){

String[] tags =
p.getTags().split(",");

for(String tag : tags){
%>

<span class="skill-tag">

    <%= tag.trim() %>

</span>

<%
}
}
%>

</div>

</div>

<%
}
%>

</div>
        </div>

        <!-- Detail panel -->
        <div class="detail-panel">
          <div class="detail-header">
            <span class="detail-emoji" id="detail-emoji">🌱</span>
            <div class="detail-title" id="detail-title">Select a project</div>
            <div class="detail-meta">
              <span style="display:flex;align-items:center;gap:5px">
                <div class="avatar" style="width:18px;height:18px;font-size:9px;background:linear-gradient(135deg,#22c55e,#38bdf8)">P</div>
                <span id="detail-owner">Priya Mehta</span>
              </span>
              <span class="badge badge-success">Active</span>
            </div>
          </div>
          <div class="detail-body">
            <div class="detail-section">
              <div class="detail-section-title">Tech Stack</div>
              <div class="skill-tags" id="detail-skills"></div>
            </div>
            <div class="detail-section">
              <div class="detail-section-title">Open Roles</div>
              <div id="detail-roles"></div>
            </div>
            <div class="detail-section">
              <div class="detail-section-title">Current Team</div>
              <div id="detail-team"></div>
            </div>
            <form
    action="<%=request.getContextPath()%>/applyProject"
    method="post">

    <input
        type="hidden"
        name="projectId"
        id="projectIdField">

    <button
        type="submit"
        id="apply-btn"
        class="btn btn-primary apply-btn">

        Apply to this Project

    </button>

</form>
    <a id="workspaceBtn"
   class="btn btn-secondary"
   style="width:100%;margin-top:10px;">

    Open Workspace

</a>

<a id="viewProjectBtn"
   class="btn btn-ghost"
   style="width:100%;margin-top:10px;">

    View Details

</a>
            <div style="text-align:center;margin-top:10px">
              <a href="/Nexus/chat" style="font-size:13px;color:var(--accent);text-decoration:none">Message the owner instead →</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</div>

<!-- FAB post button -->
<button class="btn btn-primary post-project-btn" data-modal="post-modal">
  <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
  Post Project
</button>

<!-- Post project modal -->
<div class="modal-overlay" id="post-modal">
  <div class="modal" style="max-width:520px">
    <div class="modal-header">
      <h3>Post a New Project</h3>
      <button class="modal-close"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg></button>
    </div>
    <form id="post-project-form" action="../projects/create" method="POST">
      <div style="margin-bottom:14px">
        <label>Project title</label>
        <input class="input" name="title" placeholder="e.g. EcoTrack — Sustainability Dashboard" required>
      </div>
      <div style="margin-bottom:14px">
        <label>Description</label>
        <textarea class="input" name="description" rows="3" placeholder="Describe what you're building and who you need…" style="resize:vertical"></textarea>
      </div>
      <div style="margin-bottom:14px">
        <label>Skills / Tech stack</label>
        <input class="input" name="skills" placeholder="React, Node.js, PostgreSQL…">
      </div>
      <div style="margin-bottom:20px">
        <label>Open roles (comma-separated)</label>
        <input class="input" name="roles" placeholder="Backend Developer, Data Analyst…">
      </div>
      <div style="display:flex;gap:10px;justify-content:flex-end">
        <button type="button" class="btn btn-ghost btn-sm modal-close">Cancel</button>
        <button type="submit" class="btn btn-primary btn-sm">
          <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="22" y1="2" x2="11" y2="13"/><polygon points="22 2 15 22 11 13 2 9 22 2"/></svg>
          Post Project
        </button>
      </div>
    </form>
  </div>
</div>

<div class="toast-container"></div>
<script>
const currentUserId =
"<%= currentUserId %>";

const PROJECTS = [

<%
for(int i=0;i<projects.size();i++){

    Project p = projects.get(i);
%>

{
    id : "<%= p.getId() %>",
    ownerId : "<%= p.getOwnerId() %>",
    emoji : "<%= p.getEmoji() %>",
    title : "<%= p.getTitle() %>",
    owner : "<%= p.getOwnerName() %>",
    desc : "<%= p.getDescription() %>",
    status : "<%= p.getStatus() %>",
    rolesHtml : `<%= p.getOpenRolesHtml() %>`,
    teamHtml : `<%= p.getTeamHtml() %>`,
    skills : [
        "<%= p.getTags() == null ? "" : p.getTags() %>"
    ]
}

<%= i < projects.size()-1 ? "," : "" %>

<%
}
%>

];

</script>
<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>
<script src="<%=request.getContextPath()%>/myjs/projects.js"></script>
</body>
</html>
