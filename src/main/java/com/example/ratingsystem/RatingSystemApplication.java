package com.example.ratingsystem;

import com.example.ratingsystem.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class RatingSystemApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DataSource ds = context.getBean(DataSource.class);

        try (Connection conn = ds.getConnection()) {
            System.out.println("Connected to the database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.close();
    }
}



