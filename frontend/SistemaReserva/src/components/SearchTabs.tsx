import React, { useState } from 'react';

interface SearchTabsProps {
  onSearchFlights?: (data: { origin: string; destination: string; date: string }) => void;
  onSearchHotels?: (data: { destination: string; guests: number }) => void;
}

export const SearchTabs: React.FC<SearchTabsProps> = ({ onSearchFlights, onSearchHotels }) => {
  const [activeTab, setActiveTab] = useState<'vuelos' | 'hoteles'>('vuelos');
  const [flightOrigin, setFlightOrigin] = useState('');
  const [flightDest, setFlightDest] = useState('');
  const [flightDate, setFlightDate] = useState('');
  const [hotelDest, setHotelDest] = useState('');
  const [hotelGuests, setHotelGuests] = useState(1);

  return (
    <div className="w-full max-w-5xl mx-auto bg-white/95 backdrop-blur-md rounded-3xl shadow-2xl shadow-slate-900/20 border border-white/20 p-2 overflow-hidden text-slate-800">
      
      {/* Selector de Pestañas Estilo Pastilla (Pill Tabs) */}
      <div className="flex gap-2 bg-slate-100 p-1.5 rounded-2xl w-fit mx-6 mt-6">
        <button
          type="button"
          onClick={() => setActiveTab('vuelos')}
          className={`px-6 py-2.5 rounded-xl font-bold text-xs tracking-wide uppercase transition-all duration-200 flex items-center gap-2 ${
            activeTab === 'vuelos'
              ? 'bg-white text-blue-600 shadow-sm'
              : 'text-slate-500 hover:text-slate-800'
          }`}
        >
          ✈️ Vuelos
        </button>
        <button
          type="button"
          onClick={() => setActiveTab('hoteles')}
          className={`px-6 py-2.5 rounded-xl font-bold text-xs tracking-wide uppercase transition-all duration-200 flex items-center gap-2 ${
            activeTab === 'hoteles'
              ? 'bg-white text-blue-600 shadow-sm'
              : 'text-slate-500 hover:text-slate-800'
          }`}
        >
          🏨 Hoteles
        </button>
      </div>

      {/* Contenedor del Formulario */}
      <div className="p-6 pt-4">
        {activeTab === 'vuelos' && (
          <form onSubmit={(e) => { e.preventDefault(); onSearchFlights?.({ origin: flightOrigin, destination: flightDest, date: flightDate }); }} 
                className="grid grid-cols-1 lg:grid-cols-12 gap-4 items-center">
            
            <div className="lg:col-span-4 bg-slate-50 border border-slate-200/60 rounded-2xl p-3 hover:bg-slate-100/50 transition">
              <label className="block text-[10px] font-extrabold uppercase tracking-widest text-slate-400 mb-1">Origen</label>
              <input
                type="text"
                placeholder="¿Desde dónde despegas?"
                value={flightOrigin}
                onChange={(e) => setFlightOrigin(e.target.value)}
                className="w-full bg-transparent font-medium text-slate-800 placeholder-slate-400 focus:outline-none text-sm"
                required
              />
            </div>

            <div className="lg:col-span-4 bg-slate-50 border border-slate-200/60 rounded-2xl p-3 hover:bg-slate-100/50 transition">
              <label className="block text-[10px] font-extrabold uppercase tracking-widest text-slate-400 mb-1">Destino</label>
              <input
                type="text"
                placeholder="¿A dónde quieres aterrizar?"
                value={flightDest}
                onChange={(e) => setFlightDest(e.target.value)}
                className="w-full bg-transparent font-medium text-slate-800 placeholder-slate-400 focus:outline-none text-sm"
                required
              />
            </div>

            <div className="lg:col-span-2 bg-slate-50 border border-slate-200/60 rounded-2xl p-3 hover:bg-slate-100/50 transition">
              <label className="block text-[10px] font-extrabold uppercase tracking-widest text-slate-400 mb-1">Fecha Ida</label>
              <input
                type="date"
                value={flightDate}
                onChange={(e) => setFlightDate(e.target.value)}
                className="w-full bg-transparent font-medium text-slate-800 focus:outline-none text-sm"
                required
              />
            </div>

            <button type="submit" className="lg:col-span-2 w-full bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-bold text-sm h-[58px] rounded-2xl transition shadow-lg shadow-blue-600/20 active:scale-[0.98]">
              Buscar
            </button>
          </form>
        )}

        {activeTab === 'hoteles' && (
          <form onSubmit={(e) => { e.preventDefault(); onSearchHotels?.({ destination: hotelDest, guests: hotelGuests }); }} 
                className="grid grid-cols-1 lg:grid-cols-12 gap-4 items-center">
            
            <div className="lg:col-span-6 bg-slate-50 border border-slate-200/60 rounded-2xl p-3 hover:bg-slate-100/50 transition">
              <label className="block text-[10px] font-extrabold uppercase tracking-widest text-slate-400 mb-1">Destino u Hotel</label>
              <input
                type="text"
                placeholder="Ciudad, región o alojamiento específico"
                value={hotelDest}
                onChange={(e) => setHotelDest(e.target.value)}
                className="w-full bg-transparent font-medium text-slate-800 placeholder-slate-400 focus:outline-none text-sm"
                required
              />
            </div>

            <div className="lg:col-span-4 bg-slate-50 border border-slate-200/60 rounded-2xl p-3 hover:bg-slate-100/50 transition">
              <label className="block text-[10px] font-extrabold uppercase tracking-widest text-slate-400 mb-1">Huéspedes</label>
              <select
                value={hotelGuests}
                onChange={(e) => setHotelGuests(Number(e.target.value))}
                className="w-full bg-transparent font-medium text-slate-800 focus:outline-none text-sm cursor-pointer"
              >
                <option value={1}>1 Viajero solo</option>
                <option value={2}>2 Personas (Pareja)</option>
                <option value={3}>3 Personas</option>
                <option value={4}>4 Personas</option>
              </select>
            </div>

            <button type="submit" className="lg:col-span-2 w-full bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-bold text-sm h-[58px] rounded-2xl transition shadow-lg shadow-blue-600/20 active:scale-[0.98]">
              Explorar
            </button>
          </form>
        )}
      </div>
    </div>
  );
};