package com.poliakov.springbootconference.repository;

import com.poliakov.springbootconference.model.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    String query = "SELECT presentation.*\n" +
            "FROM presentation\n" +
            "LEFT JOIN conference ON presentation.conference_id = conference.id\n" +
            "WHERE presentation.speaker_id= :speakerId\n" +
            "ORDER BY conference.date DESC";

    String countQuery = "SELECT COUNT(*)\n" +
            "FROM presentation\n" +
            "WHERE presentation.speaker_id = :speakerId";

    @Query(nativeQuery = true, value = query, countQuery = countQuery)
    Page<Presentation> findAllBySpeakerId(Long speakerId, Pageable pageable);
}
