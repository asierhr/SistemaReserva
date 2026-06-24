import { type RoomType } from "./roomType";
export interface Rooms {
    id?: number;
    hotelId: number;
    numRoom: string;
    roomType: RoomType;
    costPerNight: number;
    available: boolean;
}