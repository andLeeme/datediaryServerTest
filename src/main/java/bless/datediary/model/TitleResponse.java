package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TitleResponse {

    String coupleIndex;
    String scheduleIndex;
    String startYear;
    String startMonth;
    String startDay;
    String startTime;
    String endYear;
    String endMonth;
    String endDay;
    String endTime;
    String allDayCheck;
    String title;
    String contents;
    String placeCode;
    String missionCode;

}
//public enum E_Schedule
//{
//    E_couple_index = 1,
//
//}
//나중에 이넘클래스 확인