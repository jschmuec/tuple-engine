package com.schmueckers.tuple_engine

package object runtime {
  type Operator = PartialFunction[Any, Seq[Any]]

  trait Framework {


    def toFold[S](init:S)(foldFkt: PartialFunction[(S, Any), (S, Seq[Any])]): Operator = {
      var state = init
      new PartialFunction[Any, Seq[Any]] {
        def isDefinedAt(x: Any) = foldFkt.isDefinedAt((state, x))

        def apply(x: Any) = {
          val (ns, events) = foldFkt((state, x))
          state = ns
          events
        }
      }
    }

    //   def addOperator[S,E](foldFkt:PartialFunction[(S,Any),(S,Seq[Any])])(init :S) : Unit
    def addOperator(op: Operator): Unit

    def fold[S](init:S)(foldFkt: PartialFunction[(S, Any), (S, Seq[Any])]): Unit =
      addOperator(toFold(init)(foldFkt))
  }

  class SimpleRuntime extends Framework {
    var operators = Vector.empty[Operator]

    override def addOperator(operator: Operator) =
      operators = operators.+:(operator)

    def process(msg: Any): Unit =
      operators.reverse.flatMap((op: Operator) => op.applyOrElse(msg, (m: Any) => Nil)).map(process(_))
  }

}
