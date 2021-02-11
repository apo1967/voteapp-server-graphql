### Spring Boot GraphQL Server Example

This Spring Boot GraphQL server implements the GraphQL schema and underlying model of the original 
Node.js based server of the React example app of the React learning book [React](https://reactbuch.de/) written by [Nils Hartmann](https://nilshartmann.net/) and [Oliver Zeigermann](https://zeigermann.eu/).

The original Node.js based server and the React app that is using it can be found at their [Github repository](https://github.com/reactbuch/vote-example-v2).

#### Configuration

Per default the spring boot app runs at port 4000 and serves the pathes as configured in the React app's Apollo client config - see [application.properties](./src/main/resources/application.properties) 

The entities are persisted in an H2 in-memory database. The project comes with a built in H2-console mapped to "/h2console".

**Please note** that per default the GraphQL servlet is mapped to the root "/" and thus will handle any request, therefore the H2-console is not accessible unless you change the GraphQL mapping to e.g. "/graphql".  

#### Notes

This is a quick and dirty self tutorial with no testing included at all. Also GraphiQl is not included, so if you want to
test GraphQL queries, mutations or subscriptions with other than the React client app you have to use an external GraphQl tool like the native [GraphiQL app](https://www.electronjs.org/apps/graphiql).

#### Docker

You can pull an image of this project from the [docker registry](https://hub.docker.com/repository/docker/apo67/voteapp-server-graphql)  

