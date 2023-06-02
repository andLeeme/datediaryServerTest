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


            String sql = "select email from googleemail where email =\"" + email + ";";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);


            if (rs.next()) {
                String selectEmail = rs.getString(1).toString();
                if (selectEmail == null) {
                    result = 1;

                } else if (selectEmail.equals(email)) {
                    result = 0;
                }
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

        return result;
    }

}
