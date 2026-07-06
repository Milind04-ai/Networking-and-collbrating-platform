document.addEventListener('DOMContentLoaded', () => {
  renderSidebar('applications');
  loadNotificationCounts();
  renderTopbar('Applications & Teams');
  initRipples();
  initDropdowns();
  initModals();
  initSearchShortcut();
  initCountUp();

  // ── Withdraw application ──
  document.querySelectorAll('.withdraw-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const card = btn.closest('.app-card');
      if (!confirm('Withdraw this application?')) return;
      card.style.transition = 'opacity 0.3s, transform 0.3s';
      card.style.opacity = '0';
      card.style.transform = 'translateX(-20px)';
      setTimeout(() => card.remove(), 300);
      showToast('Application withdrawn', 'info');
      // Update stat
      const pendingEl = document.querySelector('.stat-pending');
      if (pendingEl) {
        const n = parseInt(pendingEl.textContent) - 1;
        pendingEl.textContent = Math.max(0, n);
      }
    });
  });
  
  // ── Animated stat cards ──
  document.querySelectorAll('.app-stat').forEach((card, i) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(12px)';
    card.style.transition = `all 0.3s ease ${i * 0.07}s`;
    setTimeout(() => {
      card.style.opacity = '1';
      card.style.transform = 'translateY(0)';
    }, 100);
  });

  // ── App cards staggered animation ──
  document.querySelectorAll('.app-card, .incoming-card').forEach((card, i) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(12px)';
    card.style.transition = `all 0.3s ease ${0.2 + i * 0.06}s`;
    setTimeout(() => {
      card.style.opacity = '1';
      card.style.transform = 'translateY(0)';
    }, 100);
  });
});
