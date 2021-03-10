package com.revature.gameStart.util;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://java-enterprise-21012019.cql84dkg1tu3.us-east-2.rds.amazonaws.com:5432/GameStart");
        dataSourceBuilder.username(System.getenv("db.username"));
        dataSourceBuilder.password(System.getenv("db.password"));
        return dataSourceBuilder.build();
    }
}
