package akka

import akka.MusicController.{Play, Stop}
import akka.MusicPlayer.{StartMusic, StopMusic}
import akka.actor.{Actor, ActorSystem, Props}

object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props = Props[MusicController]

}

class MusicController extends Actor{

  override def receive= {
    case Play => println("Music started...")
    case Stop => println("Music started...")
  }
}

object MusicPlayer{
  sealed trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StartMusic extends PlayMsg


}

class MusicPlayer extends Actor {

  override def receive = {
    case StopMusic =>
      println("I dont wanna stop the music")
    case StartMusic =>
      val controller = context.actorOf(MusicController.props, "controller")
      controller ! Play
    case _ => println("Unknown message")
  }

}

object Creation extends App {

  val system = ActorSystem("creation")

  val player = system.actorOf(Props[MusicPlayer], "player")

  player ! StartMusic

  system.terminate()

}
