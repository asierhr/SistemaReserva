import { type Location } from "../location/location.ts";
import { type Rooms } from "../rooms/rooms.ts";

export interface Hotel {
    id?: number;
    hotelName: string;
    location: Location;
    stars: string;
    rooms: Rooms[];
}