import React from "react";

const stats = [
  { number: "1.200+", label: "Hoteles Premium" },
  { number: "85+", label: "Países" },
  { number: "50K+", label: "Reservas" },
  { number: "24/7", label: "Soporte" },
];

export const HeroStats: React.FC = () => {
  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-6">

      {stats.map((stat) => (
        <div
          key={stat.label}
          className="
            group relative
            rounded-3xl
            bg-white/5
            border border-white/10
            backdrop-blur-xl
            p-6
            transition-all duration-300
            hover:bg-white/10
            hover:-translate-y-1
            hover:border-white/20
            hover:shadow-xl
            hover:shadow-black/30
          "
        >
          {/* glow decorativo */}
          <div className="absolute inset-0 rounded-3xl bg-gradient-to-br from-blue-500/0 via-blue-500/0 to-indigo-500/0 group-hover:from-blue-500/10 group-hover:to-indigo-500/10 transition-all duration-500" />

          <div className="relative">
            <h2 className="text-4xl md:text-5xl font-black text-white tracking-tight">
              {stat.number}
            </h2>

            <p className="mt-2 text-sm font-medium text-slate-300 tracking-wide">
              {stat.label}
            </p>
          </div>
        </div>
      ))}

    </div>
  );
};