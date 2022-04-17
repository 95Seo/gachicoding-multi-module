package org.deco.gachicoding.core.common.repository;

import org.deco.gachicoding.core.common.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {
//    Optional<Auth> findByTokenAndExpirationDateAfterAndExpired(String confirmationTokenId, LocalDateTime now, boolean expired);
    Optional<Auth> findByAuthEmail(String authEmail);
}
