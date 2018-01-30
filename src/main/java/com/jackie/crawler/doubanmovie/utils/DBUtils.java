package com.jackie.crawler.doubanmovie.utils;

import com.jackie.crawler.doubanmovie.constants.Constants;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Jackie on 2016/9/24 0024.
 *
 * @author Administrator
 */
public class DBUtils {

    public static final Logger log = LoggerFactory.getLogger(DBUtils.class);

    public static java.sql.Connection conn = null;

    public static Connection connectDB() throws Exception {
        try {
            ApplicationContext act = new ClassPathXmlApplicationContext("beans.xml");
            BasicDataSource dataSource = (BasicDataSource)act.getBean("dataSource");
            Class.forName("com.mysql.jdbc.Driver");
            String dburl = dataSource.getUrl();
            conn = DriverManager.getConnection(dburl, dataSource.getUsername(), dataSource.getPassword());
            log.info("connection built");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    public static void createTables() {
        if (conn != null) {
            //create database and table that will be needed
            try {
                String sql = Constants.CREATE_DATABASE_SQL;
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                sql = Constants.USE_DATABASE_SQL;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                //create table record
                sql = Constants.CREATE_RECORD_TABLE_SQL;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                //create table movie
                sql = Constants.CREATE_MOVIE_TABLE_SQL;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                //create table comments
                sql = Constants.CREATE_COMMENTS_TABLE_SQL;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
    }
}
