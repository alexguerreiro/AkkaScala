package akka

import akka.actor.{ActorSystem, PoisonPill, Props}

object ActorPath extends App {

  val system = ActorSystem("Actor-Paths")

  val counter1 = system.actorOf(Props[Counter], "Counter")

  println(s"Actor reference for counter1: $counter1")

  val counterSelection1 = system.actorSelection("counter")

  println(s"Actor selection for counter1: $counterSelection1")

  counter1 ! PoisonPill

  Thread.sleep(100)

  val counter2 = system.actorOf(Props[Counter], "Counter")

  println(s"Actor reference for counter2: $counter2")

  val counterSelection2 = system.actorSelection("counter")

  println(s"Actor selection for counter2: $counterSelection2")

  system.terminate()
}
