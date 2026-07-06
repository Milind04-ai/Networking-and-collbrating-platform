<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Application"%>

<%
ArrayList<Application> apps =
(ArrayList<Application>)
request.getAttribute(
    "applications");

if(apps == null){

    apps = new ArrayList<>();
}
%>

<%
int pending = 0;
int accepted = 0;
int rejected = 0;

for(Application a : apps){

    if("pending".equalsIgnoreCase(
            a.getStatus())){

        pending++;
    }
    else if(
        "accepted".equalsIgnoreCase(
            a.getStatus())){

        accepted++;
    }
    else if(
        "rejected".equalsIgnoreCase(
            a.getStatus())){

        rejected++;
    }
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus ? Applications</title>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/global.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/applications.css">
</head>
<body>
<div class="layout">
  <main class="main">
    <div class="page-content">
        
        <div class="applications-hero">

    <div>

        <h1>

            <i class="fa-solid fa-paper-plane"></i>

            My Applications

        </h1>

        <p>

            Track every project you've applied to and monitor your application status.

        </p>

    </div>

</div>

      <!-- Stats -->
      <div class="app-stats">
        <div class="app-stat"><div class="n" style="color:var(--accent)"><%= apps.size() %></div><div class="l">Applications sent</div></div>
        <div class="app-stat"><div class="n" style="color:var(--success)"><%= accepted %></div><div class="l">Accepted</div></div>
        <div class="app-stat"><div class="n stat-pending" style="color:var(--warning)"><%= pending %></div><div class="l">Pending review</div></div>
        <div class="app-stat"><div class="n" style="color:var(--text-secondary)"><%= rejected %></div><div class="l">Not selected</div></div>
      </div>

      <!-- ?? Panel: My Applications ?? -->
      <div id="panel-sent"
     class="tab-panel active">

    <div class="app-layout">

        <div>

    <div class="section-header">

        <h2>
            Sent Applications
        </h2>

        <span
        style="font-size:13px;
        color:var(--text-muted)">

            <%= apps.size() %> total

        </span>

    </div>

<%
if(apps.isEmpty()){
%>

<div class="empty-state">

    <i class="fa-regular fa-paper-plane"></i>

    <h3>

        No Applications Yet

    </h3>

    <p>

        Explore projects and submit your first application.

    </p>

    <a
    class="btn btn-primary"
    href="<%=request.getContextPath()%>/projects">

        Explore Projects

    </a>

</div>

<%
}else{

for(Application a : apps){
%>

<div class="app-card">

    <div class="app-proj-icon">

        <%= a.getProjectTitle().substring(0,1).toUpperCase() %>

    </div>

    <div class="app-info">

        <div class="app-project-name">

            <%= a.getProjectTitle() %>

        </div>

        <div class="app-role">

            Applied as Contributor

        </div>

        <div class="app-meta">

            <span>

                <i class="fa-regular fa-calendar"></i>

                Recently Applied

            </span>

        </div>

    </div>

    <div class="app-actions">

        <% if("accepted".equalsIgnoreCase(a.getStatus())){ %>

            <span class="badge badge-success">

                Accepted

            </span>

        <% } else if("rejected".equalsIgnoreCase(a.getStatus())){ %>

            <span class="badge badge-danger">

                Rejected

            </span>

        <% } else { %>

            <span class="badge badge-warning">

                Pending

            </span>

        <% } %>

        <a class="btn btn-secondary btn-sm" style="background-color: blueviolet;radius:50%; color: white"
           href="<%=request.getContextPath()%>/projectDetails?id=<%=a.getProjectId()%>">

            View Project

        </a>

    </div>
<hr class="app-divider">
</div>
<%
}
}
%>
        </div>
<div>
            <div class="section-header"><h2>Application Insights</h2></div>
            <div style="background:var(--bg-card);border:1px solid var(--border);border-radius:var(--radius-lg);padding:20px">
              <div style="display:flex;flex-direction:column;gap:14px">
                <div style="display:flex;gap:12px">
                  <div style="width:32px;height:32px;border-radius:8px;background:var(--accent-soft);border:1px solid var(--accent-glow);display:flex;align-items:center;justify-content:center;flex-shrink:0;color:var(--accent)">
                    <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>
                  </div>
                  <div><div style="font-size:13px;font-weight:500;margin-bottom:3px">Add a cover note</div><div style="font-size:12px;color:var(--text-secondary)">Applications with cover notes get 2× more responses.</div></div>
                </div>
                <div style="display:flex;gap:12px">
                  <div style="width:32px;height:32px;border-radius:8px;background:rgba(34,197,94,0.1);border:1px solid rgba(34,197,94,0.2);display:flex;align-items:center;justify-content:center;flex-shrink:0;color:var(--success)">
                    <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                  </div>
                  <div><div style="font-size:13px;font-weight:500;margin-bottom:3px">Complete your profile</div><div style="font-size:12px;color:var(--text-secondary)">Full profiles are selected 3× more often.</div></div>
                </div>
                <div style="display:flex;gap:12px">
                  <div style="width:32px;height:32px;border-radius:8px;background:rgba(245,158,11,0.1);border:1px solid rgba(245,158,11,0.2);display:flex;align-items:center;justify-content:center;flex-shrink:0;color:var(--warning)">
                    <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>
                  </div>
                  <div><div style="font-size:13px;font-weight:500;margin-bottom:3px">Add portfolio items</div><div style="font-size:12px;color:var(--text-secondary)">Showcase real work to stand out.</div></div>
                </div>
              </div>
              <a href="/Nexus/profile" class="btn btn-primary btn-sm btn-full" style="margin-top:20px;justify-content:center;background-color: blueviolet;radius:50%">Improve My Profile</a>
            </div>
            </div>
    </div>
</div>
    </div>
  </main>
</div>

<div class="toast-container"></div>
<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>
<script src="<%=request.getContextPath()%>/myjs/applications.js"></script>
<style>
</style>
</body>
</html>
