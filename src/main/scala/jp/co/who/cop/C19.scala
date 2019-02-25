package jp.co.who.cop

import scala.collection.immutable.Queue

class C19 extends LogicBase {
  // 情報隠蔽の手法

  override def run: Unit = {
    Common.head("start C19.")
    testQueue
    testCell
  }
  // 19.1
  def testQueue = {
    Common.head("testQueue")
    val q = Queue(1, 2, 3)
    val q1 = q enqueue 4
    println(q.head)
    println(q.tail)
  }

  /*
  class Queue[T] private (
    private val leading: List[T],
    private val trailing: List[T]
  ) {

    private def mirror =
      if (leading.isEmpty) {
        new Queue(trailing.reverse, Nil)
      }
      else
        this
    def head = mirror.leading.head
    def tail = {
      val q = mirror
      new Queue(q.leading.tail, q.trailing)
    }
    def enqueue(x: T) = new Queue(leading, x :: trailing)

    // 19.2
    def this() = this(Nil, Nil)
    def this(elems: T*) = this(elems.toList, Nil)
  }
  */

  // 19.3
  class Cell[T](init: T) {
    private[this] var current = init
    def get = current
    def set(x: T) = { current = x }
  }

  def testCell = {
    Common.head("testCell")
    /*
    val c1 = new Cell[String]("abc")
    val c2: Cell[Any] = c1
    c2.set(1)
    val s: String = c1.get
    println(s)
    */
  }

  // 19.4

  // 19.7
  def testPrivateData = {
    class Queue[+T] private (
      private[this] var leading: List[T],
      private[this] var trailing: List[T]
                            ) {
      private def mirror =
        if (leading.isEmpty) {
          while (!trailing.isEmpty) {
            leading = trailing.head :: leading
            trailing = trailing.tail
          }
        }
      def head: T = {
        mirror
        leading.head
      }
      def tail: Queue[T] = {
        mirror
        new Queue(leading.tail, trailing)
      }
      def enqueue[U >: T](x:U) =
        new Queue[U](leading, x :: trailing)
    }
  }

  // 19.8 上限境界：mixed in しないといけない
  def testUpperBound = {
    def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
      def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
          case (Nil, _) => ys
          case (_, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (x < y) x :: merge(xs, ys1)
            else y :: merge(xs, ys1)
        }
      val n = xs.length / 2
      if (n == 0) xs
      else {
        val (ys, zs) = xs splitAt n
        merge(orderedMergeSort(ys), orderedMergeSort(zs))
      }
    }
  }

}
