package OrganizerProject.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Theme")
public class Theme implements Comparable<Theme>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Input the description of the theme")
    String name;

    @OneToMany(mappedBy = "theme")
    private List<Plan> plans = new ArrayList<>();

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "owner")
    private User user;

    @ManyToOne(cascade={CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="parent")
    private Theme parentTheme;

    @OneToMany(mappedBy="parentTheme", orphanRemoval = true)
    private List<Theme> childThemes;

    public Theme(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addTheme(Theme theme) {
        childThemes.add(theme);
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    @Override
    public int compareTo(Theme o) {
        return this.name.compareTo(o.name);
    }
}
