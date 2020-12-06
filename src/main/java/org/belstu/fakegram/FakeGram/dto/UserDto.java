package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String aboutMe;
    private String login;
    private String password;
    private String role;
    private String status;
    private String photoUrl;
    private int CountOfSubscribers;
    private int CountOfSubscribtions;
    private int countOfPosts;
    private boolean subscribed;
}
