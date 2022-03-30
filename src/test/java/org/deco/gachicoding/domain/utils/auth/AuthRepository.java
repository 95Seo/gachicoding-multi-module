package org.deco.gachicoding.domain.utils.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {
    // 이 쿼리 용도를 모르겠음..
//    Optional<Auth> findByTokenAndExpirationDateAfterAndExpired(String confirmationTokenId, LocalDateTime now, boolean expired);
    Optional<Auth> findByAuthEmail(String authEmail);
}

