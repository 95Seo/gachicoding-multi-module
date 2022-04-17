package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.deco.gachicoding.api.notice")
@ComponentScan(basePackages = "org.deco.gachicoding.presentation.notice")
public class WebNoticeModuleConfig {
}