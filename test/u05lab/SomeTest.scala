package u05lab

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class SomeTest {

  import u05lab.code._

  val l = List("a", "b", "c")
  val l2 = 10 :: 20 :: 30 :: 40 :: Nil()

  @Test
  def testZipRight() {
    assertEquals(List.nil, List.nil.zipRight)
    assertEquals(List(("a", 0), ("b", 1), ("c", 2)), l.zipRight)
  }

  @Test def testPartition(): Unit = {
    assertEquals((List.nil, List.nil), List.nil[String].partition(_=="a"))
    assertEquals((List("a"), List("b", "c")), l.partition(_=="a"))
  }

  @Test def testSpan(): Unit = {
    assertEquals((List.nil, List(10,20,30,40)), l2.span(_>15))
    assertEquals((List(10), List(20,30,40)), l2.span(_<15))
  }

  @Test def testReduce(): Unit ={
    assertEquals(100, l2.reduce(_+_))
    try { List[Int]().reduce(_+_); assert(false) } catch { case _:UnsupportedOperationException => }
  }

  @Test def testTakeRight(): Unit ={
    assertEquals(List(40), l2.takeRight(1))
    assertEquals(List(30,40), l2.takeRight(2))
  }

  @Test def testCollect(): Unit ={
    //assertEquals(List(9, 39), l2.collect{ case x if x<15 || x>35 => x-1 })
  }
}