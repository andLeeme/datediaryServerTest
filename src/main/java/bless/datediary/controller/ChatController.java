package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ChatRequestBody;
import bless.datediary.model.ChatResponseBody;
import bless.datediary.model.ScheduleDeleteRequest;
import bless.datediary.model.TitleResponse;
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
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ChatController {


    @PostMapping("/api/chat")
    public ArrayList<ChatResponseBody> Chat(@RequestBody ChatRequestBody chatRequestBody) throws SQLException {

        String coupleIndex = chatRequestBody.getCouple_index();
        String email = chatRequestBody.getEmail();
        String sender = chatRequestBody.getSender();
        String message = chatRequestBody.getMessage();
        String timestamp = chatRequestBody.getTimestamp();

        System.out.println(coupleIndex);
        System.out.println(email);
        System.out.println(sender);
        System.out.println(message);
        System.out.println(timestamp);

        ArrayList<ChatResponseBody> ab = new ArrayList<>();

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();


            String sql = "insert chat (couple_index, email, sender, message, timestamp2) values (?,?,?,?,?);";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, coupleIndex);
            pstmt.setString(2, email);
            pstmt.setString(3, sender);
            pstmt.setString(4, message);
            pstmt.setString(5, timestamp);

            pstmt.executeUpdate();

            sql = "select * from chat where couple_index = '" + coupleIndex + "'limit 100;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                ChatResponseBody chatResponseBody = new ChatResponseBody();

                chatResponseBody.setCouple_index(rs.getString(1).toString());
                chatResponseBody.setEmail(rs.getString(2).toString());
                chatResponseBody.setSender(rs.getString(3));
                chatResponseBody.setMessage(rs.getString(4));
                chatResponseBody.setTimestamp(rs.getString(5));

                ab.add(chatResponseBody);
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


        return ab;
    }

}
