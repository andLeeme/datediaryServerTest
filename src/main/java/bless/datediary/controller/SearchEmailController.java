package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ScheduleDeleteRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


                sql = "INSERT googleemail (email) VALUES ('" + email + "');";

                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
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

        System.out.println(coupleEmail);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "select count(coupleemail) from googleemail where coupleemail = '" + coupleEmail + "';";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);


            if (rs.next()) {

                String count = rs.getString(1).toString();

                if (!count.equals("0")) {

                    result = -1;


                } else {

                    sql = "UPDATE googleemail SET coupleemail =\"" + coupleEmail + "\" WHERE email = \"" + email + "\";";

                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();
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
        System.out.println(result);
        return result;
    }

    @PostMapping("/api/matchCouple")
    public int matchCouple(@RequestBody HashMap<String, String> data) throws SQLException {


        System.out.println(data);

        String _email = data.get("email");

        String _name = data.get("name");

        String _year = data.get("Year");

        String _month = data.get("Month");

        String _day = data.get("day");

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();


            int maxIndex = 0;

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

                    if (rs.next()) {
                        maxIndex = rs.getInt(1);
                    }

                    int finalIndex = maxIndex + 1;

                    sql = "UPDATE googleemail SET coupleindex =\"" + finalIndex + "\" WHERE email = \"" + email + "\";";

                } else {

                    sql = "UPDATE googleemail SET coupleindex =\"" + responseIndex + "\" WHERE email = \"" + email + "\";";

                }
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();


                sql = "UPDATE googleemail SET nickname =\"" + _name + "\" WHERE email = \"" + email + "\";";

                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();

                sql = "UPDATE googleemail SET year =\"" + _year + "\" WHERE email = \"" + email + "\";";

                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();

                sql = "UPDATE googleemail SET month =\"" + _month + "\" WHERE email = \"" + email + "\";";

                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();

                sql = "UPDATE googleemail SET day =\"" + _day + "\" WHERE email = \"" + email + "\";";

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

    @PostMapping("/api/coupleIndex")
    public HashMap<String, String> coupleIndex(@RequestBody Object _email) throws SQLException {


        System.out.println(_email);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        HashMap<String, String> result = new HashMap<>();
        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String email = _email.toString();


            String sql = "SELECT A.COUPLEINDEX, A.NICKNAME, B.NICKNAME, A.YEAR, A.MONTH, A.DAY FROM GOOGLEEMAIL A JOIN GOOGLEEMAIL B ON A.EMAIL = B.COUPLEEMAIL WHERE a.EMAIL ='" + email + "';";


            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);


            if (rs.next()) {

                String couple_index = rs.getString(1).toString();

                String nickname = rs.getString(2).toString();

                String nickname2 = rs.getString(3).toString();

                String year = rs.getString(4).toString();

                String month = rs.getString(5).toString();

                String day = rs.getString(6).toString();

                result.put("couple_index", couple_index);

                result.put("nickname1", nickname);

                result.put("nickname2", nickname2);

                result.put("year", year);

                result.put("month", month);

                result.put("day", day);

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


    @GetMapping(value = "/mobile/download.do")
    public ResponseEntity<ByteArrayResource> download(@RequestParam("couple_index") String couple_index) throws
            IOException {
        System.out.println("download:" + couple_index);

        Path path = Paths.get("C:/Users/User/image" + couple_index);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(MediaType.parseMediaType("image/jpeg")) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
    }


}
