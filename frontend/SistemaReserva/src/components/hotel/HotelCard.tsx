import React from "react";
import { type Hotel } from "../../types/hotel/hotel";

interface HotelCardProps {
  hotel: Hotel;
}

export const HotelCard: React.FC<HotelCardProps> = ({ hotel }) => {
  const availableRooms = hotel.rooms.filter(r => r.available);

  const minPrice =
    availableRooms.length > 0
      ? Math.min(...availableRooms.map(r => r.costPerNight))
      : Math.min(...hotel.rooms.map(r => r.costPerNight));

  const rating = Number(hotel.stars);

  return (
    <article
      className="
        group relative overflow-hidden rounded-[28px]
        bg-white shadow-lg
        transition-all duration-500
        hover:-translate-y-2 hover:shadow-2xl
        border border-slate-100
      "
    >
      {/* IMAGE WRAPPER */}
      <div className="relative h-80 overflow-hidden">

        <img
          src="https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1400&q=80"
          alt={hotel.hotelName}
          className="
            h-full w-full object-cover
            transition duration-700
            group-hover:scale-110
          "
        />

        {/* overlays */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />

        {/* top badges */}
        <div className="absolute top-5 left-5 flex gap-2">

          {/* rating */}
          <div className="flex items-center gap-1 rounded-full bg-white/90 px-3 py-1 text-xs font-bold text-slate-900 backdrop-blur">
            ⭐ {rating.toFixed(1)}
          </div>

          {/* availability hint */}
          <div className="rounded-full bg-emerald-500/90 px-3 py-1 text-xs font-bold text-white backdrop-blur">
            {availableRooms.length} libres
          </div>

        </div>

        {/* location */}
        <div className="absolute bottom-6 left-6">
          <p className="text-xs uppercase tracking-[3px] text-blue-200">
            {hotel.location.country}
          </p>

          <h3 className="text-3xl font-black text-white leading-tight">
            {hotel.hotelName}
          </h3>

          <p className="text-sm text-white/80">
            📍 {hotel.location.city}
          </p>
        </div>

      </div>

      {/* BODY */}
      <div className="p-6 space-y-5">

        {/* mini room preview */}
        <div className="space-y-2">
          <p className="text-xs font-bold uppercase tracking-[3px] text-slate-400">
            Habitaciones
          </p>

          <div className="flex flex-wrap gap-2">
            {hotel.rooms.slice(0, 3).map(room => (
              <span
                key={room.id}
                className={`
                  rounded-full px-3 py-1 text-xs font-semibold
                  border transition
                  ${
                    room.available
                      ? "bg-emerald-50 text-emerald-700 border-emerald-100"
                      : "bg-rose-50 text-rose-600 border-rose-100"
                  }
                `}
              >
                {room.roomType}
              </span>
            ))}
          </div>
        </div>

        {/* price + CTA */}
        <div className="flex items-end justify-between pt-4 border-t border-slate-100">

          <div>
            <p className="text-[10px] uppercase tracking-[3px] text-slate-400">
              Desde
            </p>

            <div className="flex items-end gap-1">
              <span className="text-4xl font-black text-slate-900">
                ${minPrice}
              </span>

              <span className="mb-1 text-sm text-slate-500">
                / noche
              </span>
            </div>
          </div>

          <button
            className="
              relative overflow-hidden
              rounded-xl px-5 py-3
              bg-gradient-to-r from-blue-600 to-indigo-600
              text-white text-sm font-bold
              shadow-lg shadow-blue-600/20
              transition-all duration-300
              hover:scale-105 hover:shadow-blue-600/40
            "
          >
            Ver detalles
          </button>

        </div>

      </div>
    </article>
  );
};