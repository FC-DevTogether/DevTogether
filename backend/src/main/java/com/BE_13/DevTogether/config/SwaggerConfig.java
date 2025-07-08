package com.BE_13.DevTogether.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openAPI() {
        Info info = new Info()
                .title("DevTogether")
                .version("1.0")
                .description("코드 리뷰를 하는 수강생 전용 플랫폼입니다.");

        return new OpenAPI();
    }
}
