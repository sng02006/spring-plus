package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuery{

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u WHERE t.modifiedAt > :startDate AND t.modifiedAt < :endDate ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByDate(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u WHERE t.weather = :weather AND t.modifiedAt > :startDate AND t.modifiedAt < :endDate ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByWeatherAndDate(Pageable pageable, String weather, LocalDateTime startDate, LocalDateTime endDate);

//    @Query("SELECT t FROM Todo t " +
//            "LEFT JOIN t.user " +
//            "WHERE t.id = :todoId")
//    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
