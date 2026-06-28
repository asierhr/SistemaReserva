import React from "react";

interface Destination {
  id: number;
  city: string;
  country: string;
  image: string;
  hotels: number;
}

const DESTINATIONS: Destination[] = [
  {
    id: 1,
    city: "Cancún",
    country: "México",
    hotels: 124,
    image:
      "https://images.unsplash.com/photo-1552074284-5e88ef1aef18?auto=format&fit=crop&w=1200&q=80",
  },
  {
    id: 2,
    city: "París",
    country: "Francia",
    hotels: 89,
    image:
      "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?auto=format&fit=crop&w=1200&q=80",
  },
  {
    id: 3,
    city: "Tokio",
    country: "Japón",
    hotels: 102,
    image:
      "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=1200&q=80",
  },
  {
    id: 4,
    city: "Bali",
    country: "Indonesia",
    hotels: 73,
    image:
      "https://images.unsplash.com/photo-1537953773345-d172ccf13cf1?auto=format&fit=crop&w=1200&q=80",
  },
];

export const Destinations: React.FC = () => {
  return (
    <section className="bg-gradient-to-b from-slate-950 to-slate-900 py-28">
      <div className="mx-auto max-w-7xl px-6">

        {/* HEADER */}
        <div className="mb-16 text-center">
          <span className="rounded-full bg-white/10 px-5 py-2 text-xs font-bold uppercase tracking-[4px] text-white/70 border border-white/10">
            Destinos Populares
          </span>

          <h2 className="mt-6 text-5xl font-black text-white tracking-tight">
            Explora el mundo
          </h2>

          <p className="mx-auto mt-4 max-w-2xl text-lg text-white/60">
            Descubre destinos icónicos con experiencias premium y hoteles exclusivos.
          </p>
        </div>

        {/* GRID */}
        <div className="grid gap-8 md:grid-cols-2 xl:grid-cols-4">

          {DESTINATIONS.map((destination) => (
            <div
              key={destination.id}
              className="
                group relative h-[440px]
                overflow-hidden rounded-[32px]
                border border-white/10
                shadow-2xl shadow-black/40
                transition-all duration-500
                hover:-translate-y-2
              "
            >
              {/* IMAGE */}
              <img
                src={destination.image}
                alt={destination.city}
                className="
                  h-full w-full object-cover
                  transition duration-700
                  group-hover:scale-110
                "
              />

              {/* LAYERS */}
              <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent" />
              <div className="absolute inset-0 opacity-0 group-hover:opacity-100 transition bg-blue-500/10" />

              {/* BADGE */}
              <div className="absolute right-5 top-5 rounded-full bg-white/90 px-4 py-2 text-xs font-bold text-slate-900 backdrop-blur">
                {destination.hotels} hoteles
              </div>

              {/* CONTENT */}
              <div className="absolute bottom-0 w-full p-8">

                <p className="text-xs uppercase tracking-[4px] text-blue-200/80">
                  {destination.country}
                </p>

                <h3 className="mt-2 text-4xl font-black text-white">
                  {destination.city}
                </h3>

                <button
                  className="
                    mt-6
                    rounded-2xl
                    bg-white/10
                    px-5 py-3
                    text-sm font-bold text-white
                    border border-white/10
                    backdrop-blur
                    transition-all duration-300
                    hover:bg-white
                    hover:text-black
                    hover:scale-105
                  "
                >
                  Explorar →
                </button>

              </div>

            </div>
          ))}

        </div>

      </div>
    </section>
  );
};