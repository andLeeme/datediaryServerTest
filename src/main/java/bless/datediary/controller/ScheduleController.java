package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ScheduleEditRequest;
import bless.datediary.model.ScheduleRequest;
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
    @PostMapping("/api/scheduleReg")
    public int ScheduleReg(@RequestBody ScheduleRequest _tmp3) throws SQLException {

        System.out.println("a: " + _tmp3.getCouple_index());

        ScheduleRequest req = new ScheduleRequest();


        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;

//        String place_code = _tmp3.getPlace_code();
//        String mission_code = _tmp3.getMission_code();


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "insert into schedule (couple_index, start_year, start_month, start_day, start_time," +
                    "end_year, end_month, end_day, end_time, allDayCheck, title, contents, place_code, mission_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, _tmp3.getCouple_index());
            pstmt.setString(2, _tmp3.getStart_year());
            pstmt.setString(3, _tmp3.getStart_month());
            pstmt.setString(4, _tmp3.getStart_day());
            pstmt.setString(5, _tmp3.getStart_time());
            pstmt.setString(6, _tmp3.getEnd_year());
            pstmt.setString(7, _tmp3.getEnd_month());
            pstmt.setString(8, _tmp3.getEnd_day());
            pstmt.setString(9, _tmp3.getEnd_time());
            pstmt.setString(10, _tmp3.getAllDayCheck());
            pstmt.setString(11, _tmp3.getTitle());
            pstmt.setString(12, _tmp3.getContents());
            pstmt.setString(13, _tmp3.getPlace_code());
            pstmt.setString(14, _tmp3.getMission_code());

            pstmt.executeUpdate();
            result = 1;

        } catch (Exception e) {
            e.printStackTrace();
            result = 99;
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
        System.out.println("result: " + result);
        return result;
    }


    //스케줄 수정용
    @PostMapping("/api/scheduleEditReg")
    public int ScheduleEditReg(@RequestBody ScheduleEditRequest _tmp4) throws SQLException {

        System.out.println("a: " + _tmp4.getSchedule_index());

        ScheduleEditRequest reqEdit = new ScheduleEditRequest();

        String scheduleIndex = _tmp4.getSchedule_index();

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int resultEdit = 0;


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql =  "update schedule set " +
                    "start_year ='"+_tmp4.getStart_year()+"' " +
                    "start_month ='"+_tmp4.getStart_month()+"' " +
                    "start_day ='"+_tmp4.getStart_day()+"' " +
                    "start_time ='"+_tmp4.getStart_time()+"' " +
                    "start_year ='"+_tmp4.getEnd_year()+"' " +
                    "end_year ='"+_tmp4.getEnd_year()+"' " +
                    "end_month ='"+_tmp4.getEnd_month()+"' " +
                    "end_day ='"+_tmp4.getEnd_day()+"' " +
                    "end_time ='"+_tmp4.getEnd_time()+"' " +
                    "allDayCheck ='"+_tmp4.getAllDayCheck()+"' " +
                    "title ='"+_tmp4.getTitle()+"' " +
                    "contents ='"+_tmp4.getContents()+"' " +
                    "place_code ='"+_tmp4.getPlace_code()+"' " +
                    "mission_code ='"+_tmp4.getMission_code()+"' " +
                    "where schedule_index = '"+scheduleIndex+"';";

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


//    @PostMapping("/api/postTest")
//    public ArrayList<HashMap<String,Object>> postTest(@RequestBody HashMap<String, Object> _tmp) throws SQLException {
//
//
//        System.out.println(_tmp.get("id"));
//        System.out.println(_tmp.get("password"));
//
//        ArrayList<ScheduleRequest> postModels = new ArrayList<ScheduleRequest>();
//
//        for (int i = 0; i < _tmp.size(); i++) {
//
//            ScheduleRequest postModel = new ScheduleRequest();
//
//            postModel.setId(_tmp.get("id").toString());
//
//            postModel.setPassword(_tmp.get("password").toString());
//
//            postModels.add(postModel);
//
//            System.out.println(postModels.get(0));
//        }
//
//        DBConn DBconn;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        String result = "기본값";
//
//        try {
//
//            DBconn = new DBConn();
//            conn = DBconn.connect();
//
//            String id = postModels.get(0).getId();
//            String password = postModels.get(0).getPassword();
//
//            System.out.println("입력한 아이디 : "+id);
//            System.out.println("입력한 비밀번호 : "+password);
//
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
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception e3) {
//                e3.printStackTrace();
//            }
//
//        }
//
//        System.out.println("결과값 : " + result);
//
//        HashMap<String,Object> result2 = new HashMap<String,Object>();
//
//        result2.put("result", result);
//        result2.put("status", "서버에서 가져온 데이터입니다");
//
//        ArrayList<HashMap<String,Object>> result3 = new ArrayList<HashMap<String,Object>>();
//
//        result3.add(result2);
//
//        System.out.println(result3);
//
//        return result3;
//    }

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
