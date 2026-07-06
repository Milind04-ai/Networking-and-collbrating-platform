<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.UserConnection"%>

<%
ArrayList<UserConnection> requests =
(ArrayList<UserConnection>)
request.getAttribute("requests");
%>

<html>
<body>

<h1>Incoming Requests</h1>

<%
for(UserConnection c : requests){
%>

<div style="border:1px solid black;
padding:10px;
margin:10px;">

    <h3>
        <%= c.getRequesterName() %>
    </h3>

    <form action="acceptConnection"
          method="post">

        <input type="hidden"
               name="connectionId"
               value="<%= c.getId() %>">

        <button type="submit">
            Accept
        </button>

    </form>

    <br>

    <form action="rejectConnection"
          method="post">

        <input type="hidden"
               name="connectionId"
               value="<%= c.getId() %>">

        <button type="submit">
            Reject
        </button>

    </form>

</div>

<%
}
%>

</body>
</html>