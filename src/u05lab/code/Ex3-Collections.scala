package u05lab.code

import u05lab.code.PerformanceUtils.measure

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object PerformanceUtils {
  case class MeasurementResults[T](result: T, duration: FiniteDuration) extends Ordered[MeasurementResults[_]] {
    override def compare(that: MeasurementResults[_]): Int = duration.toNanos.compareTo(that.duration.toNanos)
  }

  def measure[T](msg: String)(expr: => T): MeasurementResults[T] = {
    val startTime = System.nanoTime()
    val res = expr
    val duration = FiniteDuration(System.nanoTime()-startTime, TimeUnit.NANOSECONDS)
    if(!msg.isEmpty) println(msg + " -- " + duration.toNanos + " nanos; " + duration.toMillis + "ms")
    MeasurementResults(res, duration)
  }

  def measure[T](expr: => T): MeasurementResults[T] = measure("")(expr)
}


object CollectionsTest extends App {

  /* Linear sequences: List, ListBuffer */
  var lst = (1 to 1000000).toList

  /* Indexed sequences: Vector, Array, ArrayBuffer */
  var vec = (1 to 1000000).toVector

  /* Sets */
  var set = (1 to 1000000).toSet

  /* Maps */
  var map = (1 to 1000000).map({e => (e, e + 10)}).toMap

  /* Seqs */
  var seq = (1 to 1000000)
  measure("seq last"){ seq.last }

  /* Comparison */
  import PerformanceUtils._

  assert( measure("lst last"){ lst.last } > measure("vec last"){ vec.last } )
  assert( measure("map last"){ map.last } > measure("set last"){ set.last } )

  println()

  measure("seq filter"){ seq.filter(i => i < 1000) }
  measure("lst filter"){ lst.filter(i => i < 1000) }
  measure("vec filter"){ vec.filter(i => i < 1000) }
  measure("map filter"){ map.filter(p => p._1 < 1000) }
  measure("set filter"){ set.filter(i => i < 1000) }

  println()

  measure("seq count"){ seq.count(e => e % 2 == 0)}
  measure("lst count"){ lst.count(e => e % 2 == 0) }
  measure("vec count"){ vec.count(e => e % 2 == 0) }
  measure("map count v1"){ map.count(e => e._1 % 2 == 0)}
  measure("map count v2"){ map.count(e => e._1 > e._2)}
  measure("set count"){ set.count(e => e % 2 == 0) }

  println()

  measure("seq map"){ seq.map(e => e % 2 == 0) }
  measure("lst map"){ lst.map(e => e % 2 == 0) }
  measure("vec map"){ vec.map(e => e % 2 == 0) }
  measure("map map"){ map.map(e => (e._1, e._1 % 2 == 0)) }
  measure("set map"){ set.map(e => e % 2 == 0) }

}