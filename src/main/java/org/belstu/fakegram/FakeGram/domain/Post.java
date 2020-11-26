package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="posts")
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User author;
    @Column(name = "photo_path")
    private String photoPath;
    private String text;
    private Date date;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Claim> claims;
    @ManyToMany(mappedBy = "posts",cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<HashTag> hashTags;
    @OneToMany(mappedBy = "postComment",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Comment> comments;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Mark> marks;

}
