package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ScheduleDeleteRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.HashMap;

@RestController
public class SearchEmailController {


    @PostMapping("/api/searchEmail")
    public int searchEmail(@RequestBody Object _email) throws SQLException {


        System.out.println(_email);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String email = _email.toString();


            String sql = "select email, coupleemail, coupleindex from googleemail where email ='" + email + "';";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);


            //기 존 회 원 0 신 규 회 원 1 미 연 동 회 원 2
            if (rs.next()) {

                String responseCode = rs.getString(3);


                if (responseCode == null) {
                    result = 2;

                } else {
                    result = 0;
                }

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

    @PostMapping("/api/coupleEmail")
    public int coupleEmail(@RequestBody HashMap<String, String> _tmp) throws SQLException {

        System.out.println(_tmp.get("email"));


        System.out.println(_tmp.get("coupleEmail"));

        String email = _tmp.get("email").toString();

        String coupleEmail = _tmp.get("coupleEmail").toString().trim();

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "UPDATE googleemail SET coupleemail =\"" + coupleEmail + "\" WHERE email = \"" + email + "\";";

            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            result = 0;

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

    @PostMapping("/api/matchCouple")
    public int matchCouple(@RequestBody Object _email) throws SQLException {


        System.out.println(_email);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            int maxIndex= 0;

            String email = _email.toString();

            String sql = "select email, coupleindex from googleemail where coupleemail ='" + email + "';";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);



            if (rs.next()) {

                String responseIndex = rs.getString(2);

                if (responseIndex == null) {

                    sql = "SELECT MAX(coupleindex) FROM googleemail;";

                     stmt = conn.createStatement();

                     rs = stmt.executeQuery(sql);

                    if(rs.next()) {
                        maxIndex = rs.getInt(1);
                    }

                    int finalIndex = maxIndex + 1;

                    sql = "UPDATE googleemail SET coupleindex =\"" + finalIndex + "\" WHERE email = \"" + email + "\";";

                } else {

                    sql = "UPDATE googleemail SET coupleindex =\"" + responseIndex + "\" WHERE email = \"" + email + "\";";

                }
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            } else {

                result = 99;
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
