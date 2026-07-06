const PEOPLE = [
  { init:'K', color:'linear-gradient(135deg,#22c55e,#38bdf8)', name:'Kavya Reddy',    headline:'ML Engineer · IIT Hyderabad',  skills:['Python','TensorFlow','FastAPI'], mutuals:14 },
  { init:'V', color:'linear-gradient(135deg,#f59e0b,#ef4444)', name:'Vivek Malhotra', headline:'Data Engineer · IIT Bombay',     skills:['Python','SQL','Spark'],          mutuals:8  },
  { init:'N', color:'linear-gradient(135deg,#38bdf8,#6c63ff)', name:'Neha Gupta',     headline:'Recruiter · TCS Digital',       skills:['Hiring','Talent Mgmt'],          mutuals:22 },
  { init:'M', color:'linear-gradient(135deg,#a855f7,#6c63ff)', name:'Mihir Joshi',    headline:'DevOps · BITS Pilani',          skills:['Docker','K8s','CI/CD'],          mutuals:6  },
  { init:'S', color:'linear-gradient(135deg,#ef4444,#f59e0b)', name:'Shruti Rao',     headline:'UX Designer · Manipal',        skills:['Figma','Sketch','Prototyping'],   mutuals:3  },
  { init:'A', color:'linear-gradient(135deg,#6c63ff,#a855f7)', name:'Aditya Verma',   headline:'Full-Stack · NSIT Delhi',      skills:['React','Node.js','MongoDB'],      mutuals:11 },
];

const PROJECTS = [
  { emoji:'🌱', title:'EcoTrack Dashboard',    owner:'Priya Mehta',  skills:['React','Node.js'], roles:2, members:3 },
  { emoji:'🏥', title:'MedConnect API',        owner:'Rahul Kumar',  skills:['Java','Spring Boot'], roles:3, members:2 },
  { emoji:'🤖', title:'SmartHire AI Tool',     owner:'Kavya Reddy',  skills:['Python','LangChain'], roles:1, members:5 },
  { emoji:'📱', title:'CampusConnect App',     owner:'Mihir Joshi',  skills:['Flutter','Firebase'], roles:4, members:1 },
];

let activeSection = 'all';
let connectedUsers = new Set();
let activeFilters = new Set(['student','developer','open']);

function renderPeople(data) {
  const grid = document.getElementById('people-grid');
  if (!data.length) { grid.innerHTML = `<div class="no-results" style="grid-column:1/-1"><svg width="40" height="40" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg><p>No people found matching your filters</p></div>`; return; }
  grid.innerHTML = data.map(p => `
    <a class="person-card" href="../profile/profile.html">
      <div class="avatar" style="background:${p.color};width:56px;height:56px;font-size:20px;margin:0 auto 12px">${p.init}</div>
      <div class="person-name">${p.name}</div>
      <div class="person-headline">${p.headline}</div>
      <div class="person-skills">${p.skills.map(s=>`<span class="skill-tag">${s}</span>`).join('')}</div>
      <div class="person-mutuals">${p.mutuals} mutual connections</div>
      <button class="btn btn-ghost btn-sm connect-btn-card" data-name="${p.name}" onclick="handleConnect(event,this)">${connectedUsers.has(p.name)?'✓ Connected':'+ Connect'}</button>
    </a>`).join('');
}

function renderProjects(data) {
  const grid = document.getElementById('project-results-grid');
  if (!data.length) { grid.innerHTML = `<div class="no-results" style="grid-column:1/-1"><p>No projects found</p></div>`; return; }
  grid.innerHTML = data.map(p => `
    <a class="exp-project-card" href="../projects/projects.html">
      <div class="epc-head">
        <div class="epc-emoji">${p.emoji}</div>
        <div><div class="epc-title">${p.title}</div><div class="epc-owner">by ${p.owner}</div></div>
      </div>
      <div style="display:flex;flex-wrap:wrap;gap:5px;margin-bottom:10px">${p.skills.map(s=>`<span class="skill-tag">${s}</span>`).join('')}</div>
      <div class="epc-footer">
        <span class="badge ${p.roles>1?'badge-success':'badge-warning'}">${p.roles} role${p.roles>1?'s':''} open</span>
        <span style="font-size:12px;color:var(--text-muted)">${p.members} member${p.members>1?'s':''}</span>
      </div>
    </a>`).join('');
}

function handleConnect(e, btn) {
  e.preventDefault();
  const name = btn.dataset.name;
  if (connectedUsers.has(name)) return;
  connectedUsers.add(name);
  btn.textContent = '✓ Connected';
  btn.style.background = 'rgba(34,197,94,0.1)';
  btn.style.color = 'var(--success)';
  btn.style.borderColor = 'rgba(34,197,94,0.2)';
  btn.disabled = true;
  showToast(`Connection request sent to ${name}`, 'success');
}

function filterAndRender(query = '') {
  const q = query.toLowerCase();
  const filteredPeople = PEOPLE.filter(p =>
    !q || p.name.toLowerCase().includes(q) ||
    p.headline.toLowerCase().includes(q) ||
    p.skills.some(s => s.toLowerCase().includes(q))
  );
  const filteredProjects = PROJECTS.filter(p =>
    !q || p.title.toLowerCase().includes(q) ||
    p.owner.toLowerCase().includes(q) ||
    p.skills.some(s => s.toLowerCase().includes(q))
  );
  renderPeople(filteredPeople);
  renderProjects(filteredProjects);
  document.getElementById('people-count').textContent = filteredPeople.length;
  document.getElementById('project-count').textContent = filteredProjects.length;
}

document.addEventListener('DOMContentLoaded', () => {
  renderSidebar('explore');
  loadNotificationCounts();
  renderTopbar('Explore');
  initRipples();
  initSearchShortcut();
  initFadeIn();
  filterAndRender();

  // ── Hero search ──
  document.getElementById('hero-search-form')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const q = this.querySelector('input').value;
    document.getElementById('filter-input')?.focus();
    filterAndRender(q);
    if (q) showToast(`Showing results for "${q}"`, 'info');
  });

  // ── Live search input ──
  document.getElementById('filter-input')?.addEventListener('input', function() {
    filterAndRender(this.value);
  });

  // ── Suggestion tags ──
  document.querySelectorAll('.sug-tag').forEach(tag => {
    tag.addEventListener('click', e => {
      e.preventDefault();
      const term = tag.textContent.replace('#', '').trim();
      const input = document.getElementById('filter-input');
      if (input) { input.value = term; filterAndRender(term); }
      showToast(`Searching for "${term}"`, 'info');
    });
  });

  // ── Section pills ──
  document.querySelectorAll('.section-pill').forEach(pill => {
    pill.addEventListener('click', () => {
      document.querySelectorAll('.section-pill').forEach(p => p.classList.remove('active'));
      pill.classList.add('active');
      activeSection = pill.dataset.section;
      const peopleSection = document.getElementById('section-people');
      const projectSection = document.getElementById('section-projects');
      if (activeSection === 'all') { peopleSection.style.display=''; projectSection.style.display=''; }
      else if (activeSection === 'people') { peopleSection.style.display=''; projectSection.style.display='none'; }
      else if (activeSection === 'projects') { peopleSection.style.display='none'; projectSection.style.display=''; }
    });
  });

  // ── Filter checkboxes ──
  document.querySelectorAll('.filter-option').forEach(opt => {
    opt.addEventListener('click', () => {
      const checkbox = opt.querySelector('.filter-checkbox');
      const filterId = opt.dataset.filter;
      checkbox.classList.toggle('checked');
      if (checkbox.classList.contains('checked')) {
        activeFilters.add(filterId);
      } else {
        activeFilters.delete(filterId);
      }
    });
  });

  // ── Clear filters ──
  document.getElementById('clear-filters')?.addEventListener('click', () => {
    document.querySelectorAll('.filter-checkbox').forEach(cb => cb.classList.remove('checked'));
    activeFilters.clear();
    filterAndRender('');
    if (document.getElementById('filter-input')) document.getElementById('filter-input').value = '';
    showToast('Filters cleared', 'info');
  });

  // ── Animated entry for hero ──
  const hero = document.querySelector('.explore-hero');
  if (hero) {
    hero.style.opacity = '0'; hero.style.transform = 'translateY(-12px)';
    hero.style.transition = 'all 0.4s ease';
    setTimeout(() => { hero.style.opacity = '1'; hero.style.transform = 'translateY(0)'; }, 50);
  }
});
