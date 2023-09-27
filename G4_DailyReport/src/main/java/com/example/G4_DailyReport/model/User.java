package com.example.G4_DailyReport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @NaturalId
    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;
    private boolean enabled;
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String avatar;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name="department_id"
            , nullable=true
    )
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id"
//            , nullable=false
    )
    private Position position;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;

    @OneToMany(mappedBy = "user")
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "user")
    private List<MemberJobsProgress> memberJobsProgresses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    //--- Constructor --------------------------------
    public User(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    // Implementing UserDetail's methods.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
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
        return true;
    }

    //---@GET
    //Một User viết nhiều Report
//    @OneToMany(
//            cascade = CascadeType.MERGE,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    @JsonIgnore
//    private List<Report> reports = new ArrayList<>();
//
//    public void addReport(Report report) {
//        reports.add(report);
//        report.setUser(this);
//    }
//
//    public void removeReport(Report report) {
//        reports.remove(report);
//        report.setUser(null);
//    }
}