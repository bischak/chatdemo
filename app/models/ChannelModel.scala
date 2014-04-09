package models

import scala.collection.mutable

import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.iteratee.{Concurrent, Enumerator}

object ChannelModel {

  protected var channels: mutable.HashMap[String, (Enumerator[String], Channel[String])] = mutable.HashMap()

  private def find(channelName: String) = {
    channels.get(channelName).getOrElse {
      val t: (Enumerator[String], Channel[String]) = Concurrent.broadcast[String]
      channels.put(channelName, t)
      t
    }
  }

  def getChannel(channelName: String): Enumerator[String] = {
    find(channelName)._1
  }

  def send(channelName: String, message: String) = {
    find(channelName)._2.push(message)
  }


}
