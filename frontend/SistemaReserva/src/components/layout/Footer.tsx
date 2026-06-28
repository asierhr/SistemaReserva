import React from "react";

export const Footer: React.FC = () => {
  const year = new Date().getFullYear();

  return (
    <footer
      id="contacto"
      className="bg-slate-950 text-slate-300 border-t border-slate-800"
    >
      <div className="mx-auto max-w-7xl px-6 py-20">
        <div className="grid gap-12 md:grid-cols-2 lg:grid-cols-4">

          {/* Logo */}
          <div>
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-to-br from-blue-600 to-indigo-600 text-xl font-black text-white">
                N
              </div>

              <div>
                <h2 className="text-xl font-black text-white">
                  Nomadic
                </h2>

                <p className="text-sm text-slate-400">
                  Luxury Travel
                </p>
              </div>
            </div>

            <p className="mt-6 leading-7 text-slate-400">
              Reserva hoteles exclusivos y vuelos internacionales con una
              experiencia moderna, rápida y segura.
            </p>
          </div>

          {/* Empresa */}
          <div>
            <h3 className="mb-6 text-lg font-bold text-white">
              Empresa
            </h3>

            <ul className="space-y-3">
              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Sobre nosotros
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Destinos
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Hoteles
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Vuelos
                </a>
              </li>
            </ul>
          </div>

          {/* Ayuda */}
          <div>
            <h3 className="mb-6 text-lg font-bold text-white">
              Soporte
            </h3>

            <ul className="space-y-3">
              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Centro de ayuda
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Contacto
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Política de privacidad
                </a>
              </li>

              <li>
                <a
                  href="#"
                  className="transition hover:text-white"
                >
                  Términos y condiciones
                </a>
              </li>
            </ul>
          </div>

          {/* Contacto */}
          <div>
            <h3 className="mb-6 text-lg font-bold text-white">
              Contacto
            </h3>

            <div className="space-y-3 text-slate-400">
              <p>📍 Barcelona, España</p>
              <p>✉️ info@nomadictravel.com</p>
              <p>📞 +34 600 123 456</p>
            </div>

            {/* Redes */}
            <div className="mt-8 flex gap-4">

              <button className="flex h-11 w-11 items-center justify-center rounded-xl bg-slate-900 transition hover:bg-blue-600">
                🌐
              </button>

              <button className="flex h-11 w-11 items-center justify-center rounded-xl bg-slate-900 transition hover:bg-sky-500">
                🐦
              </button>

              <button className="flex h-11 w-11 items-center justify-center rounded-xl bg-slate-900 transition hover:bg-pink-600">
                📷
              </button>

              <button className="flex h-11 w-11 items-center justify-center rounded-xl bg-slate-900 transition hover:bg-blue-700">
                💼
              </button>

            </div>
          </div>
        </div>

        {/* Línea */}
        <div className="my-12 h-px bg-slate-800" />

        {/* Copyright */}
        <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
          <p className="text-sm text-slate-500">
            © {year} Nomadic Luxury Travel. Todos los derechos reservados.
          </p>

          <div className="flex gap-6 text-sm text-slate-500">
            <a
              href="#"
              className="transition hover:text-white"
            >
              Cookies
            </a>

            <a
              href="#"
              className="transition hover:text-white"
            >
              Privacidad
            </a>

            <a
              href="#"
              className="transition hover:text-white"
            >
              Aviso Legal
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};