package akka.routing

import akka.actor.{ActorSystem, Props}
import akka.routing.Worker.Work

object RouterApp extends App {

  val system = ActorSystem("router")

  val router = system.actorOf(Props[Router])

  router ! Work()
  router ! Work()
  router ! Work()

  Thread.sleep(100)

  system.terminate()
}
