package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = "com.excilys")


public class SpringAnnotationWebInitializer extends AbstractContextLoaderInitializer {
	
  @Override
  protected WebApplicationContext createRootApplicationContext() {
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
    applicationContext.register(SpringAnnotationWebInitializer.class);
    return applicationContext;
  }
}


