import React from "react";

interface NavbarProps {
  onLoginClick: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({ onLoginClick }) => {
  return (
    <nav className="fixed top-5 left-1/2 -translate-x-1/2 z-50 w-[95%] max-w-7xl">
      <div className="flex items-center justify-between rounded-2xl border border-white/20 bg-white/10 px-6 py-4 backdrop-blur-xl shadow-2xl">

        {/* Logo */}
        <div className="flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-gradient-to-br from-blue-600 to-indigo-600 text-xl font-black text-white shadow-lg shadow-blue-600/30">
            N
          </div>

          <div>
            <h1 className="text-lg font-black tracking-tight text-white">
              Nomadic
            </h1>
            <p className="text-xs text-slate-300">
              Luxury Travel
            </p>
          </div>
        </div>

        {/* Menú */}
        <div className="hidden items-center gap-8 md:flex">
          <a
            href="#destinos"
            className="text-sm font-semibold text-white/80 transition hover:text-white"
          >
            Destinos
          </a>

          <a
            href="#hoteles"
            className="text-sm font-semibold text-white/80 transition hover:text-white"
          >
            Hoteles
          </a>

          <a
            href="#vuelos"
            className="text-sm font-semibold text-white/80 transition hover:text-white"
          >
            Vuelos
          </a>

          <a
            href="#contacto"
            className="text-sm font-semibold text-white/80 transition hover:text-white"
          >
            Contacto
          </a>
        </div>
        <button
          onClick={onLoginClick}
          className="group relative overflow-hidden rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 text-sm font-bold text-white shadow-lg shadow-blue-600/20 transition duration-300 hover:scale-105 hover:shadow-blue-600/40"
        >
          <span className="absolute inset-0 bg-white/10 opacity-0 transition-opacity duration-300 group-hover:opacity-100" />

          <span className="relative z-10">
            Iniciar sesión
          </span>
        </button>
      </div>
    </nav>
  );
};