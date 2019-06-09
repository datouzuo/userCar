package xin.mengzuo.user.car.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfigurer  implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
         .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                 "Access-Control-Request-Headers")
         .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
         .allowCredentials(true).maxAge(3600);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
	registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
		WebMvcConfigurer.super.addFormatters(registry);
	}

  
}
