package gr.aueb.cf.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "region",  fetch = FetchType.LAZY )
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public Region(String title) {
        this.title = title;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.setRegion(this);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.setRegion(null);
    }

    @Override
    public String toString() {
        return String.format("%dd %s", id, title);
    }
}
