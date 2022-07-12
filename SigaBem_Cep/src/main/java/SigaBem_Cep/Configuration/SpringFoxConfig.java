package com.example.demo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sigabem.teste.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.pathMapping("/");
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Siga Bem",
				"Spring Boot REST API - Transportadora SigaBem: Cálculo do preço do frete",
				"1.0.0v", null,
				new Contact("Brayan Marcelo", "https://github.com/Brayinha/JavaTest",
						"marcelobrayan.ls@gmail.com"),
				"Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}
	

}
