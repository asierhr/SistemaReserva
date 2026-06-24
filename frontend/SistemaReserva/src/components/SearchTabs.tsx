import React, { useState } from 'react';

interface SearchTabsProps {
  onSearchFlights?: (data: { origin: string; destination: string; date: string }) => void;
  onSearchHotels?: (data: { destination: string; guests: number }) => void;
}

export const SearchTabs: React.FC<SearchTabsProps> = ({ 
  onSearchFlights, 
  onSearchHotels 
}) => {
  // Estado para controlar la pestaña activa ('vuelos' por defecto al cargar)
  const [activeTab, setActiveTab] = useState<'vuelos' | 'hoteles'>('vuelos');

  // Estados independientes para cada formulario
  const [flightOrigin, setFlightOrigin] = useState('');
  const [flightDest, setFlightDest] = useState('');
  const [flightDate, setFlightDate] = useState('');

  const [hotelDest, setHotelDest] = useState('');
  const [hotelGuests, setHotelGuests] = useState(1);

  const handleFlightSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (onSearchFlights) {
      onSearchFlights({ origin: flightOrigin, destination: flightDest, date: flightDate });
    } else {
      alert(`Buscando vuelos de ${flightOrigin} a ${flightDest} para el ${flightDate}`);
    }
  };

  const handleHotelSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (onSearchHotels) {
      onSearchHotels({ destination: hotelDest, guests: hotelGuests });
    } else {
      alert(`Buscando hoteles en ${hotelDest} para ${hotelGuests} personas`);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto bg-white rounded-2xl shadow-xl border border-slate-100 text-slate-800 overflow-hidden">
      
      {/* HEADER DE LAS SECCIONES (TABS) */}
      <div className="flex border-b border-slate-100 bg-slate-50/50">
        <button
          type="button"
          onClick={() => setActiveTab('vuelos')}
          className={`flex-1 py-4 text-center font-semibold text-sm transition-all flex items-center justify-center gap-2 border-b-2 ${
            activeTab === 'vuelos'
              ? 'border-blue-600 text-blue-600 bg-white'
              : 'border-transparent text-slate-500 hover:text-slate-800 hover:bg-slate-50'
          }`}
        >
          <span>✈️</span> Vuelos
        </button>
        <button
          type="button"
          onClick={() => setActiveTab('hoteles')}
          className={`flex-1 py-4 text-center font-semibold text-sm transition-all flex items-center justify-center gap-2 border-b-2 ${
            activeTab === 'hoteles'
              ? 'border-blue-600 text-blue-600 bg-white'
              : 'border-transparent text-slate-500 hover:text-slate-800 hover:bg-slate-50'
          }`}
        >
          <span>🏨</span> Hoteles
        </button>
      </div>

      {/* CONTENIDO DINÁMICO SEGÚN LA SECCIÓN SELECCIONADA */}
      <div className="p-6">
        
        {/* Formulario de Vuelos (Se ve por defecto) */}
        {activeTab === 'vuelos' && (
          <form onSubmit={handleFlightSubmit} className="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
            <div className="space-y-1">
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400">Origen</label>
              <div className="relative">
                <span className="absolute left-3 top-3 text-slate-400">🛫</span>
                <input
                  type="text"
                  placeholder="¿Desde dónde viajas?"
                  value={flightOrigin}
                  onChange={(e) => setFlightOrigin(e.target.value)}
                  className="w-full pl-9 pr-3 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-sm bg-slate-50"
                  required
                />
              </div>
            </div>

            <div className="space-y-1">
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400">Destino</label>
              <div className="relative">
                <span className="absolute left-3 top-3 text-slate-400">🛬</span>
                <input
                  type="text"
                  placeholder="¿A dónde vas?"
                  value={flightDest}
                  onChange={(e) => setFlightDest(e.target.value)}
                  className="w-full pl-9 pr-3 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-sm bg-slate-50"
                  required
                />
              </div>
            </div>

            <div className="space-y-1">
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400">Fecha de Ida</label>
              <input
                type="date"
                value={flightDate}
                onChange={(e) => setFlightDate(e.target.value)}
                className="w-full px-3 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-sm bg-slate-50"
                required
              />
            </div>

            <button
              type="submit"
              className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-xl transition text-sm shadow-md shadow-blue-100 h-[46px]"
            >
              Buscar Vuelos
            </button>
          </form>
        )}

        {/* Formulario de Hoteles */}
        {activeTab === 'hoteles' && (
          <form onSubmit={handleHotelSubmit} className="grid grid-cols-1 md:grid-cols-3 gap-4 items-end">
            <div className="space-y-1 md:col-span-1">
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400">Destino del Hotel</label>
              <div className="relative">
                <span className="absolute left-3 top-3 text-slate-400">📍</span>
                <input
                  type="text"
                  placeholder="Ciudad, zona o nombre del hotel"
                  value={hotelDest}
                  onChange={(e) => setHotelDest(e.target.value)}
                  className="w-full pl-9 pr-3 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-sm bg-slate-50"
                  required
                />
              </div>
            </div>

            <div className="space-y-1">
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400">Huéspedes</label>
              <select
                value={hotelGuests}
                onChange={(e) => setHotelGuests(Number(e.target.value))}
                className="w-full px-3 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-sm bg-slate-50"
              >
                <option value={1}>1 Adulto</option>
                <option value={2}>2 Adultos</option>
                <option value={3}>3 Adultos</option>
                <option value={4}>4 Adultos</option>
              </select>
            </div>

            <button
              type="submit"
              className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-xl transition text-sm shadow-md shadow-blue-100 h-[46px]"
            >
              Buscar Hoteles
            </button>
          </form>
        )}

      </div>
    </div>
  );
};