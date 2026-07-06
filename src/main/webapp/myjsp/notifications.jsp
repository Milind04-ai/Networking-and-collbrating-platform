<%@page contentType="text/html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Notification"%>

<%
ArrayList<Notification> notifications =
(ArrayList<Notification>)
request.getAttribute("notifications");

if(notifications == null){
    notifications = new ArrayList<>();
}
%>

<!DOCTYPE html>
<html>
<head>

<title>Notifications</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/mycss/global.css">

</head>

<body>

<div class="layout">

    <main class="main">

        <div class="page-content">

            <h1>
                Notifications
            </h1>

            <br>

            <% if(notifications.isEmpty()){ %>

                <div class="card"
                     style="padding:24px;">

                    No notifications yet

                </div>

            <% } %>

            <% for(Notification n : notifications){ %>

            <div class="card"
                 style="
                 margin-bottom:16px;
                 padding:20px;">

                <div style="
                     display:flex;
                     justify-content:space-between;
                     align-items:center;">

                    <span class="badge">

                        <%= n.getType() %>

                    </span>

                    <small>

                        <%= n.getCreatedAt() %>

                    </small>

                </div>

                <br>

                <div style="
                     font-size:15px;">

                    <%= n.getMessage() %>

                </div>

                <% if(!n.isRead()){ %>

                <div style="
                     margin-top:10px;
                     color:#6c63ff;
                     font-size:13px;">

                    ? Unread

                </div>

                <% } %>

            </div>

            <% } %>

        </div>

    </main>

</div>

<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>

<script>

document.addEventListener(
    'DOMContentLoaded',
    () => {

        renderSidebar(
            'notifications'
        );

        renderTopbar(
            'Notifications'
        );

        loadNotificationCounts();

    });

</script>

</body>
</html>