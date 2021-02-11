package org.apohl.voteappserver.graphql.publisher;

import lombok.extern.slf4j.Slf4j;
import org.apohl.voteappserver.domain.Vote;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages multiple {@link NewChoicePublisher}s
 */
@Slf4j
@Service
public class PublisherService {

    private Map<Integer, NewChoicePublisher> newChoicePublisherMap = new HashMap<>();

    /**
     * Publishes a new choice has made for the given {@link Vote}
     *
     * @param vote the {@link Vote}
     * @return the given {@link Vote}
     */
    public Vote publishNewChoice(@NotNull Vote vote) {
        NewChoicePublisher newChoicePublisher = newChoicePublisherMap.get(vote.getId());
        if (newChoicePublisher == null) {
            return vote;
        }
        newChoicePublisher.publish(vote);
        return vote;
    }

    /**
     * See {@link #getOrCreateNewChoicePublisher(int)}
     *
     * @param voteId
     * @return
     */
    public Publisher<Vote> getNewChoicePublisher(int voteId) {
        return getOrCreateNewChoicePublisher(voteId);
    }

    /**
     * Returns the {@link Publisher} associated with the given {@link Vote} id. Creates a new <code>Publisher</code>
     * if necessary.
     *
     * @param voteId the id of a {@link Vote}
     * @return the {@link Publisher} associated with the given {@link Vote} id
     */
    private Publisher<Vote> getOrCreateNewChoicePublisher(int voteId) {
        NewChoicePublisher newChoicePublisher = newChoicePublisherMap.get(voteId);
        if (newChoicePublisher == null) {
            newChoicePublisher = new NewChoicePublisher();
            newChoicePublisherMap.put(voteId, new NewChoicePublisher());
            log.debug("created NewChoicePublisher for vote id [{}]", voteId);
        }
        return newChoicePublisher.getPublisher();
    }
}
