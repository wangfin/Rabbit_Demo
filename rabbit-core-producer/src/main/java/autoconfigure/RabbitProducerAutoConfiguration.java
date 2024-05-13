package autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wangfin
 * @Date 2024/2/28
 * @Desc 自动装配
 */
@Configuration
@ComponentScan(basePackages = {"broker", "mapper", "config.*"})
public class RabbitProducerAutoConfiguration {
}
