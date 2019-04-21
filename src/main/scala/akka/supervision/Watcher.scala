package akka.supervision

import akka.actor.{Actor, ActorIdentity, ActorRef, Identify}

class Watcher extends Actor {

  var counterRef: ActorRef =_

  val selection = context.actorSelection("/user/counter")

  selection ! Identify(None)

  def receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor reference for counter is ${ref}")
    case ActorIdentity(_, None) =>
      println(s"Actor reference for counter is not live")
  }
}