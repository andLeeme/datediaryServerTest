package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleResponse {

    String couple_index;
    String schedule_index;
    String start_day;
    String start_time;
    String end_day;
    String end_time;
    String allDayCheck;
    String title;
    String contents;
    String place_code;
    String mission_code;
    String story_reg;
}
//public enum E_Schedule
//{
//    E_couple_index = 1,
//
//}
//나중에 이넘클래스 확인