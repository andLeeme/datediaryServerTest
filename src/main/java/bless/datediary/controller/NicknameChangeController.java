package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;


@RestController
public class NicknameChangeController {
    @PostMapping("/api/nicknameChange")
    public int nicknameChange(@RequestBody NicknameChangeRequest _tmp) throws SQLException {

        System.out.println("User_email: " + _tmp.getUser_email());
        System.out.println("Edited_nickname: " + _tmp.getEdited_nickname());

        ScheduleEditRequest reqEdit = new ScheduleEditRequest();

        String userEmail = _tmp.getUser_email();
        String editedNickname = _tmp.getEdited_nickname();



        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int resultEdit = 0;


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "update googleemail set nickname ='"  + editedNickname + "' where email = '" + userEmail + "';";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();


            resultEdit = 1;

        } catch (Exception e) {
            e.printStackTrace();
            resultEdit = 99;
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
        System.out.println("result: " + resultEdit);
        return resultEdit;
    }



}
