package rs.oks.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("https://localhost:4200", "https://www.rhv.rs", "https://oks-api-production.up.railway.app", "https://accounts.google.com")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////                        .allowedHeaders("Content-Type", "Authorization")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("https://localhost:4200");
//        config.addAllowedOrigin("https://www.rhv.rs");
//        config.addAllowedOrigin("https://oks-api-production.up.railway.app");
//        config.addAllowedOrigin("https://accounts.google.com");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}

