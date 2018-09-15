package com.example.demo.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid自动配置
 *
 * @author kg.
 * @create 2017-04-05
 */

@Configuration
@ConditionalOnClass(DruidDataSource.class)
@EnableConfigurationProperties(DruidMasterProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@MapperScan(basePackages = DruidMasterConfiguration.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DruidMasterConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(com.example.demo.druid.DruidMasterConfiguration.class);

    static final String PACKAGE = "com.yongche.bigdata.platform.mapper.master";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Autowired
    private DruidMasterProperties masterProperties;

    @Bean(name = "dataSourceMaster")
    @Primary
    public DataSource dataSourceMaster() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(masterProperties.getUrl());
        dataSource.setUsername(masterProperties.getUsername());
        dataSource.setPassword(masterProperties.getPassword());
        if (masterProperties.getInitialSize() > 0) {
            dataSource.setInitialSize(masterProperties.getInitialSize());
            logger.info("setInitialSize --->" + masterProperties.getInitialSize());
        }
        if (masterProperties.getMinIdle() > 0) {
            dataSource.setMinIdle(masterProperties.getMinIdle());
            logger.info("setInitialSize --->" + masterProperties.getInitialSize());
        }
        if (masterProperties.getMaxActive() > 0) {
            dataSource.setMaxActive(masterProperties.getMaxActive());
            logger.info("setMaxActive --->" + masterProperties.getMaxActive());
        }
        if (masterProperties.getTestOnBorrow() != null) {
            dataSource.setTestOnBorrow(masterProperties.getTestOnBorrow());
            logger.info("setTestOnBorrow --->" + masterProperties.getTestOnBorrow());
        }
        if (masterProperties.getMaxWait() > 0) {
            dataSource.setMaxWait(masterProperties.getMaxWait());
            logger.info("setMaxWait --->" + masterProperties.getMaxWait());
        }
        if (masterProperties.getTimeBetweenEvictionRunsMillis() > 0) {
            dataSource.setTimeBetweenEvictionRunsMillis(masterProperties.getTimeBetweenEvictionRunsMillis());
            logger.info("setTimeBetweenEvictionRunsMillis --->" + masterProperties.getTimeBetweenEvictionRunsMillis());
        }
        if (masterProperties.getMinEvictableIdleTimeMillis() > 0) {
            dataSource.setMinEvictableIdleTimeMillis(masterProperties.getMinEvictableIdleTimeMillis());
            logger.info("setMinEvictableIdleTimeMillis --->" + masterProperties.getMinEvictableIdleTimeMillis());
        }
        if (masterProperties.getValidationQuery() != null) {
            dataSource.setValidationQuery(masterProperties.getValidationQuery());
            logger.info("setValidationQuery --->" + masterProperties.getValidationQuery());
        }
        if (masterProperties.getTestWhileIdle() != null) {
            dataSource.setTestWhileIdle(masterProperties.getTestWhileIdle());
            logger.info("setTestWhileIdle --->" + masterProperties.getTestWhileIdle());
        }
        if (masterProperties.getTestOnReturn() != null) {
            dataSource.setTestOnReturn(masterProperties.getTestOnReturn());
            logger.info("setTestOnReturn --->" + masterProperties.getTestOnReturn());
        }
        if (masterProperties.getPoolPreparedStatements() != null) {
            dataSource.setPoolPreparedStatements(masterProperties.getPoolPreparedStatements());
            logger.info("setPoolPreparedStatements --->" + masterProperties.getPoolPreparedStatements());
        }
        if (masterProperties.getMaxPoolPreparedStatementPerConnectionSize() > 0) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(masterProperties.getMaxPoolPreparedStatementPerConnectionSize());
            logger.info("setMaxPoolPreparedStatementPerConnectionSize --->" + masterProperties.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (masterProperties.getFilters() != null) {
            try {
                dataSource.setFilters(masterProperties.getFilters());
                logger.info("setFilters --->" + masterProperties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("setInitialSize error");
            }
        }
        if (masterProperties.getConnectionProperties() != null) {
            dataSource.setConnectionProperties(masterProperties.getConnectionProperties());
            logger.info("setConnectionProperties --->" + masterProperties.getConnectionProperties());
        }

        try {
            dataSource.init();
            logger.info("dataSourceMaster.init()");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }


    @Bean
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSourceMaster());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSourceMaster") DataSource dataSourceMaster)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceMaster);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DruidMasterConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    
}
