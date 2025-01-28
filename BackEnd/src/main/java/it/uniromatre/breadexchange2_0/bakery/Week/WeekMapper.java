package it.uniromatre.breadexchange2_0.bakery.Week;

import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import org.springframework.stereotype.Service;

@Service
public class WeekMapper {


    public Week toWeek(ModifyWeekRequest mod){

        // crei ogni giorno della settiana

        WeekDay lun = WeekDay.builder()
                .dayOfWeek(mod.getLun().getDayOfWeek())
                .morningOpening(mod.getLun().getMorningOpening())
                .morningClosing(mod.getLun().getMorningClosing())
                .eveningOpening(mod.getLun().getEveningOpening())
                .eveningClosing(mod.getLun().getEveningClosing())
                .isClosed(mod.getLun().isClosed())
                .isAllDayOpen(mod.getLun().isAllDayOpen())
                .build();

        WeekDay mar = WeekDay.builder()
                .dayOfWeek(mod.getMar().getDayOfWeek())
                .morningOpening(mod.getMar().getMorningOpening())
                .morningClosing(mod.getMar().getMorningClosing())
                .eveningOpening(mod.getMar().getEveningOpening())
                .eveningClosing(mod.getMar().getEveningClosing())
                .isClosed(mod.getMar().isClosed())
                .isAllDayOpen(mod.getMar().isAllDayOpen())
                .build();

        WeekDay mer = WeekDay.builder()
                .dayOfWeek(mod.getMer().getDayOfWeek())
                .morningOpening(mod.getMer().getMorningOpening())
                .morningClosing(mod.getMer().getMorningClosing())
                .eveningOpening(mod.getMer().getEveningOpening())
                .eveningClosing(mod.getMer().getEveningClosing())
                .isClosed(mod.getMer().isClosed())
                .isAllDayOpen(mod.getMer().isAllDayOpen())
                .build();

        WeekDay gio = WeekDay.builder()
                .dayOfWeek(mod.getGio().getDayOfWeek())
                .morningOpening(mod.getGio().getMorningOpening())
                .morningClosing(mod.getGio().getMorningClosing())
                .eveningOpening(mod.getGio().getEveningOpening())
                .eveningClosing(mod.getGio().getEveningClosing())
                .isClosed(mod.getGio().isClosed())
                .isAllDayOpen(mod.getGio().isAllDayOpen())
                .build();

        WeekDay ven = WeekDay.builder()
                .dayOfWeek(mod.getVen().getDayOfWeek())
                .morningOpening(mod.getVen().getMorningOpening())
                .morningClosing(mod.getVen().getMorningClosing())
                .eveningOpening(mod.getVen().getEveningOpening())
                .eveningClosing(mod.getVen().getEveningClosing())
                .isClosed(mod.getVen().isClosed())
                .isAllDayOpen(mod.getVen().isAllDayOpen())
                .build();

        WeekDay sab = WeekDay.builder()
                .dayOfWeek(mod.getSab().getDayOfWeek())
                .morningOpening(mod.getSab().getMorningOpening())
                .morningClosing(mod.getSab().getMorningClosing())
                .eveningOpening(mod.getSab().getEveningOpening())
                .eveningClosing(mod.getSab().getEveningClosing())
                .isClosed(mod.getSab().isClosed())
                .isAllDayOpen(mod.getSab().isAllDayOpen())
                .build();

        WeekDay dom = WeekDay.builder()
                .dayOfWeek(mod.getDom().getDayOfWeek())
                .morningOpening(mod.getDom().getMorningOpening())
                .morningClosing(mod.getDom().getMorningClosing())
                .eveningOpening(mod.getDom().getEveningOpening())
                .eveningClosing(mod.getDom().getEveningClosing())
                .isClosed(mod.getDom().isClosed())
                .isAllDayOpen(mod.getDom().isAllDayOpen())
                .build();

        // poi salvi ogni nuovo giorno nella settimana

        Week nuova = Week.builder()
                .lun(lun)
                .mar(mar)
                .mer(mer)
                .gio(gio)
                .ven(ven)
                .sab(sab)
                .dom(dom)
                .build();
        return nuova;
    }



}
