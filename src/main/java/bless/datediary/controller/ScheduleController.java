package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ScheduleRequest;
import bless.datediary.model.ScheduleResponse;
import bless.datediary.model.TitleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ScheduleController {
    @PostMapping("/api/main2")
    public ArrayList<ScheduleResponse> Schedule(String a) throws SQLException {

        System.out.println("a: "+a);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        ArrayList<ScheduleResponse> result = new ArrayList<ScheduleResponse>();

        DBconn = new DBConn();
        conn = DBconn.connect();
        String sql = " select * from schedule;";
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery(sql);
        while (rst.next()) {
            ScheduleResponse schedule = new ScheduleResponse();
            schedule.setCouple_index(rst.getString(1));
            schedule.setSchedule_index(rst.getString(2));
            schedule.setStart_day(rst.getString(3));
            schedule.setStart_time(rst.getString(4));
            schedule.setEnd_day(rst.getString(5));
            schedule.setEnd_time(rst.getString(6));
            schedule.setAllDayCheck(rst.getString(7));
            schedule.setTitle(rst.getString(8));
            schedule.setContents(rst.getString(9));
            schedule.setPlace_code(rst.getString(10));
            schedule.setMission_code(rst.getString(11));
            schedule.setStory_reg(rst.getString(12));

            result.add(schedule);
        }
        rst.close();
        stmt.close();
        conn.close();

        System.out.println("result: " + result);

        return result;
    }


    @PostMapping("/api/postTest")
    public ArrayList<HashMap<String,Object>> postTest(@RequestBody HashMap<String, Object> _tmp) throws SQLException {


        System.out.println(_tmp.get("id"));
        System.out.println(_tmp.get("password"));

        ArrayList<ScheduleRequest> postModels = new ArrayList<ScheduleRequest>();

        for (int i = 0; i < _tmp.size(); i++) {

            ScheduleRequest postModel = new ScheduleRequest();

            postModel.setId(_tmp.get("id").toString());

            postModel.setPassword(_tmp.get("password").toString());

            postModels.add(postModel);

            System.out.println(postModels.get(0));
        }

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        String result = "기본값";

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String id = postModels.get(0).getId();
            String password = postModels.get(0).getPassword();

            System.out.println("입력한 아이디 : "+id);
            System.out.println("입력한 비밀번호 : "+password);

            String sql = "select user_id from user where user_id =\"" + id + "\"" + "and user_pw =\"" + password + "\";";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String selectId = rs.getString(1).toString();
                if (selectId == null) {
                    result = "아이디가 없어요..";
                    System.out.println(result);
                } else if (selectId.equals(id)) {
                    result = id + "님 환영합니다!";
                }
            } else {
                result = "아이디가 없어요..";
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

        System.out.println("결과값 : " + result);

        HashMap<String,Object> result2 = new HashMap<String,Object>();

        result2.put("result", result);
        result2.put("status", "서버에서 가져온 데이터입니다");

        ArrayList<HashMap<String,Object>> result3 = new ArrayList<HashMap<String,Object>>();

        result3.add(result2);

        System.out.println(result3);

        return result3;
    }

    @GetMapping("/hyunha")
    public String postTest() throws SQLException {

        return "정말 고마워 사랑해";
    }

    @PostMapping("/api/MonthlyCalendar")
    public ArrayList<TitleResponse> MonthlyCalendar(@RequestBody HashMap<String, Object> _tmp2) throws SQLException {
        //_tmp2는 couple_index

        System.out.println(_tmp2.get("couple_index"));

        String coupleIndex = _tmp2.get("couple_index").toString();
        String selectedMonth = _tmp2.get("selected_month").toString();

        System.out.println(coupleIndex);
        System.out.println(selectedMonth);


        ArrayList<TitleResponse> titleList = new ArrayList<TitleResponse>();


        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "select start_year, start_month, start_day, end_year, end_month, end_day, allDayCheck, title from schedule\n" +
                    "where couple_index = '"+ coupleIndex + "' and start_month = '" + selectedMonth + "' order by start_day, end_day desc;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

//            if(rs.next()==false) {
//                System.out.println("셀렉된 게 없다");
//            } else
            {

                while (rs.next()) {

                    TitleResponse titleResponse = new TitleResponse();
                    System.out.println(rs.getString(7));
                    titleResponse.setStartYear(rs.getString(1).toString());
                    titleResponse.setStartMonth(rs.getString(2).toString());
                    titleResponse.setStartDay(rs.getString(3).toString());
                    titleResponse.setEndYear(rs.getString(4).toString());
                    titleResponse.setEndMonth(rs.getString(5).toString());
                    titleResponse.setEndDay(rs.getString(6).toString());
                    titleResponse.setAllDayCheck((rs.getString(7).equals("1")) ? "true" : "false");
                    titleResponse.setTitle(rs.getString(8).toString());


                    titleList.add(titleResponse);
                }
            }
            System.out.println(titleList.get(2).getAllDayCheck());

//            String sql = "select user_id from user where user_id =\"" + id + "\"" + "and user_pw =\"" + password + "\";";
//
//            Statement stmt = conn.createStatement();
//
//            ResultSet rs = stmt.executeQuery(sql);
//
//            if (rs.next()) {
//                String selectId = rs.getString(1).toString();
//                if (selectId == null) {
//                    result = "아이디가 없어요..";
//                    System.out.println(result);
//                } else if (selectId.equals(id)) {
//                    result = id + "님 환영합니다!";
//                }
//            } else {
//                result = "아이디가 없어요..";
//            }

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

        return titleList;
    }

}
