package config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author wangfin
 * @Date 2024/4/28
 * @Desc
 */

@Configuration
@AutoConfigureAfter(value = {RabbitProducerDataSourceConfiguration.class})
public class RabbitProducerMyBatisConfiguration {

    @Resource(name = "rabbitProducerDataSource")
    private DataSource rabbitProducerDataSource;

    public RabbitProducerMyBatisConfiguration(DataSource rabbitProducerDataSource) {
        this.rabbitProducerDataSource = rabbitProducerDataSource;
    }

    @Bean(name = "rabbitProducerSqlSessionFactory")
    public SqlSessionFactory rabbitProducerSqlSessionFactory(DataSource rabbitProducerDataSource) {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(rabbitProducerDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mapping/*.xml"));
            SqlSessionFactory sqlSessionFactory = bean.getObject();
            sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
            return sqlSessionFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // 模板
    @Bean(name = "rabbitProducerSqlSessionTemplate")
    public SqlSessionTemplate rabbitProducerSqlSessionTemplate(SqlSessionFactory rabbitProducerSqlSessionFactory) {
        return new SqlSessionTemplate(rabbitProducerSqlSessionFactory);
    }

}
