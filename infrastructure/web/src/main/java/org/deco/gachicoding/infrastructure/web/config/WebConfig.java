package org.deco.gachicoding.infrastructure.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     *  swagger 설정 <br>
     *  .apis(RequestHandlerSelectors.any()) -> 할당된 모든 URL 리스트를 추출 <br>
     *  .apis(RequestHandlerSelectors.basePackage("")) -> 지정된 패키지만 API 화 <br>
     *  .paths(PathSelectors.ant("/api/**")) -> 그 중 /api/** 인 URL들만 필터링 <br>
     *  .paths(PathSelectors.any()) -> 모든 URL 적용
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    /**
     * swagger-ui 설정 포함
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * pageable 사용을 위한 {@link PageableHandlerMethodArgumentResolver} 추가
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
