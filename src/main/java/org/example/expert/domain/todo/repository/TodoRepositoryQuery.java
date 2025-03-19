package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoWithConditionResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepositoryQuery {
    Optional<Todo> findByIdWithUser(Long todoId);
    Page<TodoWithConditionResponse> findAllByCondition(Pageable pageable, String title, String nickname, LocalDateTime startDate, LocalDateTime endDate);
}
