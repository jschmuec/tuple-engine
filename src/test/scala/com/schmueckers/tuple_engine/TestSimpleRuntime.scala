package com.schmueckers.tuple_engine

import com.schmueckers.tuple_engine.runtime.SimpleRuntime
import org.scalatest.{FunSuite, GivenWhenThen, Matchers}

class TestSimpleRuntime extends FunSuite with Matchers with GivenWhenThen {
  test("operator gets called on msg") {
    var result = List.empty[Any]
    object RT extends SimpleRuntime {
      addOperator({
        case x =>
          result = x :: result
          Nil
      })
    }
    RT.process("Hello world")
    result should be(List("Hello world"))
  }
  test("chained operator gets called on msg") {
    var result = List.empty[Any]
    object RT extends SimpleRuntime {
      addOperator({
        case i: Int =>
          Seq(i.toString)
      })
      addOperator({
        case s: String =>
          result = s :: result
          Nil
      })


    }
    RT.process(1)
    result should be(List("1"))
  }
  test("fold operation aggregates numbers") {
    case class Sum(s: Int)
    var result = List.empty[Any]
    object RT extends SimpleRuntime {
      addOperator({
        case Sum(i) => i :: result
      })
      fold(0)({
        case(s:Int,i:Int) =>

          (s+i, List(Sum(s + i)))
      })
      RT.process(1)
      result should be (List(Sum(1)))
      RT.process( 2)
      result.reverse should be (List(Sum(1),Sum(3)))
      RT.process( 3 )
      result.head should be (Sum(6))
    }
  }
}
