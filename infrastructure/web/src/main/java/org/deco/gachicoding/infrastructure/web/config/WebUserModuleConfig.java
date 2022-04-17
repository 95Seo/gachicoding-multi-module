package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.deco.gachicoding.api.user")
@ComponentScan(basePackages = "org.deco.gachicoding.presentation.user")
public class WebUserModuleConfig {
}