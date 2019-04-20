package akka

import akka.Checker.{BlackUser, CheckUser, WhiteUser}
import akka.Recorder.NewUser
import akka.Storage.AddUser
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.language.postfixOps

case class User(username: String, email: String)

object Recorder {
  sealed trait RecorderMsg
  case class NewUser(user: User) extends RecorderMsg

  def props(checker: ActorRef, storage: ActorRef) =
    Props(new Recorder(checker, storage))
}

object Checker {
  sealed trait CheckerMsg
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponse
  case class BlackUser(user: User) extends CheckerMsg
  case class WhiteUser(user: User) extends CheckerMsg

}

object Storage {
  sealed trait StoreMsg
  case class AddUser(user: User) extends StoreMsg
}

class Storage extends Actor {

  var users = List.empty[User]

  override def receive = {
    case AddUser(user) =>
      println(s"Storage: $user added")
      users = user :: users
  }
}

class Checker extends Actor {
  val blacklist = List(
    User("Adam", "adam@mail.com")
  )

  def receive = {
    case CheckUser(user) if blacklist.contains(user) =>
      println(s"Checker: $user in the blacklist")
      sender() ! BlackUser(user)
    case CheckUser(user) =>
      println(s"Checker: $user not in the blacklist")
      sender() ! WhiteUser(user)
  }
}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case NewUser(user) =>
      checker ? CheckUser(user) map {
        case WhiteUser(user) =>
          storage ! AddUser(user)
        case BlackUser(user) =>
          println(s"Recorder: $user in the blacklist")
      }
  }

}

/**
  * Recorder Actor sends users to be checked by the Checker Actor
  * Checker actor checks whether the user is in the blacklist or not and return to Recorder
  * if user is not in the black list, the user is sent to the Storage actor to be stored in the list
  */
object TalkToActor extends App {

  // Create the 'talk-to-actor' actor system
  val system = ActorSystem("talk-to-actor")

  // Create the 'checker' actor
  val checker = system.actorOf(Props[Checker], "checker")

  // Create the 'storage' actor
  val storage = system.actorOf(Props[Storage], "storage")

  // Create the 'recorder' actor
  val recorder = system.actorOf(Recorder.props(checker, storage), "recorder")

  //send NewUser Message to Recorder
  recorder ! Recorder.NewUser(User("Jon", "jon@packt.com"))

  recorder ! Recorder.NewUser(User("Adam", "adam@mail.com"))

  Thread.sleep(100)

  //shutdown system
  system.terminate()

}

