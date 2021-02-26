package com.poliakov.springbootconference.repository;

import com.poliakov.springbootconference.model.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConferenceRepository extends JpaRepository <Conference, Long> {
    String query = "SELECT\n" +
            "            c.*\n" +
            "            FROM\n" +
            "            Conference c\n" +
            "            LEFT JOIN (\n" +
            "            SELECT\n" +
            "            p.conference_id,\n" +
            "            COUNT(*) as presentations\n" +
            "            FROM\n" +
            "            Presentation p\n" +
            "            WHERE presentation_approved = true\n" +
            "            GROUP BY\n" +
            "            p.conference_id\n" +
            "            ) presentations_counts ON presentations_counts.conference_id = c.id\n" +
            "            LEFT JOIN (\n" +
            "            SELECT\n" +
            "            cp.conference_id,\n" +
            "            COUNT(*) as participants\n" +
            "            FROM\n" +
            "            Conference_participants cp\n" +
            "            GROUP BY\n" +
            "            cp.conference_id\n" +
            "            ) participants_counts ON participants_counts.conference_id = c.id\n" +
            "            WHERE\n" +
            "            (\n" +
            "            :showPast\n" +
            "            OR c.date >= CURDATE()\n" +
            "            )\n" +
            "            AND (\n" +
            "            :showFuture\n" +
            "            OR c.date <= CURDATE()\n" +
            "            )\n ORDER BY ";

    String countQuery = "SELECT COUNT(*)\n" +
            "FROM Conference c\n" +
            "WHERE (:showPast OR c.date >= CURDATE()) AND (:showFuture OR c.date <= CURDATE())";

    @Query(nativeQuery = true, value = query + "Date DESC", countQuery = countQuery)
    Page<Conference> findAllOrderByDateDesc(boolean showPast, boolean showFuture, Pageable pageable);
    @Query(nativeQuery = true, value = query + "Date ASC", countQuery = countQuery)
    Page<Conference> findAllOrderByDateAsc(boolean showPast, boolean showFuture, Pageable pageable);
    @Query(nativeQuery = true, value = query + "participants_counts.participants DESC", countQuery = countQuery)
    Page<Conference> findAllOrderByParticipantsDesc(boolean showPast, boolean showFuture, Pageable pageable);
    @Query(nativeQuery = true, value = query + "participants_counts.participants ASC", countQuery = countQuery)
    Page<Conference> findAllOrderByParticipantsAsc(boolean showPast, boolean showFuture, Pageable pageable);
    @Query(nativeQuery = true, value = query + "presentations_counts.presentations DESC", countQuery = countQuery)
    Page<Conference> findAllOrderByPresentationsDesc(boolean showPast, boolean showFuture, Pageable pageable);
    @Query(nativeQuery = true, value = query + "presentations_counts.presentations ASC", countQuery = countQuery)
    Page<Conference> findAllOrderByPresentationsAsc(boolean showPast, boolean showFuture, Pageable pageable);

    Page<Conference> findAllByOrderByDateDesc(Pageable pageable);
}
