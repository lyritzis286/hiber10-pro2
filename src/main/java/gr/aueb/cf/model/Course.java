package gr.aueb.cf.model;


import gr.aueb.cf.enums.LessonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 1000)
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type")
    private LessonType lessonType;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    public Course(String title, String comments, LessonType lessonType) {
        this.title = title;
        this.comments = comments;
        this.lessonType = lessonType;
    }



    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.getCourses().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.getCourses().remove(this);
    }

    @Override
    public String toString() {
        return String.format("%d %s", id, title);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course course)) return false;
        return Objects.equals(getTitle(), course.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTitle());
    }
}
