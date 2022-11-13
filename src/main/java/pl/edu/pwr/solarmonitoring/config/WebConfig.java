package pl.edu.pwr.solarmonitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${cross.origin1}")
    private String crossOrigin1;

    @Value("${cross.origin2}")
    private String crossOrigin2;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(crossOrigin1, crossOrigin2)
                .allowedMethods("PUT", "DELETE", "GET", "PATCH", "POST")
                .allowedHeaders("Content_Type", "Authorization")
                .allowCredentials(false).maxAge(3600);
    }
}
