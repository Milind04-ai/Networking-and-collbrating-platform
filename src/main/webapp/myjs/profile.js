// ── Toast ──
function showToast(message, type = 'info') {
  let c = document.querySelector('.toast-container');
  if (!c) { c = document.createElement('div'); c.className = 'toast-container'; document.body.appendChild(c); }
  const t = document.createElement('div');
  t.className = `toast toast-${type}`;
  t.innerHTML = `<div class="toast-icon">${type==='success'?'✓':'i'}</div><span>${message}</span>`;
  c.appendChild(t);
  setTimeout(() => { t.classList.add('hide'); setTimeout(() => t.remove(), 300); }, 3500);
}

// ── Count up ──
function countUp(el) {
  const target = parseInt(el.dataset.count || el.textContent, 10);
  if (isNaN(target)) return;
  let current = 0;
  const step = Math.ceil(target / 30);
  const timer = setInterval(() => {
    current = Math.min(current + step, target);
    el.textContent = current.toLocaleString();
    if (current >= target) clearInterval(timer);
  }, 30);
}

document.addEventListener('DOMContentLoaded', () => {
renderSidebar('profile');
  renderTopbar('My Profile');

  loadNotificationCounts();
  document.querySelectorAll('[data-count]').forEach(countUp);

  // ── Tabs ──
  document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
      document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
    });
  });

  // ── See more / less ──
  const aboutText = document.getElementById('about-text');
  const seeMoreBtn = document.getElementById('see-more-btn');
  if (aboutText && seeMoreBtn) {
    aboutText.classList.add('collapsed');
    seeMoreBtn.addEventListener('click', () => {
      const collapsed = aboutText.classList.toggle('collapsed');
      seeMoreBtn.textContent = collapsed ? 'See more' : 'See less';
    });
  }

  // ── Portfolio item click ──
  document.querySelectorAll('.portfolio-item').forEach(item => {
    item.addEventListener('click', () => {
      const title = item.dataset.title || item.querySelector('.portfolio-title')?.textContent;
      showToast(`Opening "${title}"…`, 'info');
    });
  });

  // ── Heatmap generation ──
  const heatmap = document.getElementById('heatmap');
  if (heatmap) {
    heatmap.innerHTML = '';
    const levels = ['', '', 'l1', 'l1', 'l2', 'l2', 'l3', 'l4'];
    for (let i = 0; i < 112; i++) {
      const cell = document.createElement('div');
      cell.className = 'hm-cell ' + levels[Math.floor(Math.random() * levels.length)];
      heatmap.appendChild(cell);
    }
  }

  // ── Edit modal ──
  const modal = document.getElementById('edit-modal');
  ['open-edit-modal', 'open-edit-modal-2'].forEach(id => {
    document.getElementById(id)?.addEventListener('click', () => modal?.classList.add('open'));
  });
  ['close-modal', 'cancel-modal'].forEach(id => {
    document.getElementById(id)?.addEventListener('click', () => modal?.classList.remove('open'));
  });
  modal?.addEventListener('click', e => { if (e.target === modal) modal.classList.remove('open'); });

  // ── Bio char counter ──
  const bio = document.getElementById('edit-bio');
  const bioCount = document.getElementById('bio-count');
  bio?.addEventListener('input', () => {
    const len = bio.value.length;
    if (bioCount) { bioCount.textContent = `${len}/500`; bioCount.style.color = len > 450 ? 'var(--warning)' : ''; }
  });

  // ── Cover photo click ──
  document.getElementById('cover-photo')?.addEventListener('click', () => {
    showToast('Cover photo upload coming soon', 'info');
  });

  // ── Staggered section entry ──
  document.querySelectorAll('.section-card').forEach((s, i) => {
    s.style.opacity = '0';
    s.style.transform = 'translateY(16px)';
    s.style.transition = `opacity 0.35s ease ${i * 0.07}s, transform 0.35s ease ${i * 0.07}s`;
    setTimeout(() => { s.style.opacity = '1'; s.style.transform = 'translateY(0)'; }, 80);
  });

  // ── Keyboard: Escape closes modal ──
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape') modal?.classList.remove('open');
  });
});
