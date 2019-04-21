package akka.routing

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.Worker.Work

class Router extends Actor {

  var routees: List[ActorRef] =_

  override def preStart() = {
    routees = List.fill(5) (
      context.actorOf(Props[Worker])
    )
  }

  def receive() = {
    case msg: Work =>
      println("Im a router and I received a message")
      routees(util.Random.nextInt(routees.size)) forward msg
  }
}
