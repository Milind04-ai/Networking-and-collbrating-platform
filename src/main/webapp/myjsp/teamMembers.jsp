<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.TeamMember"%>

<%
ArrayList<TeamMember> members =
(ArrayList<TeamMember>)
request.getAttribute("members");
%>

<html>
<head>
    <title>Project Team Members</title>
</head>
<body>

<h1>Project Team Members</h1>

<%
if(members != null && !members.isEmpty()){
%>

<h2>
Project :
<%= members.get(0).getProjectTitle() %>
</h2>

<%
for(TeamMember tm : members){
%>

<div style="border:1px solid black;
padding:10px;
margin:10px;">

    <h3>
        <%= tm.getFullName() %>
    </h3>

    <p>
        Permission :
        <%= tm.getPermission() %>
    </p>

</div>

<%
}
}else{
%>

<h3>No members found.</h3>

<%
}
%>

</body>
</html>