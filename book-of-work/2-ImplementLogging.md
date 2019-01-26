# 2-ImplementLogging

It can be difficult to see what a non explicitely programmed system does. That's why I'm adding a generic logging solution. The first thing I'm adding is a capability to add a *label* to each operator.

## Tests
It's hard to test logging, so I put a method in the middle which can then be used to call any logging framework you like.