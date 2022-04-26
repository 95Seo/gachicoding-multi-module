package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.deco.gachicoding.api.question")
@ComponentScan(basePackages = "org.deco.gachicoding.presentation.question")
public class WebQuestionModuleConfig {
}