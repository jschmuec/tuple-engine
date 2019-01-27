package com.schmueckers.tuple_engine

import org.scalatest.{FunSuite, GivenWhenThen, Matchers}

class TestHookedRuntime extends FunSuite with Matchers with GivenWhenThen {
  test("the pre-hook is called correctly") {
    var result = List.empty[Any]

    val o1 = "Any"
    val o2 = "Int"

    object RT extends HookedRuntime(new SimpleRuntime, pre = (op, msg) => result = (op, msg) :: result) {
      addOperator(o1) {
        case _: Any => Nil
      }
      addOperator(o2) {
        case i: Int => Nil
      }
    }
    RT.process("Hello world")
    result should be(List((o1, "Hello world")))
    RT.process(1)
    result.reverse should be(List((o1, "Hello world"), (o1, 1), (o2, 1)))
  }
  test("the post hook is called correctly") {
    var result = List.empty[Any]

    val o1 = "int to String"

    object RT extends HookedRuntime(new SimpleRuntime, post = (label, returns) => {
      result = (label, returns) :: result
      returns
    }) {
      addOperator(o1) {
        case i: Int => List(i.toString)
      }
    }

    RT.process(1)
    result should be (List((o1,List("1"))))
  }
  test("the pre hook is called before the post hook") {
    var result = List.empty[Any]

    val calling = "calling"
    val returning = "returning"
    val o1 = "int to String"
    object RT extends HookedRuntime( new SimpleRuntime, pre = (label,in) => result = (calling,label,in) :: result,
      post = (label,out) => { result = ("returning",label,out) :: result; out}) {
      addOperator(o1){
        case i : Int => List(i.toString)
      }
    }
    RT.process( 2)
    result.reverse should be (List((calling,o1,2),(returning,o1,List("2"))))
  }
}