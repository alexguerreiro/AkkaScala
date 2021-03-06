package akka.routing

import akka.actor.{ActorSystem, Props}
import akka.routing.Worker.Work

object Random extends App {

  val system = ActorSystem("Random-Router")

  val routerPool = system.actorOf(FromConfig.props(Props[Worker]), "random-router-pool")

  routerPool ! Work()

  routerPool ! Work()

  routerPool ! Work()

  Thread.sleep(100)

  system.terminate()

}
