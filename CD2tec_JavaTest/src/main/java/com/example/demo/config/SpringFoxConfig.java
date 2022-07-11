package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();    
        
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Spring Boot REST API - Cálculo do preço do frete")
	            .description("mplementar para empresa de transporte de cargas SigaBem o endpoint para o cálculo do preço do frete:\r\n"
	            		+ "\r\n"
	            		+ "Você deve calcular o valor total do frete e a data prevista da entrega.\r\n"
	            		+ "\r\n"
	            		+ "Considerar regras para calcular o valor do frete:\r\n"
	            		+ "\r\n"
	            		+ "CEPs com DDDs iguais tem 50% de desconto no valor do frete e entrega prevista de 1 dia\r\n"
	            		+ "CEPs de estados iguais tem 75% de desconto no valor do frete e entrega prevista de 3 dias\r\n"
	            		+ "CEPs de estados diferentes não deve ser aplicado o desconto no valor do frete e entrega prevista de 10 dias\r\n"
	            		+ "O valor do frete é cobrado pelo peso da encomenda, o valor para cada KG é R$1,00\r\n"
	            		+ "Seu input de entrada deve ser “peso”, “cepOrigem”, “cepDestino” e “nomeDestinatario“")
	            .version("1.0.0")
	            .license("Apache License Version 2.0")
	            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	            .contact(new Contact("Brayan Marcelo", "https://github.com/Brayinha/JavaTest", "marcelobrayan.ls@gmail.com"))
	            .build();
	}
}
