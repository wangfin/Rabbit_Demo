package config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author wangfin
 * @Date 2024/4/23
 * @Desc 数据源配置
 */
@Configuration
@PropertySource("classpath:rabbit-producer-message.properties")
public class RabbitProducerDataSourceConfiguration {
}
