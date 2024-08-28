package com.karpo.quisy.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.karpo.quisy.repositories"})
@EnableTransactionManagement
public class MySQLConfiguration {
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/quisy?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("1234");
        hikariConfig.setMaximumPoolSize(3);

        return new HikariDataSource(hikariConfig);
    }
}
