package com.schmueckers.tuple_engine

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

  def process( msg : Any ) : Unit
}