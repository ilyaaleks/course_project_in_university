package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@AllArgsConstructor
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @NonNull
    private String lastName;
    @NonNull
    private String name;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @Column(name = "user_description")
    @NonNull
    private String userDescription;
    @NonNull
    private boolean activate;
    private String role;
    private String activationCode;
    @NonNull
    private String status;
    @Column(name="photo_url")
    @NonNull
    private String photoUrl;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Mark> marks;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Post> posts;
    @ManyToMany
    @JoinTable(
            name="user_subscriptions",
            joinColumns = {@JoinColumn(name="subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name="channel_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> subscribers=new HashSet<>();
    @ManyToMany
    @JoinTable(
            name="user_subscriptions",
            joinColumns = {@JoinColumn(name="channel_id")},
            inverseJoinColumns = {@JoinColumn(name="subscriber_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> subscriptions=new HashSet<>();

}

