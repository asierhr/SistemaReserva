import React from "react";

import { Navbar } from "../components/layout/Navbar";
import { Hero } from "../components/hero/Hero";
import { Destinations } from "../components/destination/Destination";
import { HotelGrid } from "../components/hotel/HotelGrid";
import { Footer } from "../components/layout/Footer";

import { type Hotel } from "../types/hotel/hotel";

// Datos simulados
const MOCK_HOTELS: Hotel[] = [
  {
    id: 1,
    hotelName: "Grand Plaza Resort",
    stars: "5",
    location: {
      city: "Cancún",
      country: "México",
      countryCode: "43820",
      latitude: 43.2,
      longitude: 46.1,
      region: "Guatemala",
    },
    rooms: [
      {
        id: 101,
        hotelId: 1,
        numRoom: "101-A",
        roomType: "DOUBLE",
        costPerNight: 250,
        available: true,
      },
      {
        id: 102,
        hotelId: 1,
        numRoom: "102-B",
        roomType: "SUITE",
        costPerNight: 450,
        available: false,
      },
    ],
  },
  {
    id: 2,
    hotelName: "Tokyo Shinjuku Stay",
    stars: "4",
    location: {
      city: "Tokio",
      country: "Japón",
      countryCode: "100-0001",
      latitude: 35.6762,
      longitude: 139.6503,
      region: "Kanto",
    },
    rooms: [
      {
        id: 201,
        hotelId: 2,
        numRoom: "402",
        roomType: "TRIPLE",
        costPerNight: 120,
        available: true,
      },
      {
        id: 202,
        hotelId: 2,
        numRoom: "403",
        roomType: "SUITE",
        costPerNight: 280,
        available: true,
      },
    ],
  },
  {
    id: 3,
    hotelName: "Paris Luxury Palace",
    stars: "5",
    location: {
      city: "París",
      country: "Francia",
      countryCode: "75000",
      latitude: 48.8566,
      longitude: 2.3522,
      region: "Île-de-France",
    },
    rooms: [
      {
        id: 301,
        hotelId: 3,
        numRoom: "701",
        roomType: "SUITE",
        costPerNight: 520,
        available: true,
      },
      {
        id: 302,
        hotelId: 3,
        numRoom: "702",
        roomType: "DOUBLE",
        costPerNight: 310,
        available: true,
      },
    ],
  },
];

export const LandingPage: React.FC = () => {
  const handleLoginClick = () => {
    alert("Redirigiendo al inicio de sesión...");
  };

  return (
    <div className="min-h-screen bg-slate-950 text-white">

      <Navbar onLoginClick={handleLoginClick} />

      <Hero />

      <div className="py-10" />

      <Destinations />

      <div className="py-16" />

      <HotelGrid hotels={MOCK_HOTELS} />

      <div className="py-20" />

      <Footer />

    </div>
  );
};

export default LandingPage;