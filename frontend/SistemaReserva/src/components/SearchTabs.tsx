import React, { useState } from "react";

interface SearchTabsProps {
  onSearchFlights?: (data: {
    origin: string;
    destination: string;
    date: string;
  }) => void;
  onSearchHotels?: (data: { destination: string; guests: number }) => void;
}

export const SearchTabs: React.FC<SearchTabsProps> = ({
  onSearchFlights,
  onSearchHotels,
}) => {
  const [activeTab, setActiveTab] = useState<"vuelos" | "hoteles">("vuelos");

  const [flightOrigin, setFlightOrigin] = useState("");
  const [flightDest, setFlightDest] = useState("");
  const [flightDate, setFlightDate] = useState("");

  const [hotelDest, setHotelDest] = useState("");
  const [hotelGuests, setHotelGuests] = useState(2);

  return (
    <div
      className="
        w-full max-w-5xl mx-auto
        rounded-[32px]
        bg-white/5
        backdrop-blur-2xl
        border border-white/10
        shadow-2xl shadow-black/40
        overflow-hidden
      "
    >

      {/* TABS */}
      <div className="flex justify-center pt-6">
        <div className="flex gap-2 rounded-full bg-white/10 p-2 border border-white/10">

          <button
            onClick={() => setActiveTab("vuelos")}
            className={`
              px-6 py-2 rounded-full text-xs font-bold uppercase tracking-[3px]
              transition
              ${
                activeTab === "vuelos"
                  ? "bg-white text-slate-900 shadow"
                  : "text-white/70 hover:text-white"
              }
            `}
          >
            ✈️ Vuelos
          </button>

          <button
            onClick={() => setActiveTab("hoteles")}
            className={`
              px-6 py-2 rounded-full text-xs font-bold uppercase tracking-[3px]
              transition
              ${
                activeTab === "hoteles"
                  ? "bg-white text-slate-900 shadow"
                  : "text-white/70 hover:text-white"
              }
            `}
          >
            🏨 Hoteles
          </button>

        </div>
      </div>

      {/* CONTENT */}
      <div className="p-6 pt-8">

        {/* ===== VUELOS ===== */}
        {activeTab === "vuelos" && (
          <form
            onSubmit={(e) => {
              e.preventDefault();
              onSearchFlights?.({
                origin: flightOrigin,
                destination: flightDest,
                date: flightDate,
              });
            }}
            className="grid grid-cols-1 md:grid-cols-12 gap-3"
          >

            {/* ORIGEN */}
            <div className="md:col-span-4 px-4 py-3 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/10 transition">
              <label className="text-[10px] uppercase tracking-[3px] text-white/50">
                Origen
              </label>
              <input
                value={flightOrigin}
                onChange={(e) => setFlightOrigin(e.target.value)}
                placeholder="Madrid..."
                className="w-full bg-transparent text-white outline-none mt-1"
              />
            </div>

            {/* DESTINO */}
            <div className="md:col-span-4 px-4 py-3 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/10 transition">
              <label className="text-[10px] uppercase tracking-[3px] text-white/50">
                Destino
              </label>
              <input
                value={flightDest}
                onChange={(e) => setFlightDest(e.target.value)}
                placeholder="París..."
                className="w-full bg-transparent text-white outline-none mt-1"
              />
            </div>

            {/* FECHA */}
            <div className="md:col-span-2 px-4 py-3 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/10 transition">
              <label className="text-[10px] uppercase tracking-[3px] text-white/50">
                Fecha
              </label>
              <input
                type="date"
                value={flightDate}
                onChange={(e) => setFlightDate(e.target.value)}
                className="w-full bg-transparent text-white outline-none mt-1"
              />
            </div>

            {/* BOTÓN */}
            <button
              type="submit"
              className="
                md:col-span-2
                rounded-2xl
                bg-gradient-to-r from-blue-600 to-indigo-600
                text-white font-bold text-sm
                shadow-lg shadow-blue-600/20
                transition
                hover:scale-[1.03]
                active:scale-[0.98]
              "
            >
              Buscar
            </button>
          </form>
        )}

        {/* ===== HOTELES ===== */}
        {activeTab === "hoteles" && (
          <form
            onSubmit={(e) => {
              e.preventDefault();
              onSearchHotels?.({
                destination: hotelDest,
                guests: hotelGuests,
              });
            }}
            className="grid grid-cols-1 md:grid-cols-12 gap-3"
          >

            {/* DESTINO */}
            <div className="md:col-span-6 px-4 py-3 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/10 transition">
              <label className="text-[10px] uppercase tracking-[3px] text-white/50">
                Destino
              </label>
              <input
                value={hotelDest}
                onChange={(e) => setHotelDest(e.target.value)}
                placeholder="Ciudad o hotel"
                className="w-full bg-transparent text-white outline-none mt-1"
              />
            </div>

            {/* HUÉSPEDES */}
            <div className="md:col-span-4 px-4 py-3 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/10 transition">
              <label className="text-[10px] uppercase tracking-[3px] text-white/50">
                Huéspedes
              </label>
              <select
                value={hotelGuests}
                onChange={(e) => setHotelGuests(Number(e.target.value))}
                className="w-full bg-transparent text-white outline-none mt-1"
              >
                <option value={1}>1</option>
                <option value={2}>2</option>
                <option value={3}>3</option>
                <option value={4}>4</option>
              </select>
            </div>

            {/* BOTÓN */}
            <button
              type="submit"
              className="
                md:col-span-2
                rounded-2xl
                bg-gradient-to-r from-blue-600 to-indigo-600
                text-white font-bold text-sm
                shadow-lg shadow-blue-600/20
                transition
                hover:scale-[1.03]
                active:scale-[0.98]
              "
            >
              Explorar
            </button>

          </form>
        )}

      </div>
    </div>
  );
};