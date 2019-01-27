package com.schmueckers.tuple_engine

/**
  * A wrapper that introduces a pre and a post hook to any Framework
  *
  * @param framework
  * @param pre
  * @param post
  */
class HookedRuntime(framework : Framework,
                    pre : (String,Any) => Unit = (x,y) => Unit ,
                    post : (String,Seq[Any]) => Seq[Any] = (x,y)=> y) extends Framework {

  def addOperator(op: Operator, labelOption : Option[String] ): Unit =
    framework.addOperator( new PartialFunction[Any,Seq[Any]] {
      override def isDefinedAt(x: Any): Boolean = op.isDefinedAt(x)

      override def apply(v1: Any): Seq[Any] = {
        val label = labelOption.getOrElse(op.toString)
        pre( label, v1 )
        post(label, op.apply(v1))
      }
    })

  override def addOperator(op: Operator): Unit = addOperator( op, None )

  def addOperator( label:String) (op: Operator ) : Unit =
    addOperator( op, Some(label) )

  def process(msg: Any): Unit = framework.process(msg)
}