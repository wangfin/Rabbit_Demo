package config.database;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author wangfin
 * @Date 2024/4/23
 * @Desc 数据源配置
 */
@Configuration
@PropertySource("classpath:rabbit-producer-message.properties")
public class RabbitProducerDataSourceConfiguration {
    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitProducerDataSourceConfiguration.class);

    @Value("${rabbit.producer.druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "rabbitProducerDataSource")
    @Primary
    @ConfigurationProperties(prefix = "rabbit.producer.druid.jdbc")
    public DataSource rabbitProducerDataSource() throws SQLException {
        LOGGER.info("rabbitProducerDataSource init");
        DataSource rabbitProducerDataSource = DataSourceBuilder.create().type(dataSourceType).build();

        return rabbitProducerDataSource;
    }

    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    public DataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
