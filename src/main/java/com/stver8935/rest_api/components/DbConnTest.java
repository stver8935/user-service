package com.stver8935.rest_api.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.components
 * @fileName : PostGresConnTest
 * @since : 24. 8. 9.
 */
@Component
public class DbConnTest implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DbConnTest.class);
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public void run(ApplicationArguments args) {
        try {
            Connection connection = dataSource.getConnection();
            System.out.println( connection.getMetaData().getClass() );
            System.out.println( connection.getMetaData().getDriverName() );
            System.out.println( connection.getMetaData().getURL() );
            System.out.println( connection.getMetaData().getUserName() );


            Statement statement = connection.createStatement();
            String alias = "result";
            String sql = "SELECT 1 as "+alias;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int result = resultSet.getInt(alias);
                log.info(" TEST : "+result);
            }

            //String sql = "CREATE TABLE ACCOUNT(ID INTEGER NOT NULL, NAME VARCHAR(255), PRIMARY KEY(ID))";
            //statement.executeUpdate( sql );

            //jdbcTemplate.execute( "INSERT INTO ACCOUNT VALUES (1, 'hermeswing')" );
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
