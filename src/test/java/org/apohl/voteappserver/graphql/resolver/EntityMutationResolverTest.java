package org.apohl.voteappserver.graphql.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.apohl.voteappserver.domain.ChoiceRepository;
import org.apohl.voteappserver.domain.Vote;
import org.apohl.voteappserver.domain.VoteRepository;
import org.apohl.voteappserver.graphql.publisher.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link EntityMutationResolver} by executing predefined mutation queries located under
 * <code>/src/test/resources</code>. As <code>@GraphQLTest</code> loads only the minimum required Spring context all
 * beans that the resolver under test needs must be mocked.
 */
@GraphQLTest
class EntityMutationResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private VoteRepository voteRepository;

    @MockBean
    private ChoiceRepository choiceRepository;

    @MockBean
    private PublisherService publisherService;

    @Test
    public void testAddVote() throws IOException {

        when(voteRepository.save(any())).thenReturn(Vote.builder()
                .id(123)
                .description("the description")
                .title("the title")
                .choices(Collections.emptySet())
                .build());

        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/add-vote.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.addVote.id")).isNotNull();
        assertThat(response.get("$.data.addVote.title")).isEqualTo("the title");
    }
}
