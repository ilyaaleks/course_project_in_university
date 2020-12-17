package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User author;
    @NonNull
    private String text;
    @NonNull
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id")
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Post postComment;
}
