package org.apohl.voteappserver.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ChoiceRepositoryTest {

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private VoteRepository voteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testWhenSave_noError() {

        Vote vote = Vote.builder().title("a title").description("a description").build();
        voteRepository.save(vote);
        entityManager.flush();

        Choice choice = Choice.builder()
                .title("yes")
                .count(1)
                .vote(vote)
                .build();
        choiceRepository.save(choice);
        entityManager.flush();
        entityManager.refresh(vote);

        assertTrue(choice.getId() > 0);
        final Set<Choice> choices = vote.getChoices();
        assertEquals(choices.size(), 1);
        final Choice choice1 = choices.iterator().next();
        assertEquals(choice1.getTitle(), "yes");
        assertEquals(choice1.getCount(), 1);
    }
}
