package com.schmueckers.tuple_engine

class SimpleRuntime extends Framework {
  var operators = Vector.empty[Operator]

  override def addOperator(operator: Operator) =
    operators = operators.+:(operator)

  def invoke(msg: Any)(op: Operator) =
    op.applyOrElse(msg, (m: Any) => Nil).map(process(_))

  override def process(msg: Any): Unit =
    operators.reverse.flatMap(invoke(msg))
}

