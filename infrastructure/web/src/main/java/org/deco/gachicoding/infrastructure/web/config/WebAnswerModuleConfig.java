package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.deco.gachicoding.api.answer")
@ComponentScan(basePackages = "org.deco.gachicoding.presentation.answer")
public class WebAnswerModuleConfig {
}