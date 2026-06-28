import React from "react";
import { SearchTabs } from "../SearchTabs";
import { HeroStats } from "./HeroStats";

export const Hero: React.FC = () => {
  return (
    <section className="relative min-h-screen overflow-hidden">

      {/* Imagen de fondo */}
      <img
        src="https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=2000&q=80"
        alt="Luxury Hotel"
        className="absolute inset-0 h-full w-full object-cover"
      />

      {/* Oscurecer imagen */}
      <div className="absolute inset-0 bg-black/55" />

      {/* Degradado */}
      <div className="absolute inset-0 bg-gradient-to-b from-slate-950/30 via-slate-950/40 to-slate-950" />

      {/* Círculos decorativos */}
      <div className="absolute left-[-200px] top-[-150px] h-[500px] w-[500px] rounded-full bg-blue-500/20 blur-[140px]" />

      <div className="absolute right-[-200px] bottom-[-200px] h-[500px] w-[500px] rounded-full bg-indigo-500/20 blur-[150px]" />

      {/* Contenido */}
      <div className="relative z-20 flex min-h-screen items-center">

        <div className="mx-auto flex max-w-7xl flex-col items-center px-6 text-center">

          {/* Badge */}
          <div className="mb-8 rounded-full border border-white/20 bg-white/10 px-6 py-2 backdrop-blur-xl">
            <span className="text-xs font-bold uppercase tracking-[4px] text-white">
              Luxury Travel Experience
            </span>
          </div>

          {/* Título */}
          <h1 className="max-w-5xl text-5xl font-black leading-tight text-white md:text-7xl">
            Descubre hoteles y vuelos
            <br />

            <span className="bg-gradient-to-r from-blue-300 via-indigo-200 to-white bg-clip-text text-transparent">
              para experiencias inolvidables
            </span>
          </h1>

          {/* Descripción */}
          <p className="mt-8 max-w-3xl text-lg leading-8 text-slate-200">
            Reserva hoteles exclusivos y vuelos internacionales con disponibilidad
            en tiempo real gracias a nuestra integración directa con el sistema de
            reservas.
          </p>

          {/* Buscador */}
          <div className="mt-14 w-full max-w-5xl rounded-3xl border border-white/20 bg-white/10 p-6 backdrop-blur-2xl shadow-2xl">

            <SearchTabs
              onSearchFlights={(data) =>
                console.log("Buscar vuelos:", data)
              }
              onSearchHotels={(data) =>
                console.log("Buscar hoteles:", data)
              }
            />

          </div>

          {/* Estadísticas */}
          <div className="mt-16 w-full">
            <HeroStats />
          </div>

        </div>
      </div>

      {/* Fade inferior */}
      <div className="absolute bottom-0 left-0 h-40 w-full bg-gradient-to-t from-slate-50 to-transparent" />
    </section>
  );
};