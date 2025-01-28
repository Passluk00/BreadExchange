package it.uniromatre.breadexchange2_0.bakery.Week;

import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "week")
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private WeekDay lun;

    @OneToOne
    private WeekDay mar;

    @OneToOne
    private WeekDay mer;

    @OneToOne
    private WeekDay gio;

    @OneToOne
    private WeekDay ven;

    @OneToOne
    private WeekDay sab;

    @OneToOne
    private WeekDay dom;

}
