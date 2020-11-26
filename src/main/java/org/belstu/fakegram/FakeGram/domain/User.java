package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String surname;
    private String email;
    @Column(name = "user_description")
    private String userDescription;
    private String login;
    private String password;
    private String role;
    private String status;
    @Column(name="photo_url")
    private String photoUrl;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Mark> marks;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Claim> claims;
    @ManyToMany
    @JoinTable(
            name="user_subscriptions",
            joinColumns = {@JoinColumn(name="subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name="channel_id")}
    )
    private Set<User> subscribers=new HashSet<>();
    @ManyToMany
    @JoinTable(
            name="user_subscriptions",
            joinColumns = {@JoinColumn(name="channel_id")},
            inverseJoinColumns = {@JoinColumn(name="subscriber_id")}
    )
    private Set<User> subscriptions=new HashSet<>();

}

