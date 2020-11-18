### Spring Boot GraphQL Server Example

This Spring Boot GraphQL server implements the GraphQL schema and underlying model of the original 
Node.js based server of the React example app of the React learning book [React](https://reactbuch.de/) written by [Nils Hartmann](https://nilshartmann.net/) and [Oliver Zeigermann](https://zeigermann.eu/).

The original Node.js based server and the React app that is using it can be found at the [Github repository](https://github.com/reactbuch/vote-example-v2).

#### Configuration

The spring boot app runs at port 4000 and serves the pathes as configured in the React app's Apollo client config.
The entities are persisted in an H2 in-memory database.

#### Notes

This is a quick and dirty self tutorial with no testing included at all. Also GraphiQl is not included, so if you want to
test GraphQL queries, mutations or subscriptions with other than the React client app you have to an external GraphQl tool like the native [GraphiQL app](https://www.electronjs.org/apps/graphiql).  

