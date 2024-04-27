package com.hodubam.sinder.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DatabaseConfig {
    @Autowired private ApplicationContext applicationContext;
    @Value("{jdbc.driver.info}") String[] driverInfo;
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverInfo[0]);
        dataSource.setJdbcUrl(driverInfo[1]);
        dataSource.setUsername(driverInfo[2]);
        dataSource.setPassword(driverInfo[3]);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mybatis/mapper/*.xml"));
//        sessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactory.getObject().getConfiguration().setCacheEnabled(false);
        sessionFactory.getObject().getConfiguration().setUseGeneratedKeys(false);
        sessionFactory.getObject().getConfiguration().setDefaultExecutorType(ExecutorType.REUSE);
        sessionFactory.getObject().getConfiguration().setCallSettersOnNulls(true);
        sessionFactory.getObject().getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }


}
