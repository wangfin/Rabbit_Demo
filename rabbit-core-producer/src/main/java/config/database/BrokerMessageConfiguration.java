package config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * @Author wangfin
 * @Date 2024/4/28
 * @Desc 帮助数据库表结构的创建 执行SQL脚本
 */

@Configuration
public class BrokerMessageConfiguration {

    @Autowired
    private DataSource rabbitProducerDataSource;

    @Value("classpath:rabbit-producer-message-schema.sql")
    private Resource schemaScript;

    @Bean
    public DataSourceInitializer initDataSourceInitializer() {
        System.err.println("----------rabbitProducerDataSource-----------");
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }

}
