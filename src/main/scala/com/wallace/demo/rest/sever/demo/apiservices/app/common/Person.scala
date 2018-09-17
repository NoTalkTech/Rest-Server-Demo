package com.wallace.demo.rest.sever.demo.apiservices.app.common

import org.json4s.JsonAST.{JField, JInt, JObject, JString}
import org.json4s.native.Serialization
import org.json4s.{CustomSerializer, Formats, NoTypeHints}
import spray.json._

/**
  * Created by 10192057 on 2016/6/17.
  */
case class Person(name: List[String], age: List[Int])

case class NewPerson(name: String, age: Int)

object NewPersonProtocol extends DefaultJsonProtocol {

  implicit object myPersonFormat extends RootJsonFormat[NewPerson] {
    override def write(person: NewPerson): JsValue = JsObject("name" -> JsString(person.name), "age" -> JsNumber(person.age))

    override def read(json: JsValue): NewPerson = json match {
      case JsArray(Vector(JsString(name), JsNumber(age))) => NewPerson(name, age.toInt)
      case _ => throw new Error("Person expected")
    }
  }


  class NewPersonSerializer extends CustomSerializer[NewPerson](format => ( {
    case JObject(JField("name", JString(s)) :: JField("age", JInt(e)) :: Nil) =>
      NewPerson(s, e.intValue())
  }, {
    case x: NewPerson =>
      JObject(JField("name", JString(x.name)) ::
        JField("age", JInt(x.age)) :: Nil)
  }))

  implicit val formats: Formats = Serialization.formats(NoTypeHints) + new NewPersonSerializer
}

object JsonTestDemo {
  def main(args: Array[String]): Unit = {
    import com.wallace.demo.rest.sever.demo.apiservices.app.common.NewPersonProtocol._
    val test = NewPerson("wallace", 12)
    println(test.toJson.prettyPrint)
  }
}
