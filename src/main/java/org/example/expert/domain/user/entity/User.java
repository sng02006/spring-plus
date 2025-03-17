package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String nickname;

    public User(String email, String password, UserRole userRole, String nickname) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    public User(Long id, String email, UserRole userRole, String nickname) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    public static User fromAuthUser(AuthUser authUser) {
        UserRole userRole = authUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("Invalid role"));

        return new User(authUser.getId(), authUser.getEmail(), userRole, authUser.getNickname());
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
