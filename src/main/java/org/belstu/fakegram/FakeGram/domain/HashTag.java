package org.belstu.fakegram.FakeGram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="hash_tag")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class HashTag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String text;
    @ManyToMany
    @JoinTable(
            name = "hashtag_table",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Post> posts=new HashSet<>();

}
