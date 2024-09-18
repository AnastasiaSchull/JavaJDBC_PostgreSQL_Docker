package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Random;

public class DbWriter {
    public static void main(String[] args) {
        //настраиваем соединение
        String url = "jdbc:postgresql://localhost:5437/message-db";
        String user = "sa";
        String password = "admin";

        // sql для вставки данных
        String sql = "INSERT INTO messages (message, type, processed) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            while (true) {
                boolean isInfoType = new Random().nextBoolean();
                String message = (isInfoType ? "New message from " : "An error occurred in ") + LocalDateTime.now();
                String type = isInfoType ? "INFO" : "WARN";
                boolean processed = false; // изначально false, помечается как необработанная

                // PreparedStatement помогает предотвратить sql-инъекции
                // и упрощает задачу вставки параметров в запрос.
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, message);
                    stmt.setString(2, type);
                    stmt.setBoolean(3, processed);

                    stmt.executeUpdate();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
