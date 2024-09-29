const { ApolloServer } = require('@apollo/server');
const { startStandaloneServer } = require('@apollo/server/standalone');
const {ApolloGateway, IntrospectAndCompose} = require('@apollo/gateway')

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
        subgraphs: [
            { name: 'accounts', url: 'http://localhost:8080/graphql' },
            { name: 'transactions', url: 'http://localhost:8081/graphql' },
        ]
    })
});

const server = new ApolloServer({ gateway, subscriptions:false, tracing:true });

startStandaloneServer(server).then(({url}) => {
  console.log(`🚀  Server ready at ${url}`);
});
