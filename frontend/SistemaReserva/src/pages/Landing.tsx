import React from 'react';
import { SearchTabs } from '../components/SearchTabs.tsx';
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
  
  // Función para manejar el inicio de sesión (aquí conectarás Spring Security/OAuth en el futuro)
  const handleLoginClick = () => {
    alert("Redirigiendo al formulario de inicio de sesión...");
  };

  const getMinPrice = (hotel: Hotel): number => {
    if (!hotel.rooms || hotel.rooms.length === 0) return 0;
    const available = hotel.rooms.filter(r => r.available);
    return Math.min(...(available.length > 0 ? available : hotel.rooms).map(r => r.costPerNight));
  };

  return (
    <div className="min-h-screen bg-slate-50/60 font-sans text-slate-900 antialiased selection:bg-blue-500 selection:text-white">
      
      {/* 1. NAV PREMIUM */}
      <nav className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-slate-100">
        <div className="max-w-7xl mx-auto px-6 lg:px-8 h-20 flex items-center justify-between">

          {/* 🌟 BOTÓN DE INICIAR SESIÓN INTEGRADO 🌟 */}
          <button 
            onClick={handleLoginClick}
            className="group relative inline-flex items-center justify-center p-0.5 overflow-hidden text-xs font-bold uppercase tracking-wider text-slate-900 rounded-xl hover:text-white transition duration-200"
          >
            <span className="absolute inset-0 w-full h-full bg-gradient-to-br from-blue-600 to-indigo-600 group-hover:opacity-100 opacity-0 transition-opacity duration-300 rounded-xl"></span>
            <span className="relative px-5 py-2.5 transition-all ease-in duration-200 bg-white rounded-[10px] group-hover:bg-opacity-0 w-full border border-slate-200 group-hover:border-transparent">
              Iniciar Sesión
            </span>
          </button>
        </div>
      </nav>

      {/* 2. HERO SECTION */}
      <header className="relative bg-gradient-to-tr from-slate-950 via-indigo-950 to-slate-900 text-white pt-24 pb-40 px-6 overflow-hidden">
        <div className="absolute top-[-20%] left-[-10%] w-[600px] h-[600px] rounded-full bg-blue-500/10 blur-[150px]"></div>
        <div className="absolute bottom-[-20%] right-[-10%] w-[500px] h-[500px] rounded-full bg-indigo-500/10 blur-[130px]"></div>

        <div className="relative max-w-4xl mx-auto text-center space-y-8 z-10">
          <h1 className="text-4xl sm:text-6xl font-black tracking-tight leading-[1.1] max-w-3xl mx-auto">
            Descubre lugares que <br />
            <span className="bg-gradient-to-r from-blue-300 via-indigo-200 to-white bg-clip-text text-transparent">merecen ser vividos</span>
          </h1>
          <p className="text-sm sm:text-base text-slate-300 max-w-xl mx-auto font-medium leading-relaxed">
            Una curaduría estricta de vuelos globales y estancias boutique conectada directamente con nuestro core de reservas.
          </p>

          <div className="pt-6">
            <SearchTabs 
              onSearchFlights={(data) => console.log("Vuelos:", data)}
              onSearchHotels={(data) => console.log("Hoteles:", data)}
            />
          </div>
        </div>
      </header>

      {/* 3. GRID DE HOTELES */}
      <section id="destinos" className="py-24 max-w-7xl mx-auto px-6 lg:px-8 -mt-20 relative z-20">
        <div className="mb-14 flex flex-col sm:flex-row sm:items-end justify-between gap-4">
          <div>
            <h2 className="text-2xl sm:text-3xl font-black text-slate-900 tracking-tight">Estancias Destacadas</h2>
            <p className="text-slate-400 text-sm mt-1.5 font-medium">Habitaciones premium verificadas disponibles en tiempo real.</p>
          </div>
          <button className="text-xs font-bold uppercase tracking-wider text-blue-600 hover:text-blue-700 transition flex items-center gap-2 group">
            Ver portafolio completo <span className="group-hover:translate-x-1 transition-transform">→</span>
          </button>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-10">
          {MOCK_HOTELS.map((hotel) => {
            const minPrice = getMinPrice(hotel);
            return (
              <div key={hotel.id} className="bg-white rounded-3xl p-8 shadow-md hover:shadow-xl transition-all duration-300 border border-slate-100 flex flex-col justify-between group">
                <div className="space-y-6">
                  <div className="flex justify-between items-start gap-4">
                    <div>
                      <div className="flex items-center gap-2 text-slate-400 text-xs font-bold tracking-wider uppercase">
                        <span>📍</span> {hotel.location.city}, {hotel.location.country}
                      </div>
                      <h3 className="text-2xl font-black text-slate-900 mt-2 tracking-tight group-hover:text-blue-600 transition-colors">
                        {hotel.hotelName}
                      </h3>
                    </div>
                    <div className="bg-amber-500 text-white font-extrabold px-2.5 py-1 rounded-xl text-xs flex items-center gap-1 shadow-sm shadow-amber-500/10">
                      ★ {hotel.stars}.0
                    </div>
                  </div>

                  <div className="bg-slate-50/70 border border-slate-100 rounded-2xl p-5 space-y-3">
                    <h4 className="text-[10px] font-black uppercase tracking-widest text-slate-400">Disponibilidad interna</h4>
                    <div className="divide-y divide-slate-200/60">
                      {hotel.rooms.map((room) => (
                        <div key={room.id} className="flex justify-between items-center py-2.5 first:pt-0 last:pb-0 text-sm">
                          <div>
                            <span className="font-bold text-slate-800">Room {room.numRoom}</span>
                            <span className="mx-2 text-slate-300">|</span>
                            <span className="text-xs text-slate-500 font-medium">{room.roomType}</span>
                          </div>
                          <span className={`text-[10px] font-extrabold tracking-wider uppercase px-2.5 py-1 rounded-lg border ${
                            room.available 
                              ? 'bg-emerald-50 text-emerald-700 border-emerald-100' 
                              : 'bg-rose-50 text-rose-600 border-rose-100'
                          }`}>
                            {room.available ? 'Libre' : 'Reservada'}
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>
                </div>

                <div className="mt-8 pt-6 border-t border-slate-100 flex items-center justify-between">
                  <div>
                    <span className="text-[10px] font-extrabold uppercase tracking-widest text-slate-400 block">Tarifa base</span>
                    <div className="flex items-baseline gap-1">
                      <span className="text-3xl font-black text-slate-950">${minPrice}</span>
                      <span className="text-xs font-semibold text-slate-400">/ noche</span>
                    </div>
                  </div>
                  <button className="bg-slate-900 hover:bg-blue-600 hover:shadow-lg hover:shadow-blue-600/10 text-white text-xs font-bold uppercase tracking-wider px-6 py-3.5 rounded-xl transition-all duration-200">
                    Gestionar Reserva
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      </section>

      <footer className="bg-white border-t border-slate-100 py-10 text-center text-xs font-semibold text-slate-400 tracking-wide">
        <p>© 2026 Nomadic Architecture. Integración limpia con Spring Boot REST API.</p>
      </footer>

    </div>
  );
};