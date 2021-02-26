package com.poliakov.springbootconference.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SpeakerPresentationDto {
    private Long conferenceId;
    private String presentationTopic;
    private String conferenceTitle;
    private String conferenceLocation;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate conferenceDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime presentationTime;
    private boolean presentationApproved;
    private boolean speakerApproved;
}
