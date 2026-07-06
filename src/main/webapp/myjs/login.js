// ── Tab switching ──
document.querySelectorAll('.auth-tab').forEach(tab => {
  tab.addEventListener('click', () => {
    document.querySelectorAll('.auth-tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.form-panel').forEach(p => p.classList.remove('active'));
    tab.classList.add('active');
    document.getElementById(tab.dataset.tab).classList.add('active');
  });
});

// ── Role card selection ──
document.querySelectorAll('.role-card').forEach(card => {
  card.addEventListener('click', () => {
    document.querySelectorAll('.role-card').forEach(c => c.classList.remove('selected'));
    card.classList.add('selected');
    document.querySelector('[name="role"]').value = card.dataset.role;
  });
});

// ── Password visibility toggle ──
document.querySelectorAll('.input-toggle').forEach(btn => {
  btn.addEventListener('click', () => {
    const input = btn.previousElementSibling;
    const isText = input.type === 'text';
    input.type = isText ? 'password' : 'text';
    btn.innerHTML = isText
      ? `<svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>`
      : `<svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>`;
  });
});

// ── Password strength ──
const pwdInput = document.getElementById('reg-password');
const segments = document.querySelectorAll('.strength-seg');
const strengthLabel = document.querySelector('.strength-label');
const colors = ['', '#ef4444', '#f59e0b', '#22c55e', '#6c63ff'];
const labels = ['', 'Weak', 'Fair', 'Good', 'Strong'];

function calcStrength(pwd) {
  let s = 0;
  if (pwd.length >= 8) s++;
  if (/[A-Z]/.test(pwd)) s++;
  if (/[0-9]/.test(pwd)) s++;
  if (/[^A-Za-z0-9]/.test(pwd)) s++;
  return s;
}

pwdInput && pwdInput.addEventListener('input', () => {
  const s = calcStrength(pwdInput.value);
  segments.forEach((seg, i) => { seg.style.background = i < s ? colors[s] : ''; });
  if (strengthLabel) { strengthLabel.textContent = pwdInput.value ? labels[s] : ''; strengthLabel.style.color = colors[s]; }
});

// ── Field validation ──
function validateEmail(input) {
  const ok = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input.value);
  const err = input.closest('.form-group')?.querySelector('.field-error') || input.parentElement.querySelector('.field-error');
  if (!ok) { input.classList.add('error'); if (err) { err.textContent = 'Enter a valid email address'; err.classList.add('show'); } }
  else { input.classList.remove('error'); if (err) err.classList.remove('show'); }
  return ok;
}
function validateRequired(input, msg) {
  const ok = !!input.value.trim();
  const err = input.closest('.form-group')?.querySelector('.field-error');
  if (!ok) { input.classList.add('error'); if (err) { err.textContent = msg; err.classList.add('show'); } }
  else { input.classList.remove('error'); if (err) err.classList.remove('show'); }
  return ok;
}
document.querySelectorAll('.input').forEach(inp => {
  inp.addEventListener('input', () => {
    inp.classList.remove('error');
    const err = inp.closest('.form-group')?.querySelector('.field-error') || inp.parentElement.querySelector('.field-error');
    if (err) err.classList.remove('show');
  });
});

// ── Login submit ──
/* document.getElementById('login-form')?.addEventListener('submit', function(e) {
  e.preventDefault();
  const emailEl = this.querySelector('[name="email"]');
  const passEl = this.querySelector('[name="password"]');
  let valid = validateEmail(emailEl) & validateRequired(passEl, 'Password is required');
  if (!valid) return;
  const btn = this.querySelector('[type="submit"]');
  btn.classList.add('btn-loading'); btn.disabled = true;
  setTimeout(() => {
    btn.classList.remove('btn-loading'); btn.disabled = false;
    showToast('Signed in successfully!', 'success');
    setTimeout(() => { window.location.href = '../myhtml/dashboard.html'; }, 800);
  }, 1400);
});*/

// ── Register submit ──
/* document.getElementById('register-form')?.addEventListener('submit', function(e) {
  e.preventDefault();
  const fnEl = this.querySelector('[name="first_name"]');
  const lnEl = this.querySelector('[name="last_name"]');
  const emailEl = this.querySelector('[name="email"]');
  const passEl = document.getElementById('reg-password');
  let valid = validateRequired(fnEl, 'Required') & validateRequired(lnEl, 'Required') & validateEmail(emailEl) & validateRequired(passEl, 'Password required');
  if (passEl.value.length < 8) { passEl.classList.add('error'); showToast('Password must be at least 8 characters', 'error'); valid = false; }
  if (!valid) return;
  const btn = this.querySelector('[type="submit"]');
  btn.classList.add('btn-loading'); btn.disabled = true;
  setTimeout(() => {
    btn.classList.remove('btn-loading'); btn.disabled = false;
    showToast('Account created! Welcome to Nexus 🎉', 'success');
    setTimeout(() => { window.location.href = '../dashboard/dashboard.html'; }, 900);
  }, 1500);
});*/

// ── Toast ──
function showToast(message, type = 'info') {
  const icons = {
    success: `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>`,
    error:   `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>`,
    info:    `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>`,
  };
  let c = document.querySelector('.toast-container');
  if (!c) { c = document.createElement('div'); c.className = 'toast-container'; document.body.appendChild(c); }
  const t = document.createElement('div');
  t.className = `toast toast-${type}`;
  t.innerHTML = `<div class="toast-icon">${icons[type]}</div><span>${message}</span>`;
  c.appendChild(t);
  setTimeout(() => { t.classList.add('hide'); setTimeout(() => t.remove(), 300); }, 3500);
}
