import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

object PromiseStdIn extends App {

  def applyFromStdIn(lineInputProcessor: Int => Unit): Unit = {
    lineInputProcessor(io.StdIn.readLine().toInt)
  }

//  val future: Future[Int] = Future {
  //    var y: Int = 0
  //    applyFromStdIn({ x: Int => {
  //      y = x * 7
  //      Success(y)
  //    }
  //    })
  //    y
  //  }

  val promise: Promise[Int] = Promise[Int]
  applyFromStdIn((i) => promise.success(i * 7))

  val future: Future[Int] = promise.future

  future onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => throwable.printStackTrace()
  }
  Await.result(future, Duration.Inf)
}
