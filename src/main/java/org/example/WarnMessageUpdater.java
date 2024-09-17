package org.example;
import java.sql.*;

public class WarnMessageUpdater {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5437/message-db";
        String user = "sa";
        String password = "admin";
        String selectQuery = "SELECT id, message FROM messages WHERE type = 'WARN' AND processed = false";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                System.out.println("Processing WARN message: " + message);
                //новый Statement для запроса на обновление
                try (Statement updateStmt = conn.createStatement()) {
                    String updateQuery = "UPDATE messages SET processed = true WHERE id = " + id;
                    updateStmt.executeUpdate(updateQuery);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
