package kg.ezshopping.ezshopping.initializer;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public class YamlTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String TEST_PROPERTIES_FILE_PATH = "classpath:test.yaml";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Resource resource = applicationContext.getResource(TEST_PROPERTIES_FILE_PATH);
        try {
            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
            List<PropertySource<?>> yamlTestPropertiesList = sourceLoader.load("yamlTestProperties", resource);
            for (PropertySource<?> propertySource : yamlTestPropertiesList) {
                applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
