package com.wallace.demo.rest.sever.demo.services.app.common

import spray.json._

/**
  * Created by 10192057 on 2016/6/17.
  */
case class Person(name: List[String], age: List[Int])

case class NewPerson(name: String, age: Int)


object NewPersonProtocol extends DefaultJsonProtocol {

  implicit object NewPersonFormat extends RootJsonFormat[NewPerson] {
    override def write(person: NewPerson): JsValue = JsObject("name" -> JsString(person.name), "age" -> JsNumber(person.age))

    override def read(json: JsValue): NewPerson = json match {
      case JsArray(Vector(JsString(name), JsNumber(age))) => NewPerson(name, age.toInt)
      case _ => deserializationError("Person expected")
    }
  }

}

object JsonTestDemo {
  def main(args: Array[String]): Unit = {
    import NewPersonProtocol._
    val newP = NewPerson("wallace", 23)
    val json = newP.toJson
    println(json.prettyPrint)
  }
}
