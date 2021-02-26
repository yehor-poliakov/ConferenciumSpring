package com.poliakov.springbootconference.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreateConferenceDto {
        private Long id;
        private String title;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;
        private String location;
        private Integer actualParticipantsCount;
}
