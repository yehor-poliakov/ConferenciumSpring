package com.poliakov.springbootconference.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class CreatePresentationDto {
    private Long id;
    private String topic;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;
    private Long speakerId;
    private Long conferenceId;
    private boolean speakerApproved;
    private boolean presentationApproved;
}
