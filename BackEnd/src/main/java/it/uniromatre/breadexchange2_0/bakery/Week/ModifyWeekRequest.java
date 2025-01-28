package it.uniromatre.breadexchange2_0.bakery.Week;

import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModifyWeekRequest {

    private WeekDay lun;
    private WeekDay mar;
    private WeekDay mer;
    private WeekDay gio;
    private WeekDay ven;
    private WeekDay sab;
    private WeekDay dom;

}
