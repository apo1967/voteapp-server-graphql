package org.apohl.voteappserver.domain;

import lombok.*;

import javax.persistence.*;

/**
 * From GraphQL schema:
 * type Choice {
 * id: ID!
 * count: Int!
 * <p>
 * """Human readable title of this Choice"""
 * title: String!
 * }
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "seq_choice", initialValue = 100)
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_choice")
    private int id;

    private int count;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    public Choice() {
    }
}
