package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class SearchEmailController {


    @PostMapping("/api/searchEmail")
    public int searchEmail(@RequestBody String _email) throws SQLException {


        System.out.println(_email);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String email = _email;


            String sql = "select email from googleemail where email = 'rarara773@gmail.com';";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println(rs.next());
            if (rs.next()) {
                result = 0;
            } else {
                result = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }

        }

        System.out.println(result);

        return result;
    }

}
