package OrganizerProject.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Plan")
public class Plan implements Comparable<Plan>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String description;

    private Date time;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "theme")
    private Theme theme;

    public Plan(String description, Date time, Date date, User owner) {
        this.description = description;
        this.time = time;
        this.date = date;
        this.owner = owner;
    }

    @Override
    public int compareTo(Plan o) {
        return o.date.compareTo(date);
    }
}
