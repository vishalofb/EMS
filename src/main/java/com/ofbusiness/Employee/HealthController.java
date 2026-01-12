package com.ofbusiness.Employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/db-check")
    public String checkDB() {
        try (Connection connection = dataSource.getConnection()) {
            return "✅ Database is UP";
        } catch (Exception e) {
            return "❌ Database is DOWN: " + e.getMessage();
        }
    }
}
