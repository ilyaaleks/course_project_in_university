package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
@AllArgsConstructor
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @NonNull
    private User author;
    @NonNull
    private String text;
    @NonNull
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id")
    @NonNull
    private Post postComment;
}
