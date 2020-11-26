package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    private String text;
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id")
    private Post postComment;
}
