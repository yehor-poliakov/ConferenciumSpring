package com.poliakov.springbootconference.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "conference")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 30, message = "Title should be between 2 and 30 characters")
    private String title;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "conference")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Presentation> presentations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinTable(
            name = "conference_participants",
            joinColumns = { @JoinColumn(name = "conference_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> participants = new HashSet<>();

    @Column(name = "actual_participants_count")
    private Integer actualParticipantsCount;
}
