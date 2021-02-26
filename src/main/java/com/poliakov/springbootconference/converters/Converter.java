package com.poliakov.springbootconference.converters;

import com.poliakov.springbootconference.dto.*;
import com.poliakov.springbootconference.model.Conference;
import com.poliakov.springbootconference.model.Presentation;
import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Converter {
    private Converter() {
    }

    public ConferenceDto toConferenceDto(Conference model, User user) {
        ConferenceDto dto = new ConferenceDto();
        setCommonConferenceDtoProperties(model, dto);

        List<PresentationDto> presentationDtos = toPresentationDtoList(model.getPresentations());
        dto.setPresentations(presentationDtos);

        boolean registered = model.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toList())
                .contains(user.getId());
        dto.setRegistered(registered);

        return dto;
    }

    private void setCommonConferenceDtoProperties(Conference model, CreateConferenceDto dto) {
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setLocation(model.getLocation());
        dto.setDate(model.getDate());
        dto.setActualParticipantsCount(model.getActualParticipantsCount());
    }

    public List<ConferenceDto> toConferenceDtoList(List<Conference> models, User user) {
        return models.stream()
                .map(model -> toConferenceDto(model, user))
                .collect(Collectors.toList());
    }

    public ConferenceDetailsDto toConferenceDetailsDto(Conference model) {
        ConferenceDetailsDto dto = new ConferenceDetailsDto();
        setCommonConferenceDtoProperties(model, dto);

        long approvedPresentations = model.getPresentations()
                .stream()
                .filter(Presentation::isPresentationApproved)
                .count();

        dto.setDate(model.getDate());
        dto.setPresentationsCount((int) approvedPresentations);
        dto.setParticipantsCount(model.getParticipants().size());
        return dto;
    }

    public List<ConferenceDetailsDto> toConferenceDetailsDtoList(List<Conference> models) {
        return models.stream()
                .map(this::toConferenceDetailsDto)
                .collect(Collectors.toList());
    }


    public String speakerToString(User speaker) {
        if (speaker == null) {
            return "";
        }
        return speaker.getFirstName() + " " + speaker.getLastName();
    }

    public PresentationDto toPresentationDto(Presentation model) {
        PresentationDto dto = new PresentationDto();
        dto.setId(model.getId());
        dto.setTime(model.getTime());
        dto.setTopic(model.getTopic());
        dto.setSpeaker(speakerToString(model.getSpeaker()));
        dto.setPresentationApproved(model.isPresentationApproved());
        dto.setSpeakerApproved(model.isSpeakerApproved());
        return dto;
    }

    public CreatePresentationDto toCreatePresentationDto(Presentation model) {
        CreatePresentationDto dto = new CreatePresentationDto();
        dto.setId(model.getId());
        dto.setTime(model.getTime());
        dto.setTopic(model.getTopic());
        dto.setSpeakerId(model.getSpeakerId());
        dto.setConferenceId(model.getConferenceId());
        dto.setPresentationApproved(model.isPresentationApproved());
        dto.setSpeakerApproved(model.isSpeakerApproved());
        return dto;
    }

    public List<PresentationDto> toPresentationDtoList(Collection<Presentation> models) {
        return models.stream()
                .map(this::toPresentationDto)
                .collect(Collectors.toList());
    }

    public Presentation toPresentationModel(CreatePresentationDto dto) {
        Presentation presentation = new Presentation();
        presentation.setTopic(dto.getTopic());
        presentation.setTime(dto.getTime());
        presentation.setSpeakerId(dto.getSpeakerId());
        presentation.setConferenceId(dto.getConferenceId());
        presentation.setPresentationApproved(dto.isPresentationApproved());
        presentation.setSpeakerApproved(dto.isSpeakerApproved());
        return presentation;
    }

    public Presentation toPresentationModel(SuggestPresentationDto dto, long speakerId) {
        Presentation presentation = new Presentation();
        presentation.setTopic(dto.getTopic());
        presentation.setTime(dto.getTime());
        presentation.setSpeakerId(speakerId);
        presentation.setConferenceId(dto.getConferenceId());
        presentation.setPresentationApproved(false);
        presentation.setSpeakerApproved(true);
        return presentation;
    }
    // ToDo builders
    public SpeakerPresentationDto toSpeakerPresentationDto(Presentation model) {
        SpeakerPresentationDto dto = new SpeakerPresentationDto();
        dto.setConferenceId(model.getConferenceId());
        dto.setPresentationTime(model.getTime());
        dto.setPresentationApproved(model.isPresentationApproved());
        dto.setSpeakerApproved(model.isSpeakerApproved());
        dto.setPresentationTopic(model.getTopic());
        dto.setConferenceDate(model.getConference().getDate());
        dto.setConferenceLocation(model.getConference().getLocation());
        dto.setConferenceTitle(model.getConference().getTitle());
        return dto;
    }

    public List<SpeakerPresentationDto> toSpeakerPresentationDtoList(List<Presentation> models) {
        return models.stream()
                .map(this::toSpeakerPresentationDto)
                .collect(Collectors.toList());
    }

    public Conference toConferenceModel(CreateConferenceDto dto) {
        Conference conference = new Conference();
        conference.setId(dto.getId());
        conference.setTitle(dto.getTitle());
        conference.setLocation(dto.getLocation());
        conference.setDate(dto.getDate());
        conference.setActualParticipantsCount(dto.getActualParticipantsCount());
        return conference;
    }

    public SpeakerDto toSpeakerDto(User model) {
        SpeakerDto dto = new SpeakerDto();
        dto.setId(model.getId());
        dto.setName(model.getFirstName() + " " + model.getLastName());
        return dto;
    }

    public List<SpeakerDto> toSpeakerDtoList(Collection<User> models) {
        return models
                .stream()
                .map(this::toSpeakerDto)
                .collect(Collectors.toList());
    }

    public User toUserModel(UserDto userDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getAsSpeaker() == null ? UserRole.PARTICIPANT : UserRole.SPEAKER);
        return user;
    }
}
