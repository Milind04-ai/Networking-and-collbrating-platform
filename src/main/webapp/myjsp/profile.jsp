<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Project"%>

<%
ArrayList<Project> portfolioProjects =
(ArrayList<Project>)
request.getAttribute("portfolioProjects");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus — Profile</title>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/mycss/global.css">

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/mycss/profile.css">
</head>
<body>
<div class="layout">
  <main class="main">
    <div class="page-content">

      <div style="display:flex;justify-content:flex-end;gap:10px;margin-bottom:20px">
        <a href="#" class="btn btn-ghost btn-sm">Preview public profile</a>
        <button class="btn btn-primary btn-sm" id="open-edit-modal">
          <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
          Edit Profile
        </button>
      </div>

      <div class="profile-grid">
        <!-- Left card -->
        <div>
          <div class="profile-card">
            <div class="profile-cover" id="cover-photo">
              <div class="cover-edit-hint">
                <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/><circle cx="12" cy="13" r="4"/></svg>
                Change cover
              </div>
              <div class="profile-avatar-wrap">
                <div class="p-avatar">A</div>
              </div>
            </div>
            <div class="profile-body">
              <div class="profile-name" id="display-name">${fullName}</div>
              <div class="profile-headline" id="display-headline">${headline}</div>
              <div class="profile-location">
                <svg width="12" height="12" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/></svg>
                ${location}
              </div>
              <div class="profile-stats">
                <div class="pstat"><div class="n"><%= request.getAttribute("connectionCount") %></div><div class="l">Connections</div></div>
                <div class="pstat"><div class="n"><%= request.getAttribute("projectCount") %></div><div class="l">Projects</div></div>
                <div class="pstat"><div class="n"><%= request.getAttribute("applicationCount") %></div><div class="l">Applications</div></div>
              </div>
              <div class="skills-box">
                <div class="lbl">Top Skills</div>
                <div class="skills-list">
<%@page import="java.util.HashSet"%>

<%
HashSet<String> skills =
(HashSet<String>)
request.getAttribute("skills");
%>

<%

if(skills != null){

for(String skill : skills){

%>

<span class="skill-tag">

    <%= skill %>

</span>

<%

}

}

%>
                </div>
              </div>
              <div class="profile-actions">
                <a href="/Nexus/chat" class="btn btn-primary btn-sm" style="justify-content:center">
                  <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
                  Message
                </a>
                <a href="#" class="btn btn-ghost btn-sm" style="justify-content:center">
                  <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                  Connect
                </a>
              </div>
            </div>
          </div>
        </div>

        <!-- Right sections -->
        <div>
          <div class="tabs">
            <div class="tab active" data-tab="overview">Overview</div>
            <div class="tab" data-tab="portfolio">Portfolio</div>
            <div class="tab" data-tab="projects">Projects</div>
            <div class="tab" data-tab="activity">Activity</div>
          </div>

          <!-- About -->
          <div class="section-card">
            <div class="sc-head"><h3>About</h3><a href="#" class="edit-link" id="open-edit-modal-2"><svg width="13" height="13" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>Edit</a></div>
            <p class="about-text" id="about-text">${bio}</p>
            <span class="see-more" id="see-more-btn">See more</span>
          </div>

          <!-- Portfolio -->
          <div class="section-card">
            <div class="sc-head"><h3>Portfolio</h3><a href="#" class="edit-link"><svg width="13" height="13" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>Add item</a></div>
            <div class="portfolio-grid">

<%
if(portfolioProjects != null &&
   !portfolioProjects.isEmpty()){

    for(Project p : portfolioProjects){
%>

<div class="portfolio-item">

    <div class="portfolio-thumb">

        <%= p.getEmoji() %>

    </div>

    <div class="portfolio-info">

        <div class="portfolio-title">

            <%= p.getTitle() %>

        </div>

        <div class="portfolio-type">

            <%= p.getTags() %>

        </div>

        <span class="status-chip">

            <%= p.getStatus() %>

        </span>

    </div>

</div>

<%
    }
}else{
%>

<div class="empty-state">

    No portfolio projects yet.

</div>

<%
}
%>

</div>
          </div>

          <!-- Experience -->
          <div class="section-card">

    <div class="sc-head">

        <h3>Recent Contributions</h3>

    </div>

    <p style="color:var(--text-secondary);">

        Coming soon...

    </p>

</div>

          <!-- Activity heatmap -->
          <div class="section-card">
            <div class="sc-head"><h3>Activity</h3><span style="font-size:12px;color:var(--text-muted)">Last 16 weeks</span></div>
            <div class="heatmap" id="heatmap"></div>
            <div style="display:flex;align-items:center;gap:6px;margin-top:12px;font-size:12px;color:var(--text-muted)">
              <span>Less</span>
              <div class="hm-cell" style="width:10px;height:10px"></div><div class="hm-cell l1" style="width:10px;height:10px"></div><div class="hm-cell l2" style="width:10px;height:10px"></div><div class="hm-cell l3" style="width:10px;height:10px"></div><div class="hm-cell l4" style="width:10px;height:10px"></div>
              <span>More</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</div>

<!-- Edit modal -->
<div class="modal-overlay" id="edit-modal">
  <div class="modal">
    <div class="modal-header">
      <h3>Edit Profile</h3>
      <button class="modal-close" id="close-modal">
        <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
      </button>
    </div>
    <form id="edit-form" action="${pageContext.request.contextPath}/profile/update" method="POST">
      <div style="margin-bottom:14px"><label>Full name</label><input class="input" name="full_name" id="edit-name" value="${fullName}"></div>
      <div style="margin-bottom:14px"><label>Headline</label><input class="input" name="headline" id="edit-headline" value="${headline}"></div>
      <div style="margin-bottom:14px">
        <label>Bio</label>
        <textarea class="input" name="bio" id="edit-bio" rows="4" style="resize:vertical">${bio}</textarea>
        <div style="font-size:11px;color:var(--text-muted);text-align:right;margin-top:4px" id="bio-count">0/500</div>
      </div>
      <div style="margin-bottom:20px"><label>Location</label><input class="input" name="location" value="${location}"></div>
      <div style="display:flex;gap:10px;justify-content:flex-end">
        <button type="button" class="btn btn-ghost btn-sm" id="cancel-modal">Cancel</button>
        <button type="submit" class="btn btn-primary btn-sm">Save Changes</button>
      </div>
    </form>
  </div>
</div>

<div class="toast-container"></div>
<script src="${pageContext.request.contextPath}/myjs/shared.js"></script>
<script src="${pageContext.request.contextPath}/myjs/profile.js"></script>
</body>
</html>

