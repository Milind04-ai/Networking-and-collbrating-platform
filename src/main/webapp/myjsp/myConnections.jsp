<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.UserConnection"%>

<%
ArrayList<UserConnection> list =
(ArrayList<UserConnection>)
request.getAttribute("connections");

if(list == null){
    list = new ArrayList<>();
}
%>

<html>
<body>

<h1>My Connections</h1>

<%
for(UserConnection uc : list){
%>

<div style="border:1px solid black;
padding:10px;
margin:10px;">

    <h3>
    <%= uc.getConnectionName() %>
</h3>

<a class="btn btn-secondary"
   href="<%=request.getContextPath()%>/userProfile?id=<%= uc.getConnectionId() %>">

    View Profile

</a>

<a href="<%=request.getContextPath()%>/chat?receiverId=<%= uc.getConnectionId() %>">
    Message
</a>

</div>

<%
}
%>

</body>
</html>