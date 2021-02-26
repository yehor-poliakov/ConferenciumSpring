package com.poliakov.springbootconference.service;

import com.poliakov.springbootconference.model.Presentation;
import com.poliakov.springbootconference.repository.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationService {

    private PresentationRepository presentationRepository;

    @Autowired
    public PresentationService(PresentationRepository presentationRepository) {
        this.presentationRepository = presentationRepository;
    }

    public Presentation findById(Long id) {
        return presentationRepository.getOne(id);
    }

    public List<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public Page<Presentation> findAllBySpeaker(Long speakerId, Pageable pageable) {
        Page<Presentation> pageOfPresentations = presentationRepository.findAllBySpeakerId(speakerId, pageable);

        return pageOfPresentations;
    }



    public Presentation savePresentation(Presentation presentation) {
        return presentationRepository.save(presentation);
    }

    public void deletePresentation(Long id) {
        presentationRepository.deleteById(id);
    }

}
