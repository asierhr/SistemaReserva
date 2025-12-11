package com.asier.SistemaReservas.search.domain;

import com.asier.SistemaReservas.system.IpLocation.domain.Location;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SearchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    private Location location;
}
