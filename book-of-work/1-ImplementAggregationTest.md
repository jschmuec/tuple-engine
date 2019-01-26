# 1-ImplementAggregationTest
Implement a test that checks if a stateful component is really possible. This should really be something like a fold operation.

## Example - Summation
```scala
case class Sum( sum : Int)

object RT extends SimpleRuntime {
    fold(0){
        case( sum : Int, add : Int ) =>
            (sum + add, Seq(Sum(sum+add)))
    }
    addOperator {
        case Sum(s) =>
            println(s)
    }
}
```

If you now push 1 and 2 in there:

```scala
> RT.process(1)
1
> RT.process(2)
3
> RT.process(3)
```

## Status
Done