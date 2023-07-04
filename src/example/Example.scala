package example

import protos.touse.person.Person

import java.util.concurrent.ThreadLocalRandom
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object Example extends App {
  private val names = Seq("bill", "sherry", "terry", "jerry")
  private val random: ThreadLocalRandom = ThreadLocalRandom.current()

  private val people = names.map(new Person(_, random.nextInt(1, 100)))

  people.foreach { person =>
    println(s"${person.name} was created! at age ${person.age}")
  }

  private val service = new KillService

  println("evaluating people...")

  Thread.sleep(200)

  people.foreach(p => {
    def tellResult(p: Person, isDead: Boolean): Unit = {
      val status = if (isDead) "has died" else "is alive"
      println(s"${p.name} $status at age ${p.age}")
    }
    service.killT(p).onComplete {
      case Success(status) => tellResult(p, status.isDead)
      case _ => println(s"failed to evaluate ${p.name}")
    }
  })
}