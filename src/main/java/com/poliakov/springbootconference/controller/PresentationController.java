package com.poliakov.springbootconference.controller;

import com.poliakov.springbootconference.converters.Converter;
import com.poliakov.springbootconference.dto.CreatePresentationDto;
import com.poliakov.springbootconference.dto.SpeakerDto;
import com.poliakov.springbootconference.dto.SuggestPresentationDto;
import com.poliakov.springbootconference.exceptions.ResourceNotFoundException;
import com.poliakov.springbootconference.model.Presentation;
import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.model.UserRole;
import com.poliakov.springbootconference.service.PresentationService;
import com.poliakov.springbootconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class PresentationController {

    private final PresentationService presentationService;
    private final UserService userService;
    private final Converter converter;

    @Autowired
    public PresentationController(PresentationService presentationService, UserService userService,
                                  Converter converter) {
        this.presentationService = presentationService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/presentation/create/{conferenceId}")
    @RolesAllowed({"MODERATOR"})
    public String createPresentationForm(Model model, @PathVariable Long conferenceId) {
        CreatePresentationDto dto = new CreatePresentationDto();
        dto.setPresentationApproved(true);
        dto.setSpeakerApproved(false);
        dto.setConferenceId(conferenceId);
        model.addAttribute("presentation", dto);
        addSpeakersToModel(model);
        return "/presentation-create";
    }

    @PostMapping("/presentation/create")
    @RolesAllowed({"MODERATOR"})
    public String createPresentation(CreatePresentationDto presentationDto) {
        Presentation presentation = converter.toPresentationModel(presentationDto);
        presentationService.savePresentation(presentation);
        return "redirect:/conference/" + presentation.getConferenceId();
    }

    private void addSpeakersToModel(Model model) {
        List<User> users = userService.findByRole(UserRole.SPEAKER);
        List<SpeakerDto> speakers = converter.toSpeakerDtoList(users);
        model.addAttribute("speakers", speakers);
    }

    @GetMapping("/presentation/{id}")
    @RolesAllowed({"MODERATOR"})
    public String editPresentation(@PathVariable Long id, Model model) {
        Presentation presentation = presentationService.findById(id);
        if (presentation == null) {
            throw new ResourceNotFoundException();
        }
        CreatePresentationDto dto = converter.toCreatePresentationDto(presentation);
        model.addAttribute("presentation", dto);
        addSpeakersToModel(model);
        return "/presentation-edit";
    }

    @PostMapping("/presentation/{id}")
    @RolesAllowed({"MODERATOR"})
    public String editPresentation(CreatePresentationDto presentationDto, @PathVariable Long id) {
        Presentation model = converter.toPresentationModel(presentationDto);
        model.setId(id);
        presentationService.savePresentation(model);
        return "redirect:/conference/" + presentationDto.getConferenceId();
    }

    @PostMapping("/presentation/{id}/suggest-speaker")
    @RolesAllowed({"SPEAKER"})
    public String suggestSpeaker(HttpServletRequest request, @PathVariable Long id) {
        Presentation presentation = presentationService.findById(id);
        if (presentation == null) {
            throw new ResourceNotFoundException();
        }
        if (presentation.getSpeaker() != null) {
            throw new ResourceNotFoundException();
        }
        Optional<User> user = userService.getCurrentUser(request);
        if (user.isPresent()) {
            presentation.setSpeakerId(user.get().getId());
            presentation.setSpeakerApproved(false);
            presentationService.savePresentation(presentation);
        }
        return "redirect:/conference/" + presentation.getConferenceId();
    }

    @PostMapping("/presentation/{id}/delete/{conferenceid}")
    @RolesAllowed({"MODERATOR"})
    public String deletePresentation(@PathVariable Long id, @PathVariable Long conferenceid) {
        System.out.println(id + " conference " + conferenceid);
        presentationService.deletePresentation(id);
        return "redirect:/conference/" + conferenceid;
    }

    @GetMapping("/presentation/suggest/{conferenceid}")
    @RolesAllowed({"SPEAKER"})
    public String suggestPresentationForm(Model model, @PathVariable Long conferenceid) {
        SuggestPresentationDto dto = new SuggestPresentationDto();
        dto.setConferenceId(conferenceid);
        model.addAttribute("presentation", dto);
        return "presentation-suggest";
    }

    @PostMapping("/presentation/suggest")
    @RolesAllowed({"SPEAKER"})
    public String createPresentation(HttpServletRequest request, SuggestPresentationDto presentationDto) {
        Optional<User> user = userService.getCurrentUser(request);
        if (user.isPresent()) {
            Presentation presentation = converter.toPresentationModel(presentationDto, user.get().getId());
            presentation.setPresentationApproved(false);
            presentation.setSpeakerApproved(false);
            presentationService.savePresentation(presentation);
        }
        return "redirect:/conference/" + presentationDto.getConferenceId();
    }
}
