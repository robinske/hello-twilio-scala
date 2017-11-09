package me.krobinson.twilio

import com.twilio.Twilio
import com.twilio.`type`.PhoneNumber
import com.typesafe.config.{Config, ConfigFactory}
import com.twilio.rest.api.v2010.account.Message

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}


object Main extends App {

  def initializeTwilio(conf: Config): Unit = {
    val ACCOUNT_SID = conf.getString("twilio.account_sid")
    val AUTH_TOKEN = conf.getString("twilio.auth_token")

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
  }

  val conf = ConfigFactory.load()
  initializeTwilio(conf)

  val from = new PhoneNumber(conf.getString("twilio.from_number"))
  val to = new PhoneNumber(readLine("Who are we texting? "))

  val defaultMessageBody =
    "Do you wanna do something fun? Wanna go to Taco Bell? \uD83C\uDF2E"

  val body: String = readLine("What do you want to say? ") match {
    case m if m.isEmpty => defaultMessageBody
    case m              => m
  }

  Try(Message.creator(to, from, body).create()) match {
    case Success(m) => println(s"Message sent to $to with ID ${m.getSid}")
    case Failure(e) => println(s"Encountered an exception: \n${e.getMessage}")
  }

}