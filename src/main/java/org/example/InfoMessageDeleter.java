package org.example;
import java.sql.*;

public class InfoMessageDeleter {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5437/message-db";
        String user = "sa";
        String password = "admin";
        String selectQuery = "SELECT id, message FROM messages WHERE type = 'INFO' AND processed = false";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                System.out.println("Processing INFO message: " + message);
                // создаем новый Statement для выполнения запроса на удаление
                try (Statement deleteStmt = conn.createStatement()) {
                    String deleteQuery = "DELETE FROM messages WHERE id = " + id; //запрос на удаление
                    deleteStmt.executeUpdate(deleteQuery); // выполнение запроса
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}


