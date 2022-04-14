package org.deco.gachicoding.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social, Long> {
    Optional<Social> findBySocialTypeAndSocialId(String socialType, String socialId);
}
