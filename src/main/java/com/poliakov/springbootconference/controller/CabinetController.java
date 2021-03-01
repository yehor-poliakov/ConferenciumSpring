package com.poliakov.springbootconference.controller;

import com.poliakov.springbootconference.converters.Converter;
import com.poliakov.springbootconference.dto.SpeakerPresentationDto;
import com.poliakov.springbootconference.exceptions.ResourceNotFoundException;
import com.poliakov.springbootconference.model.Presentation;
import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.service.PresentationService;
import com.poliakov.springbootconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class CabinetController {
    private final PresentationService presentationService;
    private final UserService userService;
    private final Converter converter;

    @Autowired
    public CabinetController(PresentationService presentationService, UserService userService,
                             Converter converter) {
        this.presentationService = presentationService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/speaker")
    @RolesAllowed({"SPEAKER"})
    public String speakerCabinet(Model model, HttpServletRequest request,
                                 @PageableDefault(size = 5) Pageable pageable) {
        Optional<User> user = userService.getCurrentUser(request);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException();
        }
        User speaker = user.get();
        Page<Presentation> presentations = presentationService.findAllBySpeaker(speaker.getId(), pageable);
        Page<SpeakerPresentationDto> page = presentations.map(converter::toSpeakerPresentationDto);
        model.addAttribute("page", page);
        return "/speaker-cabinet";
    }
}
