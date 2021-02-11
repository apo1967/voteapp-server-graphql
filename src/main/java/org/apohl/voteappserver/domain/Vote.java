package org.apohl.voteappserver.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * From GraphQL schema:
 * type Vote {
 * id: ID!
 * """The readable title of this Vote"""
 * title: String!
 * <p>
 * """A meaningful description"""
 * description: String!
 * <p>
 * """Sum of all votes that have been made for all choices"""
 * totalCount: Int!
 * <p>
 * """All choices of this vote"""
 * choices: [Choice!]!
 * <p>
 * """Return the specified Choice or null if not found"""
 * choice(choiceId: ID!): Choice
 * }
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
// we do insert some initial data from a sql file, so we have to adjust our initial id value for inserts
@SequenceGenerator(name = "seq_vote", initialValue = 100)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vote")
    private int id;

    private String title;

    private String description;

    @Transient
    private int totalCount;

    @OneToMany(mappedBy = "vote", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Choice> choices = new HashSet<>();

    public Vote() {
    }

    @PostLoad
    public void postLoad() {
        totalCount = choices.stream().mapToInt(Choice::getCount).sum();
    }

    public Choice getChoice(int id) {
        return choices.stream().filter(choice -> choice.getId() == id).findFirst().orElse(null);
    }

    public void addChoice(String title) {
        // choices might be null if instance was created with Lombok Builder
        if (choices == null) {
            this.choices = new HashSet<>();
        }
        this.choices.add(Choice.builder().title(title).vote(this).build());
    }
}
