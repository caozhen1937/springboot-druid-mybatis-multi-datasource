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
@EnableConfigurationProperties(DruidSlaveProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@MapperScan(basePackages = DruidSlaveConfiguration.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class DruidSlaveConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(com.example.demo.druid.DruidSlaveConfiguration.class);

    static final String SLAVE_LOCATION = "classpath:mapper/slave/*.xml";
    static final String PACKAGE = "com.yongche.bigdata.platform.mapper.slave";

    @Autowired
    private DruidSlaveProperties slaveProperties;


    @Bean(name = "dataSourceSlave")
    public DataSource dataSourceSlave() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slaveProperties.getUrl());
        dataSource.setUsername(slaveProperties.getUsername());
        dataSource.setPassword(slaveProperties.getPassword());
        if (slaveProperties.getInitialSize() > 0) {
            dataSource.setInitialSize(slaveProperties.getInitialSize());
            logger.info("setInitialSize --->" + slaveProperties.getInitialSize());
        }
        if (slaveProperties.getMinIdle() > 0) {
            dataSource.setMinIdle(slaveProperties.getMinIdle());
            logger.info("setInitialSize --->" + slaveProperties.getInitialSize());
        }
        if (slaveProperties.getMaxActive() > 0) {
            dataSource.setMaxActive(slaveProperties.getMaxActive());
            logger.info("setMaxActive --->" + slaveProperties.getMaxActive());
        }
        if (slaveProperties.getTestOnBorrow() != null) {
            dataSource.setTestOnBorrow(slaveProperties.getTestOnBorrow());
            logger.info("setTestOnBorrow --->" + slaveProperties.getTestOnBorrow());
        }
        if (slaveProperties.getMaxWait() > 0) {
            dataSource.setMaxWait(slaveProperties.getMaxWait());
            logger.info("setMaxWait --->" + slaveProperties.getMaxWait());
        }
        if (slaveProperties.getTimeBetweenEvictionRunsMillis() > 0) {
            dataSource.setTimeBetweenEvictionRunsMillis(slaveProperties.getTimeBetweenEvictionRunsMillis());
            logger.info("setTimeBetweenEvictionRunsMillis --->" + slaveProperties.getTimeBetweenEvictionRunsMillis());
        }
        if (slaveProperties.getMinEvictableIdleTimeMillis() > 0) {
            dataSource.setMinEvictableIdleTimeMillis(slaveProperties.getMinEvictableIdleTimeMillis());
            logger.info("setMinEvictableIdleTimeMillis --->" + slaveProperties.getMinEvictableIdleTimeMillis());
        }
        if (slaveProperties.getValidationQuery() != null) {
            dataSource.setValidationQuery(slaveProperties.getValidationQuery());
            logger.info("setValidationQuery --->" + slaveProperties.getValidationQuery());
        }
        if (slaveProperties.getTestWhileIdle() != null) {
            dataSource.setTestWhileIdle(slaveProperties.getTestWhileIdle());
            logger.info("setTestWhileIdle --->" + slaveProperties.getTestWhileIdle());
        }
        if (slaveProperties.getTestOnReturn() != null) {
            dataSource.setTestOnReturn(slaveProperties.getTestOnReturn());
            logger.info("setTestOnReturn --->" + slaveProperties.getTestOnReturn());
        }
        if (slaveProperties.getPoolPreparedStatements() != null) {
            dataSource.setPoolPreparedStatements(slaveProperties.getPoolPreparedStatements());
            logger.info("setPoolPreparedStatements --->" + slaveProperties.getPoolPreparedStatements());
        }
        if (slaveProperties.getMaxPoolPreparedStatementPerConnectionSize() > 0) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(slaveProperties.getMaxPoolPreparedStatementPerConnectionSize());
            logger.info("setMaxPoolPreparedStatementPerConnectionSize --->" + slaveProperties.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (slaveProperties.getFilters() != null) {
            try {
                dataSource.setFilters(slaveProperties.getFilters());
                logger.info("setFilters --->" + slaveProperties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("setInitialSize error");
            }
        }
        if (slaveProperties.getConnectionProperties() != null) {
            dataSource.setConnectionProperties(slaveProperties.getConnectionProperties());
            logger.info("setConnectionProperties --->" + slaveProperties.getConnectionProperties());
        }

        try {

            dataSource.init();
            logger.info("dataSourceSlave.init()");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager slaveTransactionManager() {
        return new DataSourceTransactionManager(dataSourceSlave());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("dataSourceSlave") DataSource dataSourceSlave)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceSlave);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DruidSlaveConfiguration.SLAVE_LOCATION));
        return sessionFactory.getObject();
    }


}
