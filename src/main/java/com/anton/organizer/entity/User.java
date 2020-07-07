package com.anton.organizer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Input your name")
    private String name;

    @NotBlank(message = "Input your password")
    private String password;

    private String email;
    private String activationCode;

    private boolean active;

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Theme> themes = new ArrayList<>();

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public void addPlanTheme(Theme theme) {
        themes.add(theme);
    }

    public void deletePlanById(int id) {
        plans.removeIf(plan -> plan.getId() == id);
    }

    public void deleteThemeById(int id) {
        themes.removeIf(theme -> theme.getId() == id);
    }

    public void editTheme(int id, Theme editedTheme) {
        for(Theme theme: themes) {
            if(theme.getId() == id)
                theme.setName(editedTheme.getName());
        }
    }
    public void editPlan(int id, Plan editedPlan) {
        for(Plan plan: plans) {
            if(plan.getId() == id) {
                plan.setDate(editedPlan.getDate());
                plan.setTime(editedPlan.getTime());
                plan.setDescription(editedPlan.getDescription());
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }
}
