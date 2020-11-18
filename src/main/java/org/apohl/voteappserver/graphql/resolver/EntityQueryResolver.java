package org.apohl.voteappserver.graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.apohl.voteappserver.domain.Vote;
import org.apohl.voteappserver.domain.VoteRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Resolves queries from the GraphQL schema, such as
 *
 * <pre>
 * type Query {
 *    ping(msg: String): String!
 *    votes: [Vote!]!
 *    voteById(id: ID!): Vote
 * }
 * </pre>
 */
@Component
public class EntityQueryResolver implements GraphQLQueryResolver {

    private VoteRepository voteRepository;

    public EntityQueryResolver(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    /**
     * @param msg the message to be returned to the pinger if not <code>null</code>
     * @return a String to prove the instance is up and running
     */
    public String getPing(String msg) {
        return (StringUtils.isEmpty(msg) ? "up and running!" : "You say '" + msg + "' - I say 'what?'");
    }

    /**
     * @return all {@link Vote}s
     */
    public List<Vote> getVotes() {
        return voteRepository.findAll();
    }

    /**
     * @param id the id of the {@link Vote}
     * @return the {@link Vote} or <code>null</code> if no such {@link Vote} exists
     */
    public Vote getVoteById(int id) {
        try {
            Optional<Vote> vote = voteRepository.findById(id);
            if (vote.isPresent()) {
                return vote.get();
            }
        } catch (Exception e) {
            // todo: handle error
        }
        return null;
    }
}
