package com.asier.SistemaReservas.room.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum RoomType {
    INDIVIDUAL(1),
    DOUBLE(2),
    TRIPLE(3),
    SUITE(2),
    PRESIDENTIAL_SUITE(4);

    private final int capacity;

    RoomType(int capacity) {
        this.capacity = capacity;
    }

    public static List<RoomType> fromCapacity(int guests) {
        return Arrays.stream(RoomType.values())
                .filter(rt -> rt.getCapacity() >= guests)
                .toList();
    }
}
