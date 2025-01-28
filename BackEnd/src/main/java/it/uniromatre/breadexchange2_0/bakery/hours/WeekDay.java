package it.uniromatre.breadexchange2_0.bakery.hours;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "day")
public class WeekDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String dayOfWeek;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime morningOpening;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime morningClosing;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime eveningOpening;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime eveningClosing;

    @Column(nullable = false)
    private boolean isClosed = false;

    @Column(nullable = false)
    private boolean isAllDayOpen = false;


    public WeekDay(String dayOfWeek, LocalTime morningOpening, LocalTime eveningClosing){
        this.dayOfWeek = dayOfWeek;
        this.morningOpening = morningOpening;
        this.eveningClosing = eveningClosing;
        this.isAllDayOpen = true;
    }


    public WeekDay(String dayOfWeek, LocalTime morningOpening, LocalTime morningClosing, LocalTime eveningOpening, LocalTime eveningClosing){
        this.dayOfWeek = dayOfWeek;
        this.morningOpening = morningOpening;
        this.morningClosing = morningClosing;
        this.eveningOpening = eveningOpening;
        this.eveningClosing = eveningClosing;

    }

    public WeekDay(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
        this.isClosed = true;
    }


    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }





}

