package com.teleport.candidate_assessment.config;

import static com.teleport.candidate_assessment.utils.ConfigConstant.SWAGGER_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.ConfigConstant.SWAGGER_TITLE;
import static com.teleport.candidate_assessment.utils.ConfigConstant.SWAGGER_VERSION;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The type Swagger config. */
@Configuration
public class SwaggerConfig {

  /**
   * Custom open api open api.
   *
   * @return the open api
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title(SWAGGER_TITLE)
                .version(SWAGGER_VERSION)
                .description(SWAGGER_DESCRIPTION));
  }
}
