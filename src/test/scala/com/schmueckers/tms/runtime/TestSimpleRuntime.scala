package com.schmueckers.tms.runtime

import com.schmueckers.tms.runtime.runtime.SimpleRuntime
import org.scalatest.{FunSuite, GivenWhenThen, Matchers}

class TestSimpleRuntime extends FunSuite with Matchers with GivenWhenThen {
  test("operator gets called on msg") {
    var result = List.empty[Any]
    object RT extends SimpleRuntime {
      addOperator( {
        case x =>
          result = x :: result
          Nil
      } )
    }
    RT.process("Hello world" )
    result should be (List("Hello world"))
  }
  test( "chained operator gets called on msg" ) {
    var result = List.empty[Any]
    object RT extends SimpleRuntime {
      addOperator( {
        case i : Int =>
          Seq( i.toString)
      })
      addOperator( {
        case s : String =>
          result = s :: result
          Nil
      })


    }
    RT.process(1)
    result should be (List("1"))
  }
}
