package com.asier.SistemaReservas.system.JWT.repository;

import com.asier.SistemaReservas.system.JWT.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM tokens t WHERE t.user.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllInvalidOrRevokedTokensByUserId(@Param("id") Long id);

    Token findByToken(String token);
}
