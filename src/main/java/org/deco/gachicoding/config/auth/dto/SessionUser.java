package org.deco.gachicoding.config.auth.dto;

import lombok.Getter;
import org.deco.gachicoding.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getUserName();
        this.email = user.getUserEmail();
    }
}