<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.UserConnection"%>

<%
ArrayList<UserConnection> requests =
(ArrayList<UserConnection>)
request.getAttribute("requests");

ArrayList<UserConnection> connections =
(ArrayList<UserConnection>)
request.getAttribute("connections");

if(requests == null){
    requests = new ArrayList<>();
}

if(connections == null){
    connections = new ArrayList<>();
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nexus - Network</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">

<style>

.network-section{
    margin-bottom:30px;
}

.network-card{
    background:var(--bg-card);
    border:1px solid var(--border);
    border-radius:12px;
    padding:18px;
    margin-bottom:12px;
}

.network-header{
    font-size:24px;
    font-weight:700;
    margin-bottom:20px;
}

.user-name{
    font-size:16px;
    font-weight:600;
    margin-bottom:10px;
}

.action-row{
    display:flex;
    gap:10px;
}

</style>

</head>

<body>

<div class="layout">

    <main class="main">

        <div class="page-content">

            <div class="network-header">
                Network
            </div>

            <!-- Incoming Requests -->

            <div class="network-section">

                <h2>
                    Incoming Requests
                    (<%= requests.size() %>)
                </h2>

                <br>

                <%
                if(requests.isEmpty()){
                %>

                <div class="network-card">
                    No pending requests
                </div>

                <%
                }

                for(UserConnection uc : requests){
                %>

                <div class="network-card">

                    <div class="user-name">
                        <%= uc.getRequesterName() %>
                    </div>

                    <div class="action-row">

                        <form method="post"
                              action="<%=request.getContextPath()%>/acceptConnection">

                            <input type="hidden"
                                   name="connectionId"
                                   value="<%= uc.getId() %>">

                            <button class="btn btn-primary">
                                Accept
                            </button>

                        </form>

                        <form method="post"
                              action="<%=request.getContextPath()%>/rejectConnection">

                            <input type="hidden"
                                   name="connectionId"
                                   value="<%= uc.getId() %>">

                            <button class="btn btn-secondary">
                                Reject
                            </button>

                        </form>

                    </div>

                </div>

                <%
                }
                %>

            </div>

            <!-- My Connections -->

            <div class="network-section">

                <h2>
                    My Connections
                    (<%= connections.size() %>)
                </h2>

                <br>

                <%
                if(connections.isEmpty()){
                %>

                <div class="network-card">
                    No connections yet
                </div>

                <%
                }

                for(UserConnection uc : connections){
                %>

                <div class="network-card">

                    <div class="user-name">
                        <%= uc.getConnectionName() %>
                    </div>
                    
                    <a class="btn btn-primary"
   href="<%=request.getContextPath()%>/userProfile?id=<%= uc.getConnectionId() %>">

    View Profile

</a>

                    <a class="btn btn-primary"
                       href="<%=request.getContextPath()%>/chat?receiverId=<%= uc.getConnectionId() %>">

                        Message

                    </a>

                </div>

                <%
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
function(){

    renderSidebar('network');

    renderTopbar('Network');

});

</script>

</body>
</html>