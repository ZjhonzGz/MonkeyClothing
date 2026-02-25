/**
 * MONKEY CLOTHING — js.js
 * Loader · Nav · Hero Slider · Reveal · Smooth Scroll ·
 * Modales de Mapa · Newsletter Banner + Modal
 */

'use strict';

/* ================================================
   TIENDAS — datos centralizados
   ================================================ */
const STORES = [
  { id: 1, name: 'Lima Centro',  addr: 'Jr. de la Unión 456, Lima', lat: -12.0464, lng: -77.0428 },
  { id: 2, name: 'Miraflores',   addr: 'Av. Larco 890, Piso 2',     lat: -12.1182, lng: -77.0298 },
  { id: 3, name: 'San Isidro',   addr: 'Av. El Golf 234',           lat: -12.0983, lng: -77.0356 },
];

/* ================================================
   LOADER
   ================================================ */
(function initLoader() {
  const loader = document.getElementById('loader');
  const fill   = document.getElementById('loaderFill');
  if (!loader || !fill) return;

  requestAnimationFrame(() => { fill.style.width = '100%'; });

  function hideLoader() {
    loader.classList.add('hidden');
    document.body.classList.add('loaded');
    loader.addEventListener('transitionend', () => loader.remove(), { once: true });
  }

  if (document.readyState === 'complete') {
    setTimeout(hideLoader, 800);
  } else {
    window.addEventListener('load', () => setTimeout(hideLoader, 800));
  }
})();

/* ================================================
   NAV — Scroll + Mobile Toggle
   ================================================ */
(function initNav() {
  const nav    = document.getElementById('nav');
  const toggle = document.getElementById('navToggle');
  const links  = document.getElementById('navLinks');
  if (!nav) return;

  function onScroll() { nav.classList.toggle('scrolled', window.scrollY > 30); }
  window.addEventListener('scroll', onScroll, { passive: true });
  onScroll();

  if (toggle && links) {
    toggle.addEventListener('click', () => {
      const isOpen = links.classList.toggle('open');
      toggle.classList.toggle('open', isOpen);
      toggle.setAttribute('aria-expanded', isOpen);
      document.body.style.overflow = isOpen ? 'hidden' : '';
    });
    links.querySelectorAll('.nav-link').forEach(link => {
      link.addEventListener('click', () => {
        links.classList.remove('open');
        toggle.classList.remove('open');
        toggle.setAttribute('aria-expanded', 'false');
        document.body.style.overflow = '';
      });
    });
  }

  document.addEventListener('keydown', e => {
    if (e.key === 'Escape' && links && links.classList.contains('open')) {
      links.classList.remove('open');
      toggle && toggle.classList.remove('open');
      toggle && toggle.setAttribute('aria-expanded', 'false');
      document.body.style.overflow = '';
    }
  });
})();

/* ================================================
   HERO SLIDESHOW
   ================================================ */
(function initHeroSlider() {
  const slides     = document.querySelectorAll('.hero-slide');
  const indicators = document.querySelectorAll('.indicator');
  if (!slides.length) return;

  let current = 0, timer = null;
  const DELAY = 5000;

  function goTo(index) {
    slides[current].classList.remove('active');
    indicators[current] && indicators[current].classList.remove('active');
    current = (index + slides.length) % slides.length;
    slides[current].classList.add('active');
    indicators[current] && indicators[current].classList.add('active');
  }

  function startTimer() {
    clearInterval(timer);
    timer = setInterval(() => goTo(current + 1), DELAY);
  }

  indicators.forEach((btn, i) => btn.addEventListener('click', () => { goTo(i); startTimer(); }));
  startTimer();

  document.addEventListener('visibilitychange', () => {
    document.hidden ? clearInterval(timer) : startTimer();
  });
})();

/* ================================================
   REVEAL ON SCROLL
   ================================================ */
(function initReveal() {
  const elements = document.querySelectorAll('.reveal');
  if (!elements.length) return;

  const observer = new IntersectionObserver(
    entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.1, rootMargin: '0px 0px -40px 0px' }
  );
  elements.forEach(el => observer.observe(el));
})();

/* ================================================
   SMOOTH SCROLL
   ================================================ */
(function initSmoothScroll() {
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      const target = document.querySelector(this.getAttribute('href'));
      if (!target) return;
      e.preventDefault();
      const navH = parseInt(
        getComputedStyle(document.documentElement).getPropertyValue('--nav-h')
      ) || 72;
      window.scrollTo({
        top: target.getBoundingClientRect().top + window.scrollY - navH,
        behavior: 'smooth',
      });
    });
  });
})();

/* ================================================
   NAV LINK ACTIVO POR SECCIÓN
   ================================================ */
(function initActiveNavLink() {
  const sections = document.querySelectorAll('section[id]');
  const navLinks = document.querySelectorAll('.nav-link');
  if (!sections.length || !navLinks.length) return;

  const observer = new IntersectionObserver(
    entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const id = entry.target.getAttribute('id');
          navLinks.forEach(link => {
            link.classList.toggle('active', link.getAttribute('href') === `#${id}`);
          });
        }
      });
    },
    { rootMargin: '-40% 0px -55% 0px' }
  );
  sections.forEach(s => observer.observe(s));
})();

/* ================================================
   NEWSLETTER — BANNER FLOTANTE
   Aparece 5 s tras la carga, solo si el usuario
   no se suscribió en esta sesión
   ================================================ */
(function initNewsletterBanner() {
  if (sessionStorage.getItem('nl_ok')) return;   // ya suscrito: no mostrar
  const banner = document.getElementById('nlBanner');
  if (!banner) return;
  setTimeout(() => banner.classList.add('nl-visible'), 5000);
})();

function nlCerrarBanner() {
  const b = document.getElementById('nlBanner');
  if (b) { b.classList.remove('nl-visible'); b.classList.add('nl-hidden'); }
}

/* ================================================
   NEWSLETTER — MODAL
   ================================================ */
function nlAbrirModal() {
  nlCerrarBanner();
  document.getElementById('nlOverlay').classList.add('nl-open');
  document.getElementById('nlModal').classList.add('nl-open');
  document.body.style.overflow = 'hidden';
  setTimeout(() => document.getElementById('nlNombre')?.focus(), 350);
}

function nlCerrarModal() {
  document.getElementById('nlOverlay').classList.remove('nl-open');
  document.getElementById('nlModal').classList.remove('nl-open');
  document.body.style.overflow = '';
  // Resetea formulario tras animación de cierre
  setTimeout(nlReset, 380);
}

function nlReset() {
  const formState    = document.getElementById('nlFormState');
  const successState = document.getElementById('nlSuccessState');
  if (formState)    formState.style.display    = '';
  if (successState) successState.style.display = 'none';

  ['nlNombre', 'nlTelefono', 'nlEmail'].forEach(id => {
    const el = document.getElementById(id);
    if (el) el.value = '';
  });
  ['nlErrNombre', 'nlErrEmail'].forEach(id => {
    const el = document.getElementById(id);
    if (el) el.textContent = '';
  });

  const btn = document.getElementById('nlSubmitBtn');
  const txt = document.getElementById('nlSubmitTxt');
  if (btn) btn.disabled = false;
  if (txt) txt.textContent = 'Suscribirme';
}

async function nlEnviar() {
  const nombre   = (document.getElementById('nlNombre')?.value   || '').trim();
  const telefono = (document.getElementById('nlTelefono')?.value || '').trim();
  const email    = (document.getElementById('nlEmail')?.value    || '').trim();

  // Limpia errores previos
  document.getElementById('nlErrNombre').textContent = '';
  document.getElementById('nlErrEmail').textContent  = '';

  // Validación
  let valid = true;
  if (!nombre) {
    document.getElementById('nlErrNombre').textContent = 'El nombre es obligatorio.';
    document.getElementById('nlNombre').focus();
    valid = false;
  }
  if (!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    document.getElementById('nlErrEmail').textContent = 'Ingresa un email válido.';
    if (valid) document.getElementById('nlEmail').focus();
    valid = false;
  }
  if (!valid) return;

  const btn = document.getElementById('nlSubmitBtn');
  const txt = document.getElementById('nlSubmitTxt');
  btn.disabled = true;
  txt.textContent = 'Enviando...';

  try {
    const res  = await fetch('/api/newsletter', {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombre, telefono, email }),
    });
    const data = await res.json();

    if (data.ok) {
      // Marca en sesión para no volver a mostrar el banner
      sessionStorage.setItem('nl_ok', '1');
      // Muestra pantalla de éxito
      document.getElementById('nlFormState').style.display    = 'none';
      document.getElementById('nlSuccessState').style.display = '';
    } else {
      // Error de validación del servidor
      document.getElementById('nlErrEmail').textContent = data.msg || 'Error al suscribirse.';
      btn.disabled = false;
      txt.textContent = 'Suscribirme';
    }
  } catch {
    document.getElementById('nlErrEmail').textContent = 'Error de conexión. Intenta de nuevo.';
    btn.disabled = false;
    txt.textContent = 'Suscribirme';
  }
}

// Cerrar modal newsletter con Escape
document.addEventListener('keydown', e => {
  if (e.key === 'Escape') {
    if (document.getElementById('nlModal')?.classList.contains('nl-open')) {
      nlCerrarModal();
    } else {
      closeAllModals();
    }
  }
});

/* ================================================
   MODALES DE MAPA  (sin cambios respecto al original)
   ================================================ */
function buildEmbedSingle(lat, lng) {
  return `https://maps.google.com/maps?q=${lat},${lng}&z=16&output=embed`;
}

function openMapSingle(lat, lng, name, addr) {
  const modal    = document.getElementById('modalSingle');
  const backdrop = document.getElementById('mapBackdrop');
  const iframe   = document.getElementById('iframeSingle');

  document.getElementById('modalSingleTitle').textContent = name;
  document.getElementById('modalSingleAddr').textContent  = addr;
  iframe.src = buildEmbedSingle(lat, lng);
  document.getElementById('linkSingleDir').href =
    `https://www.google.com/maps/dir/?api=1&destination=${lat},${lng}`;

  modal.classList.add('active');
  backdrop.classList.add('active');
  document.body.style.overflow = 'hidden';
}

function openMapAll() {
  const modal    = document.getElementById('modalAll');
  const backdrop = document.getElementById('mapBackdrop');
  const iframe   = document.getElementById('iframeAll');
  const statusEl = document.getElementById('locStatusText');
  const locRow   = document.getElementById('locationStatus');

  iframe.src = buildEmbedSingle(STORES[0].lat, STORES[0].lng);
  modal.classList.add('active');
  backdrop.classList.add('active');
  document.body.style.overflow = 'hidden';

  if (!navigator.geolocation) {
    statusEl.textContent = 'Geolocalización no disponible';
    locRow.classList.add('error');
    return;
  }

  statusEl.textContent = 'Detectando tu ubicación...';
  locRow.classList.remove('found', 'error');

  navigator.geolocation.getCurrentPosition(
    pos => {
      const { latitude: lat, longitude: lng } = pos.coords;
      iframe.src = `https://maps.google.com/maps?q=${lat},${lng}&z=13&output=embed`;

      const nearest = STORES.reduce((prev, curr) =>
        dist(lat, lng, curr.lat, curr.lng) < dist(lat, lng, prev.lat, prev.lng) ? curr : prev
      );
      const km = dist(lat, lng, nearest.lat, nearest.lng).toFixed(1);
      statusEl.textContent = `Tu tienda más cercana: ${nearest.name} (${km} km)`;
      locRow.classList.add('found');

      document.querySelectorAll('.map-store-card').forEach((card, i) => {
        card.classList.toggle('active', i === nearest.id - 1);
      });
    },
    () => {
      statusEl.textContent = 'No se pudo obtener tu ubicación';
      locRow.classList.add('error');
    },
    { timeout: 8000 }
  );
}

function flyTo(lat, lng) {
  const iframe = document.getElementById('iframeAll');
  if (iframe) iframe.src = buildEmbedSingle(lat, lng);
  document.querySelectorAll('.map-store-card').forEach(card => {
    card.classList.toggle('active', (card.getAttribute('onclick') || '').includes(`${lat}`));
  });
}

function closeModal(id) {
  document.getElementById(id).classList.remove('active');
  const anyOpen = document.querySelector('.map-modal.active');
  if (!anyOpen) {
    document.getElementById('mapBackdrop').classList.remove('active');
    document.body.style.overflow = '';
    const iframe = document.querySelector(`#${id} iframe`);
    if (iframe) setTimeout(() => { iframe.src = ''; }, 350);
  }
}

function closeAllModals() {
  document.querySelectorAll('.map-modal.active').forEach(m => {
    m.classList.remove('active');
    const iframe = m.querySelector('iframe');
    if (iframe) setTimeout(() => { iframe.src = ''; }, 350);
  });
  const bd = document.getElementById('mapBackdrop');
  if (bd) bd.classList.remove('active');
  document.body.style.overflow = '';
}

/* ================================================
   UTILIDAD — Distancia Haversine (km)
   ================================================ */
function dist(lat1, lng1, lat2, lng2) {
  const R    = 6371;
  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLng = ((lng2 - lng1) * Math.PI) / 180;
  const a    =
    Math.sin(dLat / 2) ** 2 +
    Math.cos((lat1 * Math.PI) / 180) *
    Math.cos((lat2 * Math.PI) / 180) *
    Math.sin(dLng / 2) ** 2;
  return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
}