package org.apohl.voteappserver.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testWhenSave_NoError() {

        Vote vote = Vote.builder().title("a title").description("a description").build();
        vote.addChoice("a choice");
        voteRepository.save(vote);
        entityManager.flush();

        assertTrue(vote.getId() > 0);

        final Vote foundVote = voteRepository.findById(vote.getId()).get();
        assertEquals(foundVote.getTitle(), vote.getTitle());
    }
}
