package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TitleResponse {

    String startYear;
    String startMonth;
    String startDay;
    String endYear;
    String endMonth;
    String endDay;
    String allDayCheck;
    String title;
}
//public enum E_Schedule
//{
//    E_couple_index = 1,
//
//}
//나중에 이넘클래스 확인