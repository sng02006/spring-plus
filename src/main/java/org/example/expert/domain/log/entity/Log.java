package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long todoId;
    private Long managerUserId;
    private LocalDateTime createdAt;

    public Log(Long userId, Long todoId, Long managerUserId, LocalDateTime createdAt) {
        this.userId = userId;
        this.todoId = todoId;
        this.managerUserId = managerUserId;
        this.createdAt = createdAt;
    }
}