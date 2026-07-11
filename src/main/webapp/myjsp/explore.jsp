<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.User"%>
<%@page import="myclasses.Project"%>

<%
ArrayList<User> users =
(ArrayList<User>)
request.getAttribute("users");

ArrayList<Project> projects =
(ArrayList<Project>)
request.getAttribute("projects");

if(users == null){
    users = new ArrayList<>();
}

if(projects == null){
    projects = new ArrayList<>();
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus — Explore</title>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/global.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/explore.css">
</head>
<script>
document.addEventListener('DOMContentLoaded', () => {

    renderSidebar('explore');

    renderTopbar('Explore');

    loadNotificationCounts();

});
</script>
<body>
<div class="layout">
    <aside id="sidebar"></aside>
  <main class="main">
    <div class="page-content">

      <!-- Hero search -->
      <div class="explore-hero">
        <div class="hero-eyebrow">Discover</div>
        <h1>Find your next <span>collaboration</span></h1>
        <p>Search for projects, people, skills or companies across the Nexus network</p>
        <form id="hero-search-form"
      action="<%=request.getContextPath()%>/explore"
      method="get">
          <div class="hero-search">
            <input class="input"
       type="text"
       name="search"
       value="<%=request.getParameter("search") == null ? "" : request.getParameter("search") %>"
       placeholder="React developers, ML projects, UX designers…">
            <button class="btn btn-primary" type="submit">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
              Search
            </button>
          </div>
        </form>
        <div class="search-suggestions">
          <span style="font-size:13px;color:var(--text-muted)">Popular:</span>
          <a class="sug-tag" href="#">React</a>
          <a class="sug-tag" href="#">Python</a>
          <a class="sug-tag" href="#">ML Engineer</a>
          <a class="sug-tag" href="#">Open source</a>
          <a class="sug-tag" href="#">Flutter</a>
          <a class="sug-tag" href="#">Startup</a>
          <a class="sug-tag" href="#">LangChain</a>
        </div>
      </div>

      <!-- Section pills -->
      <div class="section-pills">
        <button class="section-pill active" data-section="all">All</button>
        <button class="section-pill" data-section="people">People</button>
        <button class="section-pill" data-section="projects">Projects</button>
      </div>

      <div class="explore-layout">
        <!-- Filter sidebar -->
        <div class="filter-sidebar">
          <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:16px">
            <div style="font-size:14px;font-weight:600">Filters</div>
            <button id="clear-filters" style="font-size:12px;color:var(--accent);background:none;border:none;cursor:pointer;padding:0">Clear all</button>
          </div>

          <div style="margin-bottom:14px">
            <input class="input" id="filter-input" type="text" placeholder="Filter results…" style="font-size:13px;padding:8px 12px">
          </div>

          <div class="divider"></div>

          <div class="filter-group">
            <div class="filter-group-title">Role type</div>
            <label class="filter-option" data-filter="student"><div class="filter-checkbox checked"></div>Student<span class="filter-count">342</span></label>
            <label class="filter-option" data-filter="developer"><div class="filter-checkbox checked"></div>Developer<span class="filter-count">218</span></label>
            <label class="filter-option" data-filter="designer"><div class="filter-checkbox"></div>Designer<span class="filter-count">95</span></label>
            <label class="filter-option" data-filter="recruiter"><div class="filter-checkbox"></div>Recruiter<span class="filter-count">64</span></label>
            <label class="filter-option" data-filter="hr"><div class="filter-checkbox"></div>HR Professional<span class="filter-count">41</span></label>
          </div>

          <div class="divider"></div>

          <div class="filter-group">
            <div class="filter-group-title">Project status</div>
            <label class="filter-option" data-filter="open"><div class="filter-checkbox checked"></div>Open roles<span class="filter-count">87</span></label>
            <label class="filter-option" data-filter="active"><div class="filter-checkbox"></div>Active<span class="filter-count">143</span></label>
            <label class="filter-option" data-filter="done"><div class="filter-checkbox"></div>Completed<span class="filter-count">56</span></label>
          </div>

          <div class="divider"></div>

          <div class="filter-group">
            <div class="filter-group-title">Skills</div>
            <label class="filter-option" data-filter="react"><div class="filter-checkbox checked"></div>React<span class="filter-count">198</span></label>
            <label class="filter-option" data-filter="python"><div class="filter-checkbox checked"></div>Python<span class="filter-count">176</span></label>
            <label class="filter-option" data-filter="nodejs"><div class="filter-checkbox"></div>Node.js<span class="filter-count">154</span></label>
            <label class="filter-option" data-filter="flutter"><div class="filter-checkbox"></div>Flutter<span class="filter-count">98</span></label>
            <label class="filter-option" data-filter="ml"><div class="filter-checkbox"></div>Machine Learning<span class="filter-count">87</span></label>
          </div>

          <div class="divider"></div>

          <div class="filter-group">
            <div class="filter-group-title">Location</div>
            <label class="filter-option" data-filter="delhi"><div class="filter-checkbox checked"></div>Delhi<span class="filter-count">89</span></label>
            <label class="filter-option" data-filter="bangalore"><div class="filter-checkbox"></div>Bangalore<span class="filter-count">167</span></label>
            <label class="filter-option" data-filter="mumbai"><div class="filter-checkbox"></div>Mumbai<span class="filter-count">121</span></label>
            <label class="filter-option" data-filter="remote"><div class="filter-checkbox"></div>Remote<span class="filter-count">234</span></label>
          </div>
        </div>

        <!-- Results -->
        <div>
          <!-- People section -->
          <div id="section-people">
            <div class="results-header">
              <h2>People <span style="font-size:14px;color:var(--text-muted);font-weight:400">· <span id="people-count">6</span> results</span></h2>
              <select class="input" style="width:auto;padding:6px 10px;font-size:13px">
                <option>Best match</option>
                <option>Most connections</option>
                <option>Recently joined</option>
              </select>
            </div>
            <div class="people-grid">

<%
for(User u : users){
%>

<div class="person-card">

    <div class="avatar"
         style="
         width:56px;
         height:56px;
         margin:auto;
         font-size:20px;">

        <%= u.getFullName()
              .substring(0,1)
              .toUpperCase() %>

    </div>

    <div class="person-name">
        <%= u.getFullName() %>
    </div>

    <div class="person-headline">
        <%= u.getHeadline() == null
                ? "Nexus User"
                : u.getHeadline() %>
    </div>

    <div class="person-mutuals">
        📍
        <%= u.getLocation() == null
                ? "Unknown"
                : u.getLocation() %>
    </div>

    <form method="post"
      action="<%=request.getContextPath()%>/sendConnection">

    <input type="hidden"
           name="receiverId"
           value="<%=u.getId()%>">

    <button class="btn btn-ghost btn-sm"
            style="width:100%">
        Connect
    </button>
    
    <a class="btn btn-ghost btn-sm"
   style="width:100%"
   href="<%=request.getContextPath()%>/userProfile?id=<%=u.getId()%>">

    View Profile

</a>

</form>

</div>

<%
}
%>

</div>
            <a href="#" style="font-size:13px;color:var(--accent);text-decoration:none;display:block;margin-bottom:28px">See all people →</a>
          </div>

          <!-- Projects section -->
          <div id="section-projects">
            <div class="results-header">
              <h2>Projects <span style="font-size:14px;color:var(--text-muted);font-weight:400">· <span id="project-count">4</span> open</span></h2>
              <a href="<%=request.getContextPath()%>/projects" style="font-size:13px;color:var(--accent);text-decoration:none">See all →</a>
            </div>
            <div class="project-results-grid">

<%
for(Project p : projects){
%>

<div class="exp-project-card">

    <div class="epc-head">

        <div class="epc-emoji">

            <%= p.getEmoji() == null
                    ? "🚀"
                    : p.getEmoji() %>

        </div>

        <div>

            <div class="epc-title">
                <%= p.getTitle() %>
            </div>

            <div class="epc-owner">
                by
                <%= p.getOwnerName() %>
            </div>

        </div>

    </div>

    <p style="margin-bottom:10px">

        <%= p.getDescription() %>

    </p>

    <div style="
    font-size:12px;
    color:var(--text-muted);
    margin-bottom:10px;">

        Skills:
        <%= p.getTags() == null
                ? "Not specified"
                : p.getTags() %>

    </div>

    <a class="btn btn-primary btn-sm"
       href="<%=request.getContextPath()%>/projectDetails?id=<%= p.getId() %>">

        View Project

    </a>

</div>

<%
}
%>

</div>
          </div>
        </div>
      </div>
    </div>
  </main>
</div>

<div class="toast-container"></div>
<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>
</body>
</html>

