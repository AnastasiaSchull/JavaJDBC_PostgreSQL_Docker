package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Random;

public class DbWriter {
    public static void main(String[] args) {
        //настраиваем соединение
        String url = "jdbc:postgresql://localhost:5437/message-db";
        String user = "sa";
        String password = "admin";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            while (true) {
                boolean isInfoType = new Random().nextBoolean();
                String message = (isInfoType ? "New message from " : "An error occurred in ") + LocalDateTime.now();
                String type = isInfoType ? "INFO" : "WARN";
                boolean processed = false; // изначально false, помечается как необработанная

                // создание запроса
                String sql = String.format("INSERT INTO messages (message, type, processed) VALUES ('%s', '%s', %b)",
                        message,
                        type,
                        processed);
                // объект Statement используется для выполнения sqlзапросов
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
