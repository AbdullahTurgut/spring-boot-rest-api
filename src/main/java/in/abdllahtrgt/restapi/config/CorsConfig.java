package in.abdllahtrgt.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuration class for handling Cross-Origin Resource Sharing (CORS).
 * <p>
 * This configuration allows the frontend application running on {@code http://localhost:5173}
 * to access backend resources by permitting all HTTP methods and headers.
 * </p>
 *
 * @author Abdullah
 * @since 1.0
 */
@Configuration
public class CorsConfig {


    /**
     * Creates and registers a {@link CorsFilter} bean that enables CORS support
     * for requests coming from the frontend domain.
     *
     * @return a configured {@code CorsFilter} instance
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("http://localhost:5173")); // Vite kullanıyorsan bu doğru
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
