// ── Toast ──
function showToast(message, type = 'info') {
  const icons = { success: '✓', info: 'i', error: '✕' };
  let c = document.querySelector('.toast-container');
  if (!c) { c = document.createElement('div'); c.className = 'toast-container'; document.body.appendChild(c); }
  const t = document.createElement('div');
  t.className = `toast toast-${type}`;
  t.innerHTML = `<div class="toast-icon">${icons[type]}</div><span>${message}</span>`;
  c.appendChild(t);
  setTimeout(() => { t.classList.add('hide'); setTimeout(() => t.remove(), 300); }, 3500);
}

// ── Count up animation ──
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
renderSidebar('dashboard');
  renderTopbar('dashboard');

  loadNotificationCounts();

  // Count up stats
  document.querySelectorAll('[data-count]').forEach(countUp);

  // ── Feed filter tabs ──
  document.querySelectorAll('.feed-filter').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.feed-filter').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      const filter = btn.dataset.filter;
      document.querySelectorAll('.feed-item').forEach(item => {
        const show = filter === 'all' || item.dataset.type === filter;
        item.style.display = show ? '' : 'none';
        if (show) { item.style.animation = 'none'; item.offsetHeight; item.style.animation = 'fadeIn 0.3s ease'; }
      });
    });
  });

  // ── Like button ──
  document.querySelectorAll('.feed-btn.like-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const liked = btn.classList.toggle('liked');
      const countEl = btn.querySelector('.like-count');
      if (countEl) {
        const n = parseInt(countEl.textContent, 10);
        countEl.textContent = liked ? n + 1 : n - 1;
        countEl.style.transform = 'scale(1.4)';
        setTimeout(() => { countEl.style.transform = ''; countEl.style.transition = 'transform 0.2s'; }, 200);
      }
      if (liked) showToast('Added to your interests', 'success');
    });
  });

  // ── Connect buttons ──
  document.querySelectorAll('.btn-connect').forEach(btn => {
    btn.addEventListener('click', () => {
      const name = btn.closest('.suggestion-item')?.querySelector('.suggestion-name')?.textContent || 'User';
      btn.textContent = '✓ Sent';
      btn.style.cssText += 'background:rgba(34,197,94,0.1);color:var(--success);border-color:rgba(34,197,94,0.2);pointer-events:none';
      showToast(`Connection request sent to ${name}`, 'success');
    });
  });

  // ── Project preview click ──
  document.querySelectorAll('.project-preview').forEach(card => {
    card.addEventListener('click', () => { window.location.href = '/Nexus/projects'; });
  });

  // ── Live dot pulse ──
  const dot = document.getElementById('live-dot');
  if (dot) setInterval(() => { dot.style.opacity = '0'; setTimeout(() => { dot.style.opacity = '1'; }, 400); }, 3000);

  // ── Staggered feed item entry ──
  document.querySelectorAll('.feed-item').forEach((item, i) => {
    item.style.opacity = '0';
    item.style.transform = 'translateY(16px)';
    item.style.transition = `opacity 0.35s ease ${i * 0.09}s, transform 0.35s ease ${i * 0.09}s`;
    setTimeout(() => { item.style.opacity = '1'; item.style.transform = 'translateY(0)'; }, 80);
  });

  // ── / shortcut for search ──
  document.addEventListener('keydown', e => {
    if (e.key === '/' && document.activeElement.tagName !== 'INPUT') {
      e.preventDefault();
      document.getElementById('search-input')?.focus();
    }
  });
});
