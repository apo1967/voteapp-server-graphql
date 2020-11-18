package org.apohl.voteappserver.graphql.resolver;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.apohl.voteappserver.domain.Vote;
import org.apohl.voteappserver.graphql.publisher.PublisherService;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

/**
 * Resolves subscriptions from  the GraphQl schema such as
 * <pre>
 *  type Subscription {
 *      onNewChoice(voteId: ID): Vote!
 *  }
 * </pre>
 */
@Component
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

    private PublisherService publisherService;

    public SubscriptionResolver(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    Publisher<Vote> onNewChoice(int voteId) {
        return publisherService.getNewChoicePublisher(voteId);
    }
}