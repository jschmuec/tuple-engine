# 2-ImplementLogging

It can be difficult to see what a non explicitely programmed system does. That's why I'm adding a generic logging solution. Instead of creating a logged solution, I created the [HookedRuntime](../src/main/scala/com/schmueckers/tuple_engine/HookedRuntime.scala)

## Tests
Tests can be found in [TestHookedRuntime](
../src/test/scala/com/schmueckers/tuple_engine/TestHookedRuntime.scala)

## Status
Done

## Description

To implement simple logging, the pre and post hooks can be used to write to the logger:

```scala
object RT extends HookedRuntime( new SimpleRuntime,
    pre = (label,arg) => println(s"called with $label, $arg) ) {
    RT.addOperator( "x" ){
        case i : Int => Nil
    }
}

RT.process(1)
```

Will print `called with x, 1`