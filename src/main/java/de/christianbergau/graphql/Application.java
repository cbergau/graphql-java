package de.christianbergau.graphql;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class Application {
    public static void main(String... args) {
        // Schema creation
        String schema = "type Query{hello: String}";
        SchemaParser schemaParser = new SchemaParser();

        // Set of type definitions
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        // Create an executable schema
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // Build GraphQL query
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        // Result of query
        ExecutionResult executionResult = build.execute("{hello}");

        // Print result
        System.out.println(executionResult.getData().toString());
    }
}
