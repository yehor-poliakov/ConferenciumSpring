package com.poliakov.springbootconference.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "presentation")
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic", nullable=false)
    private String topic;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="speaker_id", nullable = true, insertable = false, updatable = false)
    private User speaker;

    @Column(name = "speaker_id", nullable = true)
    private Long speakerId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="conference_id", nullable = false, insertable = false, updatable = false)
    private Conference conference;

    @Column(name = "conference_id", nullable = false)
    private Long conferenceId;

    @Column(name = "presentation_approved", nullable = false)
    private boolean presentationApproved;

    @Column(name = "speaker_approved", nullable = false)
    private boolean speakerApproved;
}
