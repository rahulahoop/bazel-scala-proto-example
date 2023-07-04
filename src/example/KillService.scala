package example

import protos.touse.person.{Person, PersonStatus}
import protos.touse.person.PersonServiceGrpc.PersonService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class KillService extends PersonService {
  override def killT(request: Person): Future[PersonStatus] = Future {
    val isDead = request.age > 77 || request.name.equalsIgnoreCase("bill")
    PersonStatus(isDead)
  }
}
