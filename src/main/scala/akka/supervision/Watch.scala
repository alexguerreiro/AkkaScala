package akka.supervision

import akka.Counter
import akka.actor.{ActorSystem, Props}

object Watch extends App {
  val system = ActorSystem("Watch-actor-selection")

  val counter = system.actorOf(Props[Counter], "counter")

  val watcher = system.actorOf(Props[Watcher], "watcher")

  Thread.sleep(1000)

  system.terminate()
}
