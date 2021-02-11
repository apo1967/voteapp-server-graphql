package org.apohl.voteappserver.graphql.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.apohl.voteappserver.domain.Choice;
import org.apohl.voteappserver.domain.ChoiceRepository;
import org.apohl.voteappserver.domain.Vote;
import org.apohl.voteappserver.domain.VoteRepository;
import org.apohl.voteappserver.graphql.publisher.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Resolves mutations from the Graphql schema such as
 * <pre>
 * type Mutation {
 *     addVote(newVote: NewVote!): Vote!
 *     registerChoice(choiceId: ID!): Vote!
 * }
 * </pre>
 */
@Component
public class EntityMutationResolver implements GraphQLMutationResolver {

    private VoteRepository voteRepository;

    private ChoiceRepository choiceRepository;

    private PublisherService publisherService;

    public EntityMutationResolver(@Autowired VoteRepository voteRepository, @Autowired ChoiceRepository choiceRepository,
                                  @Autowired PublisherService publisherService) {
        this.voteRepository = voteRepository;
        this.choiceRepository = choiceRepository;
        this.publisherService = publisherService;
    }

    /**
     * @param newVote the input object from the GraphQL mutation that the {@link Vote} and it's {@link Choice}s are
     *                created from
     * @return the saved {@link Vote}
     */
    @Transactional
    public Vote getAddVote(NewVote newVote) {
        Vote vote = Vote.builder().title(newVote.getTitle()).description(newVote.getDescription()).build();
        newVote.getChoices().forEach(choice -> vote.addChoice(choice));
        return voteRepository.save(vote);
    }

    /**
     * Registers a user's vote by increasing the count of votes of the {@link Choice} with the given <code>id</code>
     *
     * @param choiceId the <code>id</code> of the {@link Choice}
     * @return the {@link Choice}s parent {@link Vote}
     */
    @Transactional
    public Vote getRegisterChoice(int choiceId) {
        Optional<Choice> choiceOptional = choiceRepository.findById(choiceId);
        if (choiceOptional.isPresent()) {
            Choice choice = choiceOptional.get();
            choice.setCount(choice.getCount() + 1);
            choiceRepository.save(choice);
            final Vote vote = voteRepository.findById(choice.getVote().getId()).get();
            // todo implement proper error handling
            Assert.notNull(vote, "Vote should never be null");
            publisherService.publishNewChoice(vote);
            return vote;
        }
        return null;
    }
}
