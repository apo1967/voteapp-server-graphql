package org.apohl.voteappserver.graphql.resolver;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class NewVote {

    private String title;

    private String description;

    private List<String> choices = new LinkedList<>();
}
