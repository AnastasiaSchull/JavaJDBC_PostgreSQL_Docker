package org.example;
import java.sql.*;

public class InfoMessageDeleter {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5437/message-db";
        String user = "sa";
        String password = "admin";
        String selectQuery = "SELECT id, message FROM messages WHERE type = 'INFO' AND processed = false";
        String deleteQuery = "DELETE FROM messages WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                System.out.println("Processing INFO message: " + message);

                try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}


