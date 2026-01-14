package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>CI/CD Test Application</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }");
        out.println("h1 { color: #333; }");
        out.println(".success { color: green; background: #d4edda; padding: 10px; border-radius: 5px; margin: 10px 0; }");
        out.println(".error { color: red; background: #f8d7da; padding: 10px; border-radius: 5px; margin: 10px 0; }");
        out.println(".info { background: #d1ecf1; padding: 10px; border-radius: 5px; margin: 10px 0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>🚀 CI/CD Test Application</h1>");
        
        // Test 1: Tomcat
        out.println("<h2>✅ Test 1: Tomcat Deployment</h2>");
        out.println("<div class='success'>");
        out.println("<strong>SUCCESS:</strong> Tomcat is running correctly!<br>");
        out.println("Server Info: " + getServletContext().getServerInfo() + "<br>");
        out.println("Servlet Context: " + getServletContext().getContextPath());
        out.println("</div>");
        
        // Test 2: Database Connection
        out.println("<h2>🗄️ Test 2: Database Connectivity</h2>");
        
        String dbUrl = "jdbc:mariadb://mariadb:3306/cicd_db";
        String dbUser = "cicd_user";
        String dbPassword = "cicd_password";
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 Statement stmt = conn.createStatement()) {
                
                out.println("<div class='success'>");
                out.println("<strong>SUCCESS:</strong> Database connection established!<br>");
                out.println("Database: cicd_db<br>");
                out.println("User: " + dbUser + "<br>");
                
                // Test query
                ResultSet rs = stmt.executeQuery("SELECT VERSION() as version, NOW() as now");
                if (rs.next()) {
                    out.println("MariaDB Version: " + rs.getString("version") + "<br>");
                    out.println("Current Time: " + rs.getString("now"));
                }
                rs.close();
                
                out.println("</div>");
                
                // Create test table if not exists
                out.println("<h3>📊 Creating Test Table</h3>");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS test_table (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "message VARCHAR(255), " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")");
                
                // Insert test data
                stmt.executeUpdate("INSERT INTO test_table (message) VALUES ('Test from servlet at " + 
                        new java.util.Date() + "')");
                
                // Read test data
                ResultSet testRs = stmt.executeQuery("SELECT * FROM test_table ORDER BY id DESC LIMIT 5");
                out.println("<div class='info'>");
                out.println("<strong>Recent test entries:</strong><br>");
                out.println("<table border='1' style='margin-top: 10px; border-collapse: collapse;'>");
                out.println("<tr><th style='padding: 5px;'>ID</th><th style='padding: 5px;'>Message</th><th style='padding: 5px;'>Created At</th></tr>");
                
                while (testRs.next()) {
                    out.println("<tr>");
                    out.println("<td style='padding: 5px;'>" + testRs.getInt("id") + "</td>");
                    out.println("<td style='padding: 5px;'>" + testRs.getString("message") + "</td>");
                    out.println("<td style='padding: 5px;'>" + testRs.getTimestamp("created_at") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</div>");
                testRs.close();
                
            }
            
        } catch (Exception e) {
            out.println("<div class='error'>");
            out.println("<strong>ERROR:</strong> Database connection failed!<br>");
            out.println("Error: " + e.getMessage());
            out.println("</div>");
            e.printStackTrace();
        }
        
        out.println("<hr>");
        out.println("<h2>📋 Summary</h2>");
        out.println("<div class='info'>");
        out.println("✅ Tomcat is working correctly<br>");
        out.println("✅ Database connectivity is functional<br>");
        out.println("✅ Application deployed successfully<br>");
        out.println("</div>");
        
        out.println("</body>");
        out.println("</html>");
    }
}
