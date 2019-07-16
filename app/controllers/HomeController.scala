package controllers

import javax.inject._
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  case class Person(name:String, age:Int)
  object Person {
    implicit val format: OFormat[Person] = Json.format[Person]
  }

  case class RequestData(name: String)
  object RequestData {
    implicit val format: OFormat[RequestData] = Json.format[RequestData]
  }


  val listOfPeople = Seq(
    Person("James", 24)
  )


  def index = Action(parse.json) { implicit request =>
    val data = request.body.as[RequestData]
    if(listOfPeople.map(_.name).contains(data.name))
      Ok(Json.toJson(listOfPeople.filter(_.name == data.name).head))
    else NotFound
  }

}
