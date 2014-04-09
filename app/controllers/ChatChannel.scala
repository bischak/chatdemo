package controllers

import play.api.mvc.{Action, WebSocket, Controller}
import models.ChannelModel
import play.api.libs.iteratee.Iteratee

import play.api.libs.concurrent.Execution.Implicits.defaultContext


object ChatChannel extends Controller {

  def get(name: String) = WebSocket.using[String] {
    request => {
      val in = Iteratee.foreach[String] {
        message => ChannelModel.send(name, message)
      }
      (in, ChannelModel.getChannel(name))
    }
  }

  def send(name: String) = Action {
    request =>
      request.body.asFormUrlEncoded.map {
        f =>
          val user = f.get("user").flatMap(_.headOption)
          val message = f.get("message").flatMap(_.headOption)

          if (user.nonEmpty && message.nonEmpty) {
            ChannelModel.send(name, user.get + ": " + message.get + "\n")
            Ok("[" + user.get + "]: " + message.get)
          } else BadRequest("error")

      }.getOrElse(BadRequest("wrong request"))
  }
}
