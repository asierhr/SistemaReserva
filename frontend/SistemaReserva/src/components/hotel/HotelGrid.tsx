import React from "react";
import { type Hotel } from "../../types/hotel/hotel.ts";
import { HotelCard } from "./HotelCard.tsx";

interface HotelGridProps {
  hotels: Hotel[];
}

export const HotelGrid: React.FC<HotelGridProps> = ({ hotels }) => {
  return (
    <section
      id="hoteles"
      className="bg-gradient-to-b from-slate-50 via-white to-slate-100 py-24"
    >
      <div className="mx-auto max-w-7xl px-6">

        {/* Encabezado */}
        <div className="mb-16 flex flex-col gap-6 md:flex-row md:items-end md:justify-between">
          <div>
            <span className="rounded-full bg-blue-100 px-4 py-2 text-xs font-bold uppercase tracking-[3px] text-blue-600">
              Hoteles destacados
            </span>

            <h2 className="mt-5 text-4xl font-black text-slate-900 md:text-5xl">
              Encuentra tu próxima estancia
            </h2>

            <p className="mt-4 max-w-2xl text-lg text-slate-500">
              Descubre hoteles cuidadosamente seleccionados con disponibilidad
              en tiempo real y habitaciones verificadas.
            </p>
          </div>

          <button
            className="
              rounded-xl
              border
              border-slate-300
              bg-white
              px-6
              py-3
              font-semibold
              text-slate-700
              transition
              hover:border-blue-600
              hover:bg-blue-600
              hover:text-white
            "
          >
            Ver todos
          </button>
        </div>

        {/* Grid */}
        {hotels.length > 0 ? (
          <div className="grid gap-8 sm:grid-cols-2 xl:grid-cols-3">
            {hotels.map((hotel) => (
              <HotelCard key={hotel.id} hotel={hotel} />
            ))}
          </div>
        ) : (
          <div className="flex h-64 items-center justify-center rounded-3xl border border-dashed border-slate-300 bg-white">
            <div className="text-center">
              <h3 className="text-xl font-bold text-slate-700">
                No hay hoteles disponibles
              </h3>

              <p className="mt-2 text-slate-500">
                Intenta realizar una búsqueda diferente.
              </p>
            </div>
          </div>
        )}
      </div>
    </section>
  );
};