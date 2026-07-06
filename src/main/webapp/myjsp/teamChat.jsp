<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.GroupMessage"%>

<%
ArrayList<GroupMessage> messages =
(ArrayList<GroupMessage>)
request.getAttribute("messages");

String chatId =
(String)request.getAttribute("chatId");

String chatName =
(String)request.getAttribute("chatName");

String currentUserId =
session.getAttribute("userId").toString();
%>

<!DOCTYPE html>
<html>
<head>

<title><%= chatName %></title>

<style>

body{
    margin:0;
    font-family:Arial,sans-serif;
    background:#f5f7fb;
}

.header{
    background:#2563eb;
    color:white;
    padding:18px;
    font-size:22px;
    font-weight:bold;
}

.messages{
    height:70vh;
    overflow-y:auto;
    padding:20px;
}

.message{
    margin-bottom:15px;
    max-width:65%;
}

.mine{
    margin-left:auto;
}

.sender{
    font-size:12px;
    color:#666;
    margin-bottom:4px;
}

.bubble{
    padding:12px;
    border-radius:12px;
    background:white;
    box-shadow:0 1px 4px rgba(0,0,0,0.1);
}

.mine .bubble{
    background:#2563eb;
    color:white;
}

.time{
    font-size:11px;
    color:#888;
    margin-top:4px;
}

.form-area{
    background:white;
    border-top:1px solid #ddd;
    padding:15px;
}

.form-area form{
    display:flex;
    gap:10px;
}

.form-area input[type=text]{
    flex:1;
    padding:12px;
    border:1px solid #ccc;
    border-radius:8px;
}

.form-area button{
    background:#2563eb;
    color:white;
    border:none;
    padding:12px 20px;
    border-radius:8px;
    cursor:pointer;
}

.form-area button:hover{
    background:#1d4ed8;
}

</style>

</head>

<body>

<div class="header">
    <%= chatName %>
</div>

<div class="messages" id="messages">

<%
for(GroupMessage gm : messages){
%>

<div class="message
<%= gm.getSenderId().equals(currentUserId)
        ? " mine"
        : "" %>">

    <div class="sender">
        <%= gm.getSenderName() %>
    </div>

    <div class="bubble">
        <%= gm.getContent() %>
    </div>

    <div class="time">
        <%= gm.getSentAt() %>
    </div>

</div>

<%
}
%>

</div>

<div class="form-area">

<form action="sendGroupMessage"
      method="post">

    <input type="hidden"
           name="chatId"
           value="<%= chatId %>">

    <input type="text"
           name="content"
           placeholder="Type a message..."
           required>

    <button type="submit">
        Send
    </button>

</form>

</div>

<script>

window.onload = function(){

    const box =
        document.getElementById(
            "messages");

    box.scrollTop =
        box.scrollHeight;
};

</script>

</body>
</html>