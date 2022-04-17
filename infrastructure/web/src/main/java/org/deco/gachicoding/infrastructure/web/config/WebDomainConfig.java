package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.deco.gachicoding.core.common.repository")
@EntityScan(basePackages = "org.deco.gachicoding.core.common.domain")
public class WebDomainConfig {
}
