package com.poliakov.springbootconference.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;


@Data
public class PresentationDto {
    private Long id;
    private String topic;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;
    private String speaker;
    private boolean presentationApproved;
    private boolean speakerApproved;
}
