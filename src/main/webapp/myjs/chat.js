let typingTimer;
let lastMessageCount = 0;
let lastSidebarData = "";
let lastGroupData = "";
const activeReceiverId = receiverId;

function initReactions() {
  document.querySelectorAll('.reaction-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const bubble = btn.closest('.bubble');
      let reactionEl = bubble.querySelector('.bubble-reaction');
      if (!reactionEl) {
        reactionEl = document.createElement('span');
        reactionEl.className = 'bubble-reaction';
        reactionEl.style.cssText = 'position:absolute;bottom:-10px;right:8px;font-size:14px;background:var(--bg-elevated);border:1px solid var(--border);border-radius:12px;padding:2px 6px;cursor:pointer';
        bubble.appendChild(reactionEl);
      }
      reactionEl.textContent = btn.dataset.emoji + ' 1';
    });
  });
}

document.addEventListener('DOMContentLoaded', () => {
  renderSidebar('chat');
  if(groupId){

    loadGroupMessages();

    setInterval(
        loadGroupMessages,
        3000
    );
}
  loadNotificationCounts();
  initDropdowns();
  initSearchShortcut();
  
  // Conversation Search
const searchInput =
    document.getElementById(
        "search-input");

if(searchInput){

    searchInput.addEventListener(
        "keyup",
        function(){

            const searchText =
                this.value
                    .toLowerCase();

            document
                .querySelectorAll(
                    ".convo-item")
                .forEach(item => {

                    const userName =
                        item.querySelector(
                            ".convo-name")
                        .textContent
                        .toLowerCase();

                    const wrapper =
                        item.closest("a");

                    if(userName.includes(
                            searchText)){

                        wrapper.style.display =
                            "";

                    }else{

                        wrapper.style.display =
                            "none";
                    }
                });
        });
}

  // ── Convo item click ──
  document.querySelectorAll('.convo-item').forEach(item => {
    item.addEventListener('click', () => {
      const { convo, name, init, color, status } = item.dataset;
      // Remove unread badge
      item.querySelector('.unread-badge')?.remove();
      const preview = item.querySelector('.convo-preview');
      if (preview) preview.classList.remove('unread');
    });
  });

  // ── Convo tabs (DM / Groups) ──
  document.querySelectorAll('.convo-tab').forEach(tab => {

    tab.addEventListener('click', () => {

        document.querySelectorAll('.convo-tab')
            .forEach(t => t.classList.remove('active'));

        tab.classList.add('active');

        if(tab.textContent.trim() === "Groups"){

            loadGroups();

        }else{

            loadSidebar();
        }
    });
});

  // ── Send message (form submit) ──
  /*
  document.getElementById('chat-form')?.addEventListener('submit', function(e) {
    const input = this.querySelector('.chat-input');
    const text = input.value.trim();
    if (!text) return;
    sendMessage(text);
    input.value = '';
    input.style.height = 'auto';
    updateCharCount(0);
  });
*/
  // ── Auto-resize textarea ──
  const chatInput = document.querySelector('.chat-input');
  const charCount = document.querySelector('.char-limit');
  function updateCharCount(len) {
    if (!charCount) return;
    charCount.textContent = len > 0 ? `${len}/1000` : '';
    charCount.style.color = len > 900 ? 'var(--warning)' : '';
  }
  chatInput?.addEventListener('input', function() {
    this.style.height = 'auto';
    this.style.height = Math.min(this.scrollHeight, 100) + 'px';
    updateCharCount(this.value.length);
  });

  // ── Enter to send (Shift+Enter for newline) ──
  chatInput?.addEventListener('keydown', function(e) {
    if (e.key === 'Enter' && !e.shiftKey) {
      document.getElementById('chat-form')?.dispatchEvent(new Event('submit'));
    }
  });

  // ── Emoji picker placeholder ──
  document.querySelector('.attach-btn')?.addEventListener('click', () => {
    showToast('File attachment coming soon', 'info');
  });

  // ── Header action buttons ──
  document.getElementById('video-btn')?.addEventListener('click', () => showToast('Video call feature coming soon', 'info'));
  document.getElementById('profile-btn')?.addEventListener('click', () => { window.location.href = '../profile/profile.html'; });

  // ── Mark convo as read when clicked ──
  document.querySelector('[data-convo="priya"]')?.classList.add('active');
  
  loadMessages();

setInterval(loadMessages , 3000);

loadSidebar();

setInterval(
    loadSidebar,
    5000
);

});

function loadMessages() {

    if(!receiverId) return;

    fetch(`/Nexus/getMessages?receiverId=${receiverId}`)
    .then(response => response.json())
    .then(data => {

    if(data.length === lastMessageCount){
        return;
    }

    lastMessageCount = data.length;

    const messagesArea =
        document.getElementById(
            "messages-area");

    let html = "";

    data.forEach(msg => {

        const mine =
            msg.senderId === currentUserId;

        html += `
        <div class="msg-group ${mine ? 'mine' : ''}">
            <div class="msg-content">

                <div class="bubble ${mine ? 'mine' : ''}">
                    ${msg.content}
                </div>

                <div class="msg-time">
                    ${msg.sentAt}
                </div>

            </div>
        </div>`;
    });

        messagesArea.innerHTML = html;

    messagesArea.scrollTop =
        messagesArea.scrollHeight;
});
}

function loadSidebar() {

    fetch('/Nexus/getChatUsers')
    .then(response => response.json())
    .then(data => {

        const currentData =
            JSON.stringify(data);

        if(currentData === lastSidebarData){
            return;
        }

        lastSidebarData =
            currentData;

        const convoList =
            document.querySelector(
                ".convo-list");

        if(!convoList){
            return;
        }

        let html = "";

        data.forEach(user => {

            html += `
            <a href="/Nexus/chat?receiverId=${user.userId}"
               style="text-decoration:none;color:inherit;">

                <div class="convo-item ${
    user.userId === activeReceiverId
        ? "active"
        : ""
}">

                    <div class="convo-avatar-wrap">

                        <div class="avatar"
                             style="
                             width:38px;
                             height:38px;
                             font-size:14px">

                            ${user.fullName
                                .charAt(0)
                                .toUpperCase()}

                        </div>

                    </div>

                    <div class="convo-info">

                        <div class="convo-name">

    <span>
        ${user.fullName}
    </span>

    ${
      Number(user.unreadCount) > 0
      ? `<span style="
background:blue;
color:white;
padding:3px 8px;
border-radius:100%;
margin-left:10px;
display:inline-block;
">
            ${user.unreadCount}
         </span>`
      : ''
    }

</div>

                        <div class="convo-preview">

                            ${user.lastMessage}

                        </div>

                    </div>

                </div>

            </a>`;
        });
                
        convoList.innerHTML = html;
    });
}

function loadGroups() {

    fetch('/Nexus/getGroups')
    .then(response => response.json())
    .then(data => {

        const convoList =
            document.querySelector(
                ".convo-list");

        let html = "";

        data.forEach(group => {

            html += `
            <a href="/Nexus/chat?groupId=${group.id}"
               style="text-decoration:none;color:inherit;">

                <div class="convo-item">

                    <div class="convo-avatar-wrap">

                        <div class="avatar"
                             style="
                             width:38px;
                             height:38px;
                             font-size:14px;
                             background:#6366f1;">

                            👥

                        </div>

                    </div>

                    <div class="convo-info">

                        <div class="convo-name">

                            ${group.name}

                        </div>

                        <div class="convo-preview">

                            Group Chat

                        </div>

                    </div>

                </div>

            </a>`;
        });

        convoList.innerHTML = html;
    });
}

function loadGroupMessages() {

    if(!groupId) return;

    fetch(
        "/Nexus/getGroupMessages?groupId="
        + groupId
    )
    .then(r => r.json())
    .then(data => {
        
        const currentData =
        JSON.stringify(data);

    if(currentData === lastGroupData){
        return;
    }

    lastGroupData =
        currentData;

        const area =
            document.getElementById(
                "messages-area"
            );

        let html = "";

        data.forEach(msg => {

            const mine =
                msg.senderId === currentUserId;

            html += `
            <div class="msg-group ${mine ? "mine" : ""}">
                <div class="msg-content">

                    ${
                        !mine
                        ? `<div class="sender-name">
                            ${msg.senderName}
                           </div>`
                        : ""
                    }

                    <div class="bubble ${mine ? "mine" : ""}">
                        ${msg.content}
                    </div>

                    <div class="msg-time">
                        ${msg.sentAt}
                    </div>

                </div>
            </div>`;
        });

console.log(
    "GROUP CHAT REBUILT"
);
        area.innerHTML = html;
    });
}