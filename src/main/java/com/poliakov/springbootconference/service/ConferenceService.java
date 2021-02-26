package com.poliakov.springbootconference.service;

import com.poliakov.springbootconference.dto.ConferenceSearchFilters;
import com.poliakov.springbootconference.model.Conference;
import com.poliakov.springbootconference.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

    private ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference findById(Long id) {
        return conferenceRepository.getOne(id);
    }

    public Page<Conference> findAll(Pageable pageable) {
        return conferenceRepository.findAllByOrderByDateDesc(pageable);
    }

    public Page<Conference> findAll(ConferenceSearchFilters filters, Pageable pageable) throws Exception {
        switch (filters.getOrderby()) {
            case DateAsc:
                return conferenceRepository.findAllOrderByDateAsc(filters.isShowPast(), filters.isShowFuture(), pageable);
            case DateDesc:
                return conferenceRepository.findAllOrderByDateDesc(filters.isShowPast(), filters.isShowFuture(), pageable);
            case ParticipantsAsc:
                return conferenceRepository.findAllOrderByParticipantsAsc(filters.isShowPast(), filters.isShowFuture(), pageable);
            case ParticipantsDesc:
                return conferenceRepository.findAllOrderByParticipantsDesc(filters.isShowPast(), filters.isShowFuture(), pageable);
            case PresentationsAsc:
                return conferenceRepository.findAllOrderByPresentationsAsc(filters.isShowPast(), filters.isShowFuture(), pageable);
            case PresentationsDesc:
                return conferenceRepository.findAllOrderByPresentationsDesc(filters.isShowPast(), filters.isShowFuture(), pageable);
            default:
                throw new Exception("Invalid sort type: " + filters.getOrderby());
        }

    }

    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public void deleteConference(Long id) {
        conferenceRepository.deleteById(id);
    }

    public void updateConference(Conference conference) {
        conferenceRepository.save(conference);
    }
}
