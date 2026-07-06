// ============================================================
//  shared.js  —  Nexus Platform
//  Include this file on every page BEFORE the page-specific JS
// ============================================================
const CONTEXT_PATH = "/Nexus";
// ── Sidebar renderer ──────────────────────────────────────
function renderSidebar(activePage, basePath = '..') {
  const nav = [
{
    section:'Main',
    items:[
        {
            id:'dashboard',
            label:'Dashboard',
            href:`${CONTEXT_PATH}/dashboard`,
            icon:'grid'
        },
        {
            id:'explore',
            label:'Explore',
            href:`${CONTEXT_PATH}/explore`,
            icon:'search'
        },
        {
            id:'projects',
            label:'Projects',
            href:`${CONTEXT_PATH}/projects`,
            icon:'folder',
            badge:'4',
            badgeType:'accent'
        },
        {
            id:'applications',
            label:'Applications',
            href:`${CONTEXT_PATH}/applications`,
            icon:'file',
            badge:'0',
            badgeType:'accent'
        },
        {
            id:'manageApplications',
            label:'Manage Applications',
            href:`${CONTEXT_PATH}/manageApplications`,
            icon:'file'
        }
    ]
},
{
    section:'People',
    items:[
        {
            id:'profile',
            label:'My Profile',
            href:`${CONTEXT_PATH}/profile`,
            icon:'user'
        },
        {
            id:'network',
            label:'Network',
            href:`${CONTEXT_PATH}/network`,
            icon:'users',
            badge:'0',
            badgeType:'success'
        },
        {
            id:'logout',
            label:'Logout',
            href:`${CONTEXT_PATH}/logout`,
            icon:'user'
        }
    ]
},
{
    section:'Communication',
    items:[
        {
            id:'chat',
            label:'Messages',
            href:`${CONTEXT_PATH}/chat`,
            icon:'message',
            badge:'0',
            badgeType:'accent'
        },
        {
            id:'notifications',
            label:'Notifications',
            href:`${CONTEXT_PATH}/notificationsPage`,
            icon:'bell'
        }
    ]
}
];
  const icons = {
    grid:    `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>`,
    search:  `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>`,
    folder:  `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>`,
    file:    `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14,2 14,8 20,8"/></svg>`,
    user:    `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>`,
    users:   `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>`,
    message: `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>`,
    bell:    `<svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>`,
  };

  let html = `<aside class="sidebar" id="sidebar">
    <div class="sidebar-logo">
      <div class="logo-mark">N</div>
      <span class="logo-name">Nexus</span>
    </div>
    <nav class="sidebar-nav">`;

  nav.forEach(({ section, items }) => {
    html += `<div class="nav-section"><div class="nav-label">${section}</div>`;
    items.forEach(item => {
      const isActive = item.id === activePage;
      let extra = '';
      if (item.badge) {
        const bg   = item.badgeType === 'success' ? 'rgba(34,197,94,0.1)'   : 'var(--accent-soft)';
        const col  = item.badgeType === 'success' ? 'var(--success)'         : 'var(--accent)';
        const bdr  = item.badgeType === 'success' ? 'rgba(34,197,94,0.2)'   : 'var(--accent-glow)';
let badgeId = '';

if(item.id === 'applications')
    badgeId = 'applications-badge';

if(item.id === 'chat')
    badgeId = 'messages-badge';

if(item.id === 'network')
    badgeId = 'network-badge';

extra = `<span id="${badgeId}" style="margin-left:auto; font-size:11px; padding:2px 7px; background:${bg}; color:${col}; border:1px solid ${bdr}; border-radius:999px"> ${item.badge} </span>`;
} else if (item.dot) {
        extra = `<span style="margin-left:auto;width:8px;height:8px;background:var(--accent);border-radius:50%;box-shadow:0 0 6px var(--accent)"></span>`;
      }
      html += `<a class="nav-item${isActive ? ' active' : ''}" href="${item.href}">${icons[item.icon]}${item.label}${extra}</a>`;
    });
    html += `</div>`;
  });

  html += `</nav>
    <div class="sidebar-footer">
      <a class="user-chip" href="/Nexus/profile">
        <div class="avatar"
     id="sidebar-avatar"
     style="width:32px;height:32px;font-size:13px">
</div>

<div class="user-info">

    <div id="sidebar-name"
         style="font-size:13px;font-weight:500;color:var(--text-primary)">
    </div>

    <div id="sidebar-headline"
         style="font-size:11px;color:var(--text-muted)">
    </div>

</div>
      </a>
    </div>
  </aside>`;

  document.body.insertAdjacentHTML('afterbegin', html);
}

fetch("/Nexus/currentUser")

.then(r => r.json())

.then(user => {

    document.getElementById(
        "sidebar-name"
    ).textContent = user.name;

    document.getElementById(
        "sidebar-headline"
    ).textContent = user.headline;

    document.getElementById(
        "sidebar-avatar"
    ).textContent =
        user.name.charAt(0).toUpperCase();

});

// ── Topbar renderer ───────────────────────────────────────
function renderTopbar(title, basePath = '..') {
  const html = `<header class="topbar">
    <div style="font-family:var(--font-display);font-weight:600;font-size:16px">${title}</div>
    <div class="search-bar" id="global-search">
      <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
      <input type="text" placeholder="Search... (press /)" id="search-input">
    </div>
    <div style="display:flex;align-items:center;gap:10px">
      <a href="/Nexus/chat" style="position:relative;display:flex;align-items:center;justify-content:center;width:36px;height:36px;border-radius:var(--radius-md);background:var(--bg-elevated);border:1px solid var(--border);color:var(--text-secondary);text-decoration:none;transition:var(--transition)" data-tooltip="Messages">
        <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
        <span style="position:absolute;top:-3px;right:-3px;width:8px;height:8px;background:var(--accent);border-radius:50%;border:2px solid var(--bg-base)"></span>
      </a>
      <div style="position:relative">

<div
id="notif-btn"
style="
display:flex;
align-items:center;
justify-content:center;
width:36px;
height:36px;
border-radius:var(--radius-md);
background:var(--bg-elevated);
border:1px solid var(--border);
cursor:pointer;">

<svg width="16"
     height="16"
     fill="none"
     stroke="currentColor"
     stroke-width="2"
     viewBox="0 0 24 24">

<path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
<path d="M13.73 21a2 2 0 0 1-3.46 0"/>

</svg>

<span
id="notif-dot"
style="
position:absolute;
top:-3px;
right:-3px;
width:8px;
height:8px;
background:red;
border-radius:50%;">
</span>

</div>

<div
id="notif-dropdown"
style="
display:none;
position:absolute;
top:45px;
right:0;
width:340px;
background:var(--bg-card);
border:1px solid var(--border);
border-radius:12px;
padding:12px;
z-index:9999;
box-shadow:0 10px 30px rgba(0,0,0,.3);">

<h4 style="margin-bottom:12px">
Notifications
</h4>

<div id="notif-list">

Loading...

</div>

<hr style="margin:12px 0">

<a
href="/Nexus/notificationsPage"
style="
color:var(--accent);
text-decoration:none;">

View All Notifications ->

</a>

</div>

</div>
      <a href="/Nexus/profile">
        <div class="avatar" style="width:36px;height:36px;font-size:14px">A</div>
      </a>
    </div>
  </header>`;
  const container =
            document.querySelector('.main')
            || document.querySelector('.main-area');

    if(container){
        container.insertAdjacentHTML(
            'afterbegin',
            html);
            
            initNotificationDropdown();
    }
}

// ── Toast notification ────────────────────────────────────
function showToast(message, type = 'info') {
  const icons = {
    success: `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>`,
    error:   `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>`,
    info:    `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>`,
  };
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    document.body.appendChild(container);
  }
  const toast = document.createElement('div');
  toast.className = `toast toast-${type} fade-in`;
  toast.innerHTML = `<div class="toast-icon">${icons[type] || icons.info}</div><span>${message}</span>`;
  container.appendChild(toast);
  setTimeout(() => {
    toast.classList.add('hide');
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

// ── Modal init ────────────────────────────────────────────
function initModals() {
  document.querySelectorAll('[data-modal]').forEach(trigger => {
    trigger.addEventListener('click', () => {
      const modal = document.getElementById(trigger.dataset.modal);
      if (modal) modal.classList.add('open');
    });
  });
  document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', e => {
      if (e.target === overlay) overlay.classList.remove('open');
    });
  });
  document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', () => btn.closest('.modal-overlay').classList.remove('open'));
  });
}

// ── Dropdown init ─────────────────────────────────────────
function initDropdowns() {
  document.querySelectorAll('.dropdown').forEach(dd => {
    const trigger = dd.querySelector('.dropdown-trigger');
    const menu    = dd.querySelector('.dropdown-menu');
    if (!trigger || !menu) return;
    trigger.addEventListener('click', e => {
      e.stopPropagation();
      menu.classList.toggle('open');
    });
  });
  document.addEventListener('click', () => {
    document.querySelectorAll('.dropdown-menu.open').forEach(m => m.classList.remove('open'));
  });
}

// ── Ripple effect ─────────────────────────────────────────
function addRipple(el) {
  el.addEventListener('click', function(e) {
    const rect   = this.getBoundingClientRect();
    const size   = Math.max(rect.width, rect.height);
    const ripple = document.createElement('span');
    ripple.className = 'ripple';
    ripple.style.cssText = `width:${size}px;height:${size}px;left:${e.clientX - rect.left - size / 2}px;top:${e.clientY - rect.top - size / 2}px`;
    this.appendChild(ripple);
    setTimeout(() => ripple.remove(), 600);
  });
}
function initRipples() {
  document.querySelectorAll('.btn-primary, .btn-ghost').forEach(addRipple);
}

// ── Count-up animation ────────────────────────────────────
function countUp(el) {
  const target = parseInt(el.dataset.count || el.textContent, 10);
  if (isNaN(target)) return;
  let current = 0;
  const step  = Math.ceil(target / 30);
  const timer = setInterval(() => {
    current = Math.min(current + step, target);
    el.textContent = current.toLocaleString();
    if (current >= target) clearInterval(timer);
  }, 30);
}
function initCountUp() {
  document.querySelectorAll('[data-count]').forEach(countUp);
}

// ── Intersection-observer fade-in ─────────────────────────
function initFadeIn() {
  const obs = new IntersectionObserver(entries => {
    entries.forEach(e => {
      if (e.isIntersecting) { e.target.classList.add('fade-in'); obs.unobserve(e.target); }
    });
  }, { threshold: 0.1 });
  document.querySelectorAll('.observe-fade').forEach(el => obs.observe(el));
}

// ── Keyboard shortcuts ────────────────────────────────────
function initSearchShortcut() {
  document.addEventListener('keydown', e => {
    if (e.key === '/' && document.activeElement.tagName !== 'INPUT' && document.activeElement.tagName !== 'TEXTAREA') {
      e.preventDefault();
      document.getElementById('search-input')?.focus();
    }
    if (e.key === 'Escape') {
      document.querySelectorAll('.modal-overlay.open').forEach(m => m.classList.remove('open'));
      document.querySelectorAll('.dropdown-menu.open').forEach(m => m.classList.remove('open'));
    }
  });
}

function loadNotificationCounts(basePath = '..') {
    
    fetch('/Nexus/notifications')

    .then(response => response.json())

    .then(data => {
        
        const notifDot =
document.getElementById(
'notif-dot');

if(notifDot){

    if(
        data.applications +
        data.messages +
        data.requests > 0
    ){
        notifDot.style.display =
            'block';
    }else{
        notifDot.style.display =
            'none';
    }
}

        const appBadge =
            document.getElementById(
                'applications-badge');

        const msgBadge =
            document.getElementById(
                'messages-badge');

        const reqBadge =
            document.getElementById(
                'network-badge');

        if(appBadge){

            appBadge.textContent =
                data.applications;
        }

        if(msgBadge){

            msgBadge.textContent =
                data.messages;
        }

        if(reqBadge){

            reqBadge.textContent =
                data.requests;
        }

    })

    .catch(error => {

        console.log(error);
    });
}

function initNotificationDropdown(){

const btn =
document.getElementById(
'notif-btn');

const dropdown =
document.getElementById(
'notif-dropdown');

if(!btn || !dropdown){
    return;
}

btn.addEventListener(
'click',
function(){

if(dropdown.style.display==='block'){

dropdown.style.display='none';
return;

}

dropdown.style.display='block';

fetch('/Nexus/notificationDropdown')

.then(r=>r.json())

.then(data=>{

let html='';

if(data.length===0){

html=
'<p>No notifications</p>';

}else{

data.forEach(n=>{

html += `
<div style="
padding:10px;
border-bottom:1px solid var(--border);">

<div style="
font-size:14px;
margin-bottom:4px;">

${n.message}

</div>

<div style="
font-size:11px;
opacity:.7;">

${n.time}

</div>

</div>
`;

});

}

document.getElementById(
'notif-list')
.innerHTML = html;

});

});

document.addEventListener(
'click',
function(e){

if(
!dropdown.contains(e.target)
&&
!btn.contains(e.target)
){

dropdown.style.display='none';

}

});
}