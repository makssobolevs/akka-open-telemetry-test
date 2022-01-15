package com.github.makssobolevs

import akka.actor.Actor
import com.github.makssobolevs.TestActor.Message
import com.github.makssobolevs.TestActor.ReplyMessage

class TestActor extends Actor {
  override def receive: Receive = {
    case Message(x) =>
      Thread.sleep(1000)
      sender() ! ReplyMessage(s"$x - reply")
  }
}

object TestActor {
  case class Message(x: String)
  case class ReplyMessage(x: String)
}
