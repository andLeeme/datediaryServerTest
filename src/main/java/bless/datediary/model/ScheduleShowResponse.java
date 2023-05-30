package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleShowResponse {

    String coupleIndex;
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
