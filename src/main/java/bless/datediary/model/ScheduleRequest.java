package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
    String couple_index;
    String start_year;
    String start_month;
    String start_day;
    String start_time;
    String end_year;
    String end_month;
    String end_day;
    String end_time;
    String allDayCheck;
    String title;
    String contents;
    String place_code;
    String mission_code;
    String name;
    String timestamp2;
}
