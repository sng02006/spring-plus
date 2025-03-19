package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
public class TodoWithConditionResponse {

    private final String title;
    private final Long numberOfManagers;
    private final Long numberOfComments;

    public TodoWithConditionResponse(String title, Long numberOfManagers, Long numberOfComments) {
        this.title = title;
        this.numberOfManagers = numberOfManagers;
        this.numberOfComments = numberOfComments;
    }
}