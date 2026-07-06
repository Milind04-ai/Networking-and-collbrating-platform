<%@page contentType="text/html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Application"%>

<%
ArrayList<Application> apps =
(ArrayList<Application>)
request.getAttribute("applications");

if(apps == null){
    apps = new ArrayList<>();
}
%>

<html>
<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/manageApplications.css">
<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">
<head>
<title>Manage Applications</title>
</head>
<body>

<div class="layout">

    <main class="main">

        <div class="page-content">

    <!-- Hero -->
    <div class="manage-hero">

        <h1>
            <i class="fa-solid fa-user-check"></i>
            Manage Applications
        </h1>

        <p>
            Review applicants for your projects and build the right team.
        </p>

    </div>

    <!-- Search -->

    <input
        class="input"
        id="searchApplicants"
        placeholder="Search applicant...">

    <br><br>

<%
if(apps.isEmpty()){
%>

<div class="empty-state">

    <i class="fa-regular fa-folder-open"></i>

    <h3>No Applications</h3>

    <p>
        No one has applied to your projects yet.
    </p>

</div>

<%
}else{

for(Application a : apps){
%>

<div class="card application-card">

    <!-- Top -->

    <div class="application-top">

        <div class="applicant-header">

            <div class="avatar-lg">

                <%= a.getApplicantName().substring(0,1).toUpperCase() %>

            </div>

            <div>

                <h3>

                    <%= a.getApplicantName() %>

                </h3>

                <p>

                    Applied for

                    <b>

                        <%= a.getProjectTitle() %>

                    </b>

                </p>

            </div>

        </div>

<%
String status = a.getStatus().toLowerCase();

if(status.equals("pending")){
%>

<span class="status-badge pending">

    Pending

</span>

<%
}else if(status.equals("accepted")){
%>

<span class="status-badge accepted">

    Accepted

</span>

<%
}else{
%>

<span class="status-badge rejected">

    Rejected

</span>

<%
}
%>

    </div>

    <!-- Buttons -->

    <div class="action-row">

        <a
        class="btn btn-secondary"

        href="<%=request.getContextPath()%>/userProfile?id=<%=a.getApplicantId()%>">

            View Profile

        </a>

        <a
        class="btn btn-primary"

        href="<%=request.getContextPath()%>/chat?receiverId=<%=a.getApplicantId()%>">

            Message

        </a>

    </div>

<%
if("pending".equalsIgnoreCase(a.getStatus())){
%>

    <div class="decision-row">

        <form
            method="post"
            action="<%=request.getContextPath()%>/acceptApplication">

            <input
                type="hidden"
                name="applicationId"
                value="<%=a.getId()%>">

            <button
                class="btn btn-success">

                Accept

            </button>

        </form>

        <form
            method="post"
            action="<%=request.getContextPath()%>/rejectApplication">

            <input
                type="hidden"
                name="applicationId"
                value="<%=a.getId()%>">

            <button
                class="btn btn-danger">

                Reject

            </button>

        </form>

    </div>

<%
}
%>

</div>

<%
}
}
%>

</div>
        </div>

    </main>

</div>

<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>

<script>

document.addEventListener(
    'DOMContentLoaded',
    () => {

        renderSidebar(
            'manageApplications'
        );

        renderTopbar(
            'Manage Applications'
        );

    });

document
.getElementById("searchApplicants")
.addEventListener(
"input",

function(){

const q =
this.value.toLowerCase();

document
.querySelectorAll(".application-card")
.forEach(card=>{

card.style.display =
card.innerText
.toLowerCase()
.includes(q)
? ""
: "none";

});

});
</script>

</body>
</body>
</html>