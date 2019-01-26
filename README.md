# Tuple Engine
A tuple-based execution engine for business logic.

When I write new code, I usually commingle business logic with orchestration and persistence. The tuple engine tries to abstract this away from the business logic by allowing to create partial functions which are then executed on all objects pushed into a big pool that are in the domain of the partial function. All business logic needs therefore be encapsulated in functionality.

Unfortunately, I can't work only on pure functions because I want to be able to aggregate.

## Performance
It's fairly obvious that this might not be the optimal implementation but if they check for domain is reasonabily fast there is really no reason why this would be slower than a manually programmed feeding pipeline.

## Prove of Concept
I'm currently writing a Trade Management System using this framework. Let's see if that works and how it can cope with the challenges arising from this kind of environtment. The project is called TBT for Tuple Based Trade management system.

## Examples
Can be found in the tests:

[Usage of the SimpleRuntime](./src/test/scala/com/schmueckers/tuple_engine/TestSimpleRuntime.scala)

Or in the change logs:

[Usage of fold](./book-of-work/1-ImplementOfAggregationTest.md)