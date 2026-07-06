<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Project"%>
<%@page import="myclasses.Application"%>

<%
ArrayList<Project> recentProjects =
(ArrayList<Project>)
request.getAttribute(
        "recentProjects");

Integer projectCount =
(Integer)request.getAttribute(
        "projectCount");

Integer connectionCount =
(Integer)request.getAttribute(
        "connectionCount");

Integer applicationCount =
(Integer)request.getAttribute(
        "applicationCount");

Integer unreadMessages =
(Integer)request.getAttribute(
        "unreadMessages");

String fullName =
(String)request.getAttribute(
        "fullName");

String greeting =
(String)request.getAttribute(
        "greeting");

ArrayList<Application> recentApplications =
(ArrayList<Application>)
request.getAttribute(
        "recentApplications");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus Dashboard</title>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/global.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/dashboard.css">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
</head>
<body>
<div class="layout">
  <main class="main">
    <div class="page-content">

      <!-- Welcome banner -->
      <div class="welcome-banner">
        <div class="welcome-text">
          <h2>
<%= greeting %>,
<%= fullName %>
</h2>
          <p>

You currently have

<strong>

<%= projectCount %>

active projects</strong>,

<strong>

<%= applicationCount %>

pending applications</strong>

and

<strong>

<%= unreadMessages %>

unread messages</strong>.

Let's make today productive ?

</p>
          <div class="welcome-actions">
            <a href="/Nexus/applications" class="btn btn-primary btn-sm">View Applications</a>
            <a href="/Nexus/profile" class="btn btn-ghost btn-sm">Complete Profile</a>
          </div>
        </div>
        <div class="completion-wrap">
          <div class="completion-ring" style="background:conic-gradient(var(--accent) 234deg,var(--bg-elevated) 234deg)">
            <div class="completion-ring-inner">65%</div>
          </div>
          <div class="completion-label">Profile strength</div>
        </div>
      </div>

<div class="quick-actions">

    <a href="<%=request.getContextPath()%>/projects/create"
       class="action-card">

        <div>

            <i class="fas fa-plus"></i><h4>Create Project</h4>

            <span>Start a new collaboration</span>

        </div>

    </a>

    <a href="<%=request.getContextPath()%>/projects"
       class="action-card">

        <div>

            <i class="fas fa-folder-open"></i><h4>My Projects</h4>

            <span>Manage your projects</span>

        </div>

    </a>

    <a href="<%=request.getContextPath()%>/messages"
       class="action-card">

        <div>

            <i class="fas fa-comments"></i><h4>Messages</h4>

            <span>Open your chats</span>

        </div>

    </a>

    <a href="<%=request.getContextPath()%>/profile"
       class="action-card">

        <div>

            <i class="fas fa-user"></i><h4>Profile</h4>

            <span>View your profile</span>

        </div>

    </a>

</div>

      <!-- Stats -->
      <div class="stats-row">
        <div class="stat-mini">
          <div class="stat-icon purple"><svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg></div>
          <div><div class="stat-num"
     data-count="<%= projectCount %>">

    <%= projectCount %>

</div><div class="stat-lbl">Active projects</div><div class="stat-trend up">? 1 this week</div></div>
        </div>
        <div class="stat-mini">
          <div class="stat-icon green"><svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></div>
          <div><div class="stat-num"
     data-count="<%= connectionCount %>">

    <%= connectionCount %>

</div><div class="stat-lbl">Connections</div><div class="stat-trend up">? 5 new</div></div>
        </div>
        <div class="stat-mini">
          <div class="stat-icon amber"><svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14,2 14,8 20,8"/></svg></div>
          <div><div class="stat-num"
     data-count="<%= applicationCount %>">

    <%= applicationCount %>

</div><div class="stat-lbl">Applications</div><div class="stat-trend up">? 3 pending</div></div>
        </div>
        <div class="stat-mini">
          <div class="stat-icon blue"><svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg></div>
          <div><div class="stat-num"
     data-count="<%= unreadMessages %>">

    <%= unreadMessages %>

</div></div>
        </div>
      </div>
    
    <div class="dashboard-card">

    <div class="section-header">

        <h3>

            <i class="fa-solid fa-folder-open"></i>

            Recent Projects

        </h3>

        <a href="<%=request.getContextPath()%>/projects">

            View All

        </a>

    </div>

<%
if(recentProjects == null ||
   recentProjects.isEmpty()){
%>

    <div class="empty-state">

        No projects created yet.

    </div>

<%
}
else{

for(Project p : recentProjects){
%>

<div class="recent-project">

    <div class="recent-left">

        <div class="project-icon">

            <i class="fa-solid fa-rocket"></i>

        </div>

        <div>

            <div class="recent-project-title">

                <%= p.getTitle() %>

            </div>

            <div class="recent-project-status">

                <span class="status-chip
                <%= p.getStatus().toLowerCase() %>">

                    <%= p.getStatus() %>

                </span>

            </div>

        </div>

    </div>

    <a class="btn btn-primary btn-sm"
       href="<%=request.getContextPath()%>/workspace?id=<%=p.getId()%>">

        <i class="fa-solid fa-arrow-up-right-from-square"></i>

        Open

    </a>

</div>
<%
}
}
%>

</div>
          

      <!-- Feed + Sidebar -->
      <div class="feed-grid">
        <div>
          <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:14px">
            <div class="feed-filters">
              <button class="feed-filter active" data-filter="all">All</button>
              <button class="feed-filter" data-filter="project">Projects</button>
              <button class="feed-filter" data-filter="connection">People</button>
              <button class="feed-filter" data-filter="update">Updates</button>
            </div>
            <span id="live-dot" style="width:8px;height:8px;background:var(--success);border-radius:50%;display:inline-block;box-shadow:0 0 6px var(--success);transition:opacity 0.3s" title="Live feed"></span>
          </div>

          <div class="feed-item" data-type="project">
            <div class="feed-item-header">
              <div class="feed-actor">
                <div class="avatar" style="width:36px;height:36px;font-size:14px;background:linear-gradient(135deg,#22c55e,#38bdf8)">P</div>
                <div><div style="font-size:14px;font-weight:500">Priya Mehta</div><div class="feed-meta">UI Designer · 2 hours ago</div></div>
              </div>
              <span class="badge badge-success">New Project</span>
            </div>
            <p class="feed-action">Posted a new project ? <span>EcoTrack Sustainability Dashboard</span></p>
            <div class="project-preview">
              <div class="project-icon">?</div>
              <div style="flex:1;min-width:0">
                <div style="font-size:14px;font-weight:600;margin-bottom:4px">EcoTrack ? Sustainability Dashboard</div>
                <div style="font-size:13px;color:var(--text-secondary);margin-bottom:8px">Real-time carbon footprint tracker. Looking for backend dev &amp; data analyst.</div>
                <div class="skill-tags"><span class="skill-tag">React</span><span class="skill-tag">Node.js</span><span class="skill-tag">PostgreSQL</span><span class="skill-tag">D3.js</span></div>
              </div>
            </div>
            <div class="feed-actions">
              <a href="../projects" class="feed-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg> View project</a>
              <button class="feed-btn like-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg> <span class="like-count">24</span> interested</button>
            </div>
          </div>

          <div class="feed-item" data-type="project">
            <div class="feed-item-header">
              <div class="feed-actor">
                <div class="avatar" style="width:36px;height:36px;font-size:14px;background:linear-gradient(135deg,#f59e0b,#ef4444)">R</div>
                <div><div style="font-size:14px;font-weight:500">Rahul Kumar</div><div class="feed-meta">Full-stack Dev · 5 hours ago</div></div>
              </div>
              <span class="badge badge-accent">2 Roles Open</span>
            </div>
            <p class="feed-action">Looking for collaborators on <span>MedConnect Healthcare API</span></p>
            <div class="project-preview">
              <div class="project-icon">?</div>
              <div style="flex:1;min-width:0">
                <div style="font-size:14px;font-weight:600;margin-bottom:4px">MedConnect ? Healthcare API Platform</div>
                <div style="font-size:13px;color:var(--text-secondary);margin-bottom:8px">Connecting hospitals with labs. Need mobile dev &amp; security expert.</div>
                <div class="skill-tags"><span class="skill-tag">Java</span><span class="skill-tag">Spring Boot</span><span class="skill-tag">Flutter</span><span class="skill-tag">AWS</span></div>
              </div>
            </div>
            <div class="feed-actions">
              <a href="../projects" class="feed-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg> View project</a>
              <button class="feed-btn like-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg> <span class="like-count">18</span> interested</button>
            </div>
          </div>

          <div class="feed-item" data-type="connection">
            <div class="feed-item-header">
              <div class="feed-actor">
                <div class="avatar" style="width:36px;height:36px;font-size:14px;background:linear-gradient(135deg,#6c63ff,#a855f7)">S</div>
                <div><div style="font-size:14px;font-weight:500">Sana Ali</div><div class="feed-meta">Product Designer · 1 day ago</div></div>
              </div>
              <span class="badge badge-muted">New Connection</span>
            </div>
            <p class="feed-action">You and <span>Sana Ali</span> are now connected. Check out their portfolio.</p>
            <div style="display:flex;gap:10px;margin-top:8px">
              <a href="../profile" class="btn btn-ghost btn-sm">View Profile</a>
              <a href="../chat" class="btn btn-ghost btn-sm">Send Message</a>
            </div>
          </div>

          <div class="feed-item" data-type="update">
            <div class="feed-item-header">
              <div class="feed-actor">
                <div class="avatar" style="width:36px;height:36px;font-size:14px;background:linear-gradient(135deg,#38bdf8,#6c63ff)">K</div>
                <div><div style="font-size:14px;font-weight:500">Kavya Reddy</div><div class="feed-meta">ML Engineer · 2 days ago</div></div>
              </div>
              <span class="badge badge-warning">Update</span>
            </div>
            <p class="feed-action">SmartHire just crossed <span>500 users</span>! Looking for a data engineer to scale the pipeline.</p>
            <div class="feed-actions">
              <a href="../projects" class="feed-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg> See project</a>
              <button class="feed-btn like-btn"><svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg> <span class="like-count">41</span> claps</button>
            </div>
          </div>
        </div>

        <!-- Right widgets -->
        <div>
          <div class="widget">

    <div class="widget-title">

        <i class="fa-solid fa-user-check"></i>

        Pending Applications

    </div>

<%
if(recentApplications == null ||
   recentApplications.isEmpty()){
%>

    <div class="empty-state">

        No pending applications.

    </div>

<%
}
else{

for(Application app : recentApplications){
%>

<div class="application-card">

    <div>

        <div class="application-name">

            <%= app.getApplicantName() %>

        </div>

        <div class="application-project">

            Applied to:
            <%= app.getProjectTitle() %>

        </div>

    </div>

    <a class="btn btn-primary btn-sm"
       href="<%=request.getContextPath()%>/manageApplications">

        Review

    </a>

</div>

<%
}
}
%>

</div>

          <div class="widget">

    <div class="widget-title">

        <i class="fa-solid fa-chart-line"></i>

        Project Insights

    </div>

    <div class="insight-item">

        <span>

            <i class="fa-solid fa-folder-open"></i>

            Total Projects

        </span>

        <strong>

            <%= projectCount %>

        </strong>

    </div>

    <div class="insight-item">

        <span>

            <i class="fa-solid fa-file-circle-check"></i>

            Applications

        </span>

        <strong>

            <%= applicationCount %>

        </strong>

    </div>

    <div class="insight-item">

        <span>

            <i class="fa-solid fa-users"></i>

            Connections

        </span>

        <strong>

            <%= connectionCount %>

        </strong>

    </div>

    <div class="insight-item">

        <span>

            <i class="fa-solid fa-comments"></i>

            Messages

        </span>

        <strong>

            <%= unreadMessages %>

        </strong>

    </div>

</div>

          <div class="widget" style="background:linear-gradient(135deg,var(--bg-card),var(--bg-elevated));border-color:var(--accent-glow)">
            <div style="font-size:12px;color:var(--accent);font-weight:600;margin-bottom:8px">? Boost your visibility</div>
            <p style="font-size:13px;color:var(--text-secondary);line-height:1.6">Adding 2+ portfolio items increases your visibility by <strong style="color:var(--text-primary)">3×</strong> in project searches.</p>
            <a href="../profile" class="btn btn-primary btn-sm btn-full" style="margin-top:14px;justify-content:center">Add Portfolio Item</a>
          </div>
        </div>
      </div>
    </div>
  </main>
</div>
<div class="toast-container"></div>
<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>
<script src="<%=request.getContextPath()%>/myjs/dashboard.js"></script>
</body>
</html>
