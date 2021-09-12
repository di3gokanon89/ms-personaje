/**
 * 
 */
package com.devpredator.mspersonaje.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configure the swagger library for the rest controllers's api's.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 02/08/2021
 */
@Configuration
public class SpringFoxConfig {

	/**
	 * Defines the api to register in the spring context the controllers to document.
	 * 
	 * @return {@link Docket} object to configure the description of the endpoints.
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiInfo())
				.securityContexts(Arrays.asList(this.securityContext()))
				.securitySchemes(Arrays.asList(this.apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.devpredator.mspersonaje.controllers"))
				.paths(PathSelectors.any())
				.build();
	}
	
	/**
	 * Defines the metadata of the anime's microservice
	 * 
	 * @return {@link ApiInfo} object with the anime's metadata.
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("DevPredator - Personaje's API Documentation")
				.description("Defines all the endpoints of the personaje's microservice")
				.termsOfServiceUrl("https://facebook.com/devpredator")
				.contact(new Contact("Diego Paniagua López", "", "diego.paniagua.kanon89@gmail.com"))
				.license("Licencia de DevPredator").version("v1.0")
				.build();
	}
	/**
	 * Defines the api key for the endpoint of the microservice
	 * @return {@link ApiKey} SHA key
	 */
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}
	
	/**
	 * Load the security context in swagger.
	 * @return {@link SecurityContext} security context with the default authorization scope for the enpoints.
	 */
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.defaultAuth()).build();
	}
	
	/**
	 * Build the authorization scope for the microservices's enpoint.
	 * @return {@link List} get the list of security references with the authorization's scope.
	 */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}
