package org.deco.gachicoding.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAuthRepository extends JpaRepository<Social, Long> {
    public Optional<Social> findByTypeAndSocialId(String type, String socialId);
}
