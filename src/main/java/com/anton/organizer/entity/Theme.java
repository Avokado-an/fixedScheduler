package com.anton.organizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Theme")
public class Theme implements Comparable<Theme> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String name;

    @OneToMany(mappedBy = "theme", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Plan> plans = new ArrayList<>();

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "owner")
    private User user;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "parent")
    private Theme parentTheme;

    @OneToMany(mappedBy = "parentTheme", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
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
