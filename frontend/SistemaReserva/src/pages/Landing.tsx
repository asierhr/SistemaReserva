import React from 'react';
import { SearchTabs } from '../components/SearchTabs.ts';
import { type Hotel } from '../types/hotel/hotel.ts'; // Tu interfaz vinculada a Java

// Datos simulados (Mock Data) basados en tus entidades reales
const MOCK_HOTELS: Hotel[] = [
  {
    id: 1,
    hotelName: "Grand Plaza Resort",
    stars: "5",
    location: {city: "Cancún", country: "México", countryCode: "43820", latitude: 43.20, longitude: 46.1, region:"Guatemala"},
    rooms: [
      { id: 101, hotelId: 1, numRoom: "101-A", roomType: "DOUBLE", costPerNight: 250, available: true },
      { id: 102, hotelId: 1, numRoom: "102-B", roomType: "SUITE", costPerNight: 450, available: false }
    ]
  },
  {
    id: 2,
    hotelName: "Tokyo Shinjuku Stay",
    stars: "4",
    location: {city: "Cancún", country: "México", countryCode: "43820", latitude: 43.20, longitude: 46.1, region:"Guatemala"},
    rooms: [
      { id: 201, hotelId: 2, numRoom: "402", roomType: "TRIPLE", costPerNight: 120, available: true }
    ]
  }
];

export const LandingPage: React.FC = () => {
  
  // Funciones controladoras para cuando el usuario use el componente SearchTabs
  const handleFlightSearch = (data: { origin: string; destination: string; date: string }) => {
    console.log("Enviando petición de vuelos al backend de Java:", data);
    alert(`Buscando vuelos desde ${data.origin} hacia ${data.destination}...`);
  };

  const handleHotelSearch = (data: { destination: string; guests: number }) => {
    console.log("Filtrando hoteles para el backend:", data);
    alert(`Buscando hoteles en ${data.destination} para ${data.guests} personas...`);
  };

  // Función auxiliar para calcular el precio mínimo por noche de un hotel
  const getMinPrice = (hotel: Hotel): number => {
    if (!hotel.rooms || hotel.rooms.length === 0) return 0;
    const available = hotel.rooms.filter(r => r.available);
    const targetRooms = available.length > 0 ? available : hotel.rooms;
    return Math.min(...targetRooms.map(r => r.costPerNight));
  };

  return (
    <div className="min-h-screen bg-slate-50 font-sans text-slate-800 antialiased">
      
      {/* 1. BARRA DE NAVEGACIÓN */}
      <nav className="sticky top-0 z-50 bg-white/90 backdrop-blur-md border-b border-slate-100 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2 font-black text-2xl text-blue-600 tracking-tight">
            <span className="text-3xl">✈️</span> Fly&Stay
          </div>
          <div className="hidden md:flex items-center gap-8 font-semibold text-sm text-slate-600">
            <a href="#explorar" className="hover:text-blue-600 transition">Explorar Hoteles</a>
            <a href="#beneficios" className="hover:text-blue-600 transition">Beneficios</a>
            <a href="#app" className="hover:text-blue-600 transition">Descargar App</a>
          </div>
          <button className="bg-slate-900 hover:bg-slate-800 text-white font-bold text-sm px-5 py-2.5 rounded-xl transition shadow-sm">
            Mi Cuenta
          </button>
        </div>
      </nav>

      {/* 2. HERO SECTION (Contiene tu componente reutilizable) */}
      <header className="relative bg-gradient-to-b from-blue-700 via-blue-800 to-indigo-950 text-white pt-20 pb-32 px-4 overflow-hidden">
        {/* Decoración geométrica de fondo */}
        <div className="absolute inset-0 opacity-10 bg-[radial-gradient(#fff_1px,transparent_1px)] [background-size:16px_16px]"></div>
        
        <div className="relative max-w-4xl mx-auto text-center space-y-6 z-10">
          <span className="bg-blue-500/30 text-blue-200 text-xs font-bold px-3 py-1.5 rounded-full uppercase tracking-widest">
            Todo en un solo lugar
          </span>
          <h1 className="text-4xl md:text-6xl font-black tracking-tight leading-none">
            Vuelos baratos y <br />
            <span className="text-blue-300">hoteles de ensueño</span>
          </h1>
          <p className="text-base md:text-lg text-blue-100/90 max-w-xl mx-auto font-medium">
            Planifica, reserva y gestiona tu itinerario completo en tiempo real con nuestra plataforma integrada.
          </p>

          {/* 🌟 AQUÍ ENTRA TU COMPONENTE REUTILIZABLE 🌟 */}
          <div className="pt-6 animate-fade-in-up">
            <SearchTabs 
              onSearchFlights={handleFlightSearch}
              onSearchHotels={handleHotelSearch}
            />
          </div>
        </div>
      </header>

      {/* 3. SECCIÓN DINÁMICA DE HOTELES (Usando tus interfaces) */}
      <section id="explorar" className="py-20 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 -mt-16 relative z-20">
        <div className="bg-white rounded-3xl p-8 md:p-12 shadow-xl border border-slate-100/80">
          <div className="mb-10 flex flex-col md:flex-row md:items-end justify-between gap-4">
            <div>
              <h2 className="text-2xl md:text-3xl font-extrabold text-slate-900 tracking-tight">Recomendados para ti</h2>
              <p className="text-slate-500 text-sm mt-1">Sincronizado directamente con las habitaciones de nuestro sistema.</p>
            </div>
            <button className="text-sm font-bold text-blue-600 hover:text-blue-700 transition flex items-center gap-1">
              Ver todos los hoteles <span>→</span>
            </button>
          </div>

          <div className="grid sm:grid-cols-2 lg:grid-cols-2 gap-8">
            {MOCK_HOTELS.map((hotel) => {
              const minPrice = getMinPrice(hotel);
              return (
                <div key={hotel.id} className="bg-slate-50/50 rounded-2xl border border-slate-100 p-6 flex flex-col justify-between hover:border-slate-200 hover:bg-slate-50 transition group">
                  <div className="space-y-4">
                    <div className="flex justify-between items-start">
                      <div>
                        <span className="text-[11px] font-bold uppercase tracking-wider text-blue-600 bg-blue-50 px-2 py-1 rounded">
                          📍 {hotel.location.city}, {hotel.location.country}
                        </span>
                        <h3 className="text-xl font-bold text-slate-900 mt-2 group-hover:text-blue-600 transition">
                          {hotel.hotelName}
                        </h3>
                      </div>
                      <span className="bg-amber-50 text-amber-700 px-2 py-1 rounded-lg text-xs font-black flex items-center gap-0.5 border border-amber-100 shadow-sm">
                        ⭐ {hotel.stars}
                      </span>
                    </div>

                    {/* Desglose resumido de habitaciones */}
                    <div className="bg-white rounded-xl p-4 border border-slate-100 space-y-2">
                      <p className="text-[10px] font-bold text-slate-400 uppercase tracking-wider">Estado de Habitaciones</p>
                      {hotel.rooms.map((room) => (
                        <div key={room.id} className="flex justify-between items-center text-xs">
                          <span className="font-medium text-slate-600">
                            Nº {room.numRoom} <span className="text-slate-400 font-normal">({room.roomType})</span>
                          </span>
                          <span className={`font-semibold px-2 py-0.5 rounded-full text-[10px] ${
                            room.available ? 'bg-emerald-50 text-emerald-700 border border-emerald-100' : 'bg-rose-50 text-rose-600 border border-rose-100'
                          }`}>
                            {room.available ? 'Disponible' : 'Ocupada'}
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>

                  {/* Precio y Acción */}
                  <div className="mt-6 pt-4 border-t border-slate-100 flex items-center justify-between">
                    <div>
                      <span className="text-[11px] text-slate-400 block uppercase font-bold tracking-wider">Desde</span>
                      <p className="text-2xl font-black text-slate-900">
                        ${minPrice.toFixed(2)}
                        <span className="text-xs font-medium text-slate-400">/noche</span>
                      </p>
                    </div>
                    <button className="bg-blue-600 hover:bg-blue-700 text-white text-xs font-bold px-4 py-2.5 rounded-xl transition shadow-md shadow-blue-100">
                      Ver Disponibilidad
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </section>

      {/* 4. BENEFICIOS */}
      <section id="beneficios" className="py-12 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center grid md:grid-cols-3 gap-8 border-t border-slate-200/60">
        <div className="p-4 space-y-2">
          <div className="text-3xl">🛡️</div>
          <h4 className="font-bold text-base text-slate-900">Cancelación Flexible</h4>
          <p className="text-xs text-slate-500 leading-relaxed">Modifica las fechas de tus vuelos o habitaciones sin penalizaciones extras.</p>
        </div>
        <div className="p-4 space-y-2">
          <div className="text-3xl">⚡</div>
          <h4 className="font-bold text-base text-slate-900">Confirmación Inmediata</h4>
          <p className="text-xs text-slate-500 leading-relaxed">Sin esperas. Tus DTOs viajan al servidor de Java y aseguran tu cupo al instante.</p>
        </div>
        <div className="p-4 space-y-2">
          <div className="text-3xl">💬</div>
          <h4 className="font-bold text-base text-slate-900">Soporte 24/7</h4>
          <p className="text-xs text-slate-500 leading-relaxed">Estamos listos para ayudarte en cualquier huso horario durante tu viaje.</p>
        </div>
      </section>

      {/* 5. FOOTER */}
      <footer className="bg-white border-t border-slate-100 py-6 text-center text-xs text-slate-400 font-medium">
        <p>© 2026 Fly&Stay Inc. Conectado con Spring Boot Services.</p>
      </footer>

    </div>
  );
};