package app.commerceio.spring.data.search.jpa.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {
    private boolean dataInit = true;
    private long dataSize = 10000;

}
