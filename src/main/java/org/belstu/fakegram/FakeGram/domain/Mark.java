package org.belstu.fakegram.FakeGram.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="like_or_dislike")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    @NonNull
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @NonNull
    private User author;
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private TypeOfVote typeOfVote;
    @NonNull
    private Date date;

}
