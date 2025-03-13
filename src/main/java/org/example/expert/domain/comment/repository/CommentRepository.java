package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId")
    // @EntityGraph(attributePaths = {"user"})
    // @EntityGraph를 사용하는 경우 메서드명에서 WithUser를 지워주어야 동작한다.
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);
}
