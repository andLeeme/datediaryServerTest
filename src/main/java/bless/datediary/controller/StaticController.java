package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;



@RestController
public class StaticController {

    @PostMapping("/api/static1")
    public ArrayList<StaticResponse> static1(@RequestBody StaticRequest _tmp) throws SQLException {

        System.out.println(_tmp.getCouple_index());

        int coupleIndex = Integer.parseInt(_tmp.getCouple_index());
        int startYear = Integer.parseInt(_tmp.getStart_year());
        int startMonth = Integer.parseInt(_tmp.getStart_month());


        ArrayList<StaticResponse> resultCount= new ArrayList<>();

        int startYear1 = 0;
        int startMonth1 = 0;
        int startYear2 = 0;
        int startMonth2 = 0;
        int startYear3 = 0;
        int startMonth3 = 0;


        //1, 2월 예외처리
        switch (startMonth) {
            case 1:
                startYear1 = startYear;
                startMonth1 = startMonth;
                startYear2 = startYear -1;
                startMonth2 = 12;
                startYear3 = startYear -1;
                startMonth3 = 11;
                break;

            case 2:
                startYear1 = startYear;
                startMonth1 = startMonth;
                startYear2 = startYear;
                startMonth2 = startMonth -1;
                startYear3 = startYear -1 ;
                startMonth3 = 12;
                break;

            default:
                startYear1 = startYear;
                startMonth1 = startMonth;
                startYear2 = startYear;
                startMonth2 = startMonth -1;
                startYear3 = startYear;
                startMonth3 = startMonth -2;
                break;
        }


        System.out.println("startYear1 " + startYear1 + " startMonth1 " + startMonth1 + " startYear2 " + startYear2 + " startMonth2 " + startMonth2 + " startYear3 " + startYear3 + " startMonth3 " + startMonth3);



        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();



            //최근 데이트 횟수?
            String sql = "select start_month, count(start_month) from schedule where couple_index = '" + coupleIndex
                    + "' and start_year = '" + startYear1 + "' and start_month = '" + startMonth1 +"' group by start_month;";


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);



            if(rs.next()) {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");
                static1.setStartMonth(rs.getString(1));
                static1.setCount(rs.getString(2));

                result = 123;
                if(rs.getString(1) != null) {

                    resultCount.add(static1);
                    result = 123123;
                } else {
                    System.out.println(startMonth + "의 결과 없음");
                    result = 123123123;
                }
            } else {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");
                static1.setStartMonth("13");
                static1.setCount("0");
                resultCount.add(static1);
            }


            //저번달
            sql = "select start_month, count(start_month) from schedule where couple_index = '" + coupleIndex
                    + "' and start_year = '" + startYear2 + "' and start_month = '" + startMonth2 +"' group by start_month;";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);


            if(rs.next()) {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");

                static1.setStartMonth(rs.getString(1));
                static1.setCount(rs.getString(2));

                result = 123;
                if(rs.getString(1) != null) {

                    resultCount.add(static1);
                    result = 123123;
                } else {
                    System.out.println(startMonth2 + "의 결과 없음");
                    result = 123123123;
                }
            } else {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");
                static1.setStartMonth("13");
                static1.setCount("0");
                resultCount.add(static1);
            }

            //다음달
            sql = "select start_month, count(start_month) from schedule where couple_index = '" + coupleIndex
                    + "' and start_year = '" + startYear3 + "' and start_month = '" + startMonth3 +"' group by start_month;";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);


            if(rs.next()) {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");

                static1.setStartMonth(rs.getString(1));
                static1.setCount(rs.getString(2));

                result = 123;
                if(rs.getString(1) != null) {

                    resultCount.add(static1);
                    result = 123123;
                } else {
                    System.out.println(startMonth3 + "의 결과 없음");
                    result = 123123123;
                }
            } else {
                StaticResponse static1 = new StaticResponse();
                static1.setStartYear(startYear1 + "");
                static1.setStartMonth("13");
                static1.setCount("0");
                resultCount.add(static1);
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
        System.out.println("이번 달 데이트 횟수 " + resultCount.get(0).getStartMonth() + ", " + resultCount.get(0).getStartYear() + ", " + resultCount.get(0).getCount());

        return resultCount;
    }














    @PostMapping("/api/static2")
    public ArrayList<Static2Response> static2(@RequestBody Object _coupleIndex) throws SQLException {

        System.out.println("static2_coupleIndex : " + _coupleIndex.toString());

        String coupleIndex = _coupleIndex.toString();

        ArrayList<Static2Response> resultCount2 = new ArrayList<>();


        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

           //select place_code, count(place_code) as count from schedule where couple_index = "1" group by place_code order by count(place_code) DESC limit 4;

            //가장 많이 방문한 장소
            String sql ="select place_code, count(place_code) as count from schedule where couple_index = '" + coupleIndex
                    + "' group by place_code order by count(place_code) DESC limit 4;";


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()) {
                Static2Response static2 = new Static2Response();

                static2.setPlaceCode(rs.getString(1));
                static2.setCount(rs.getString(2));

                result = 123;
                if(rs.getString(1) != null) {

                    resultCount2.add(static2);
                    result = 123123;
                } else {
                    System.out.println("placeCode의 결과 없음");
                    result = 123123123;
                }
            }

//            else {
//                Static2Response static2 = new Static2Response();
//                static2.setPlaceCode("noResult");
//                static2.setCount("0");
//                resultCount2.add(static2);
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
        System.out.println(result);
//        System.out.println("placecode: " + resultCount2.get(0).getPlaceCode());
//        System.out.println("0번: " + resultCount2.get(0).getPlaceCode() + ", " + resultCount2.get(0).getCount());
//        System.out.println("1번: " + resultCount2.get(1).getPlaceCode() + ", " + resultCount2.get(1).getCount());
//        System.out.println("2번: " + resultCount2.get(2).getPlaceCode() + ", " + resultCount2.get(2).getCount());
//        System.out.println("3번: " + resultCount2.get(3).getPlaceCode() + ", " + resultCount2.get(3).getCount());

        return resultCount2;
    }



    @PostMapping("/api/static3")
    public ArrayList<Static3Response> static3(@RequestBody Static3Request _tmp) throws SQLException {



        String coupleIndex = _tmp.getCouple_index().toString();
        String thisMonth = _tmp.getStart_month().toString();
        String thisYear = _tmp.getStart_year().toString();
        System.out.println("static3_coupleIndex : " + coupleIndex);

        ArrayList<Static3Response> resultCount3 = new ArrayList<>();


        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 99;

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();



            //미션 횟수
            String sql ="select start_year, start_month, count(mission_code) from schedule where couple_index = '" + coupleIndex +
                            "'  group by start_year, start_month order by start_month;";


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()) {
                Static3Response static3 = new Static3Response();

                static3.setYear(rs.getString(1));
                static3.setMonth(rs.getString(2));
                static3.setCount(rs.getString(3));

                result = 123;
                if(rs.getString(1) != null) {

                    resultCount3.add(static3);
                    result = 123123;
                } else {
                    System.out.println("mission의 조회 결과 없음");
                    result = 123123123;
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
        System.out.println("mission: " + resultCount3.get(0).getYear()+ "/" + resultCount3.get(0).getMonth() + "/" + resultCount3.get(0).getCount());
//        System.out.println("0번: " + resultCount2.get(0).getPlaceCode() + ", " + resultCount2.get(0).getCount());
//        System.out.println("1번: " + resultCount2.get(1).getPlaceCode() + ", " + resultCount2.get(1).getCount());
//        System.out.println("2번: " + resultCount2.get(2).getPlaceCode() + ", " + resultCount2.get(2).getCount());
//        System.out.println("3번: " + resultCount2.get(3).getPlaceCode() + ", " + resultCount2.get(3).getCount());

        return resultCount3;
    }



}
