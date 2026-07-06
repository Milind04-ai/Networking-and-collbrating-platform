<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="myclasses.Message"%>
<%@page import="myclasses.ChatUser"%>

<%
ArrayList<Message> messages =
(ArrayList<Message>)
request.getAttribute("messages");

ArrayList<ChatUser> chatUsers =
(ArrayList<ChatUser>)
request.getAttribute("chatUsers");

String currentUserId =
(String)request.getAttribute(
        "currentUserId");

String receiverId =
(String)request.getAttribute(
        "receiverId");

String receiverName =
(String)request.getAttribute(
        "receiverName");

String statusText =
(String)request.getAttribute(
        "statusText");

String groupId =
(String)request.getAttribute(
        "groupId");

String groupName =
(String)request.getAttribute(
        "groupName");

if(messages == null){
    messages = new ArrayList<>();
}

if(chatUsers == null){
    chatUsers = new ArrayList<>();
}

if(chatUsers == null){
    chatUsers = new ArrayList<>();
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nexus — Messages</title>
<script>
const receiverId = "<%= receiverId %>";
const currentUserId = "<%= currentUserId %>";
const groupId = "<%= groupId %>";
</script>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/global.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/mycss/chat.css">
</head>
<body>

<div class="page-wrapper">
  <!-- ═══ MAIN AREA ═══ -->
  <div class="main-area">
    <header class="topbar">
      <div class="topbar-title">Messages</div>
      <div class="topbar-right">
        <span style="font-size:13px;color:var(--text-muted)">Press <kbd>/</kbd> to search</span>
        <a href="<%=request.getContextPath()%>/profile">
          <div class="avatar" style="width:36px;height:36px;font-size:14px">A</div>
        </a>
      </div>
    </header>

    <div class="chat-layout">
      <!-- Conversation list -->
      <div class="convo-panel">
        <div class="convo-search">
          <div class="search-bar">
            <svg width="13" height="13" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
            <input type="text" id="search-input" placeholder="Search messages…">
          </div>
        </div>
        <div class="convo-tabs">
          <div class="convo-tab active">Direct</div>
          <div class="convo-tab">Groups</div>
        </div>
          <div class="convo-list">
              <%
for(ChatUser cu : chatUsers){
%>

<a href="<%=request.getContextPath()%>/chat?receiverId=<%= cu.getUserId() %>"
   style="text-decoration:none;color:inherit;">

<div class="convo-item
<%= receiverId != null &&
    receiverId.equals(
        cu.getUserId())
        ? " active"
        : "" %>">
    <div class="convo-avatar-wrap">

        <div class="avatar"
             style="width:38px;
                    height:38px;
                    font-size:14px">

            <%= cu.getFullName()
                    .substring(0,1)
                    .toUpperCase() %>

        </div>

    </div>

    <div class="convo-info">

        <div class="convo-name">

    <span>
        <%= cu.getFullName() %>
    </span>

    <%
    if(cu.getUnreadCount() > 0){
    %>

        <span class="unread-badge">

            <%= cu.getUnreadCount() %>

        </span>

    <%
    }
    %>

</div>
            
        <div class="convo-preview">

            <%= cu.getLastMessage() %>
            
        </div>

    </div>

</div>

</a>

<%
}
%>
          </div>
      </div>
<%
if(receiverId != null || groupId != null){
%>
      <!-- Chat window -->
      <div class="chat-window">
        <div class="chat-header">
          <div class="msg-avatar" id="chat-avatar" style="width:36px;height:36px;font-size:14px;background:linear-gradient(135deg,#22c55e,#38bdf8)">P</div>
          <div class="chat-header-info">
            <div class="chat-header-name" id="chat-name"><%if(groupId != null){%>
                <%= groupName %>
                <%}else{%>
                <%= receiverName %>
                <%}%></div>
            <div class="chat-header-sub" id="chat-sub">

<%
if("Online".equals(statusText)){
%>

<span style="
width:7px;
height:7px;
background:var(--success);
border-radius:50%;
display:inline-block">
</span>

Online

<%
}else{
%>

<%
if(groupId != null){
%>

Group Chat

<%
}else{
%>

<%= statusText %>

<%
}
%>

<%
}
%>

</div>
          </div>
          <div class="chat-header-actions">
            <button class="icon-btn" id="video-btn" data-tooltip="Video call">
              <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><polygon points="23 7 16 12 23 17 23 7"/><rect x="1" y="5" width="15" height="14" rx="2" ry="2"/></svg>
            </button>
            <% if(receiverId != null){ %>

<a href="<%=request.getContextPath()%>/userProfile?id=<%=receiverId%>"
   class="icon-btn"
   data-tooltip="View profile">

  <svg width="15"
       height="15"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24">

    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>

    <circle cx="12"
            cy="7"
            r="4"/>

  </svg>

</a>

<% } %>
            <button class="icon-btn" data-tooltip="More options">
              <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="12" cy="5" r="1"/><circle cx="12" cy="12" r="1"/><circle cx="12" cy="19" r="1"/></svg>
            </button>
          </div>
        </div>
<div class="messages-area" id="messages-area">

<%
for(Message m : messages){
%>

<div class="msg-group
<%= m.getSenderId().equals(currentUserId)
        ? " mine"
        : "" %>">

    <div class="msg-avatar">
        <%= receiverName != null &&
    !receiverName.isEmpty()
        ? receiverName.substring(0,1)
        : "?" %>
    </div>

    <div class="msg-content">

        <div class="bubble
        <%= m.getSenderId().equals(currentUserId)
                ? " mine"
                : "" %>">
            
            <%
if(groupId != null &&
   !m.getSenderId().equals(
           currentUserId)){
%>

<div style="
font-size:12px;
font-weight:bold;
margin-bottom:4px;">
    <%= m.getSenderName() %>
</div>

<%
}
%>

            <%= m.getContent() %>

        </div>

        <div class="msg-time">

            <%= m.getSentAt() %>

        </div>

        <%
        if(m.getSenderId().equals(currentUserId)){
        %>

        <div class="msg-read">

            <%= m.isRead()
                    ? "✓✓ Read"
                    : "✓ Sent" %>

        </div>

        <%
        }
        %>

    </div>

</div>

<%
}
%>

</div>
</h1>
        <div class="chat-input-bar">
          <form id="chat-form"

action="<%= groupId != null
? request.getContextPath()
+ "/sendGroupMessage"
: request.getContextPath()
+ "/sendMessage" %>"

method="post">
<%
if(groupId != null){
%>

<input type="hidden"
       name="chatId"
       value="<%= groupId %>">

<%
}else{
%>

<input type="hidden"
       name="receiverId"
       value="<%= receiverId %>">

<%
}
%>            <div class="input-row">
<input type="file"
       id="file-input"
       name="file"
       hidden>

<button type="button"
        class="attach-btn"
        onclick="document.getElementById('file-input').click();">                <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21.44 11.05l-9.19 9.19a6 6 0 0 1-8.49-8.49l9.19-9.19a4 4 0 0 1 5.66 5.66l-9.2 9.19a2 2 0 0 1-2.83-2.83l8.49-8.48"/></svg>
              </button>
              <textarea class="chat-input" id="chat-input" name="content" rows="1" placeholder="Message <%= groupId != null
        ? groupName
        : receiverName %>..."></textarea>
              <button type="button" class="attach-btn" data-tooltip="Emoji">
                <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><path d="M8 13s1.5 2 4 2 4-2 4-2"/><line x1="9" y1="9" x2="9.01" y2="9"/><line x1="15" y1="9" x2="15.01" y2="9"/></svg>
              </button>
              <button type="submit" class="send-btn" data-tooltip="Send (Enter)">
                <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><line x1="22" y1="2" x2="11" y2="13"/><polygon points="22 2 15 22 11 13 2 9 22 2"/></svg>
              </button>
            </div>
            <div class="char-limit" id="char-limit"></div>
          </form>
        </div>
      </div>
              <%
}
else{
%>

<div class="chat-window">
    <div class="empty-chat">
        <h2>Select a conversation</h2>
        <p>Choose a user from the left panel to start chatting.</p>
    </div>
</div>

<%
}
%>
    </div>
  </div>
</div>

<div class="toast-container" id="toast-container"></div>
<script>
window.onload = function() {

    const messagesArea =
        document.getElementById(
            "messages-area");

    if(messagesArea){

        messagesArea.scrollTop =
            messagesArea.scrollHeight;
    }
};
</script>
<script src="<%=request.getContextPath()%>/myjs/shared.js"></script>
<script src="<%=request.getContextPath()%>/myjs/chat.js"></script>
</body>
</html>
