package controllers

import connectors.MongoConnector
import javax.inject._
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(mongoConnector: MongoConnector, cc: ControllerComponents) extends AbstractController(cc) {
  def index = Action.async(parse.json) { implicit request =>
    val data = request.body.as[RequestData]
    mongoConnector.getAllPeople.map {
      listOfPeople =>
        if(listOfPeople.map(_.name).contains(data.name))
          Ok(Json.toJson(listOfPeople.filter(_.name == data.name).head))
        else NotFound
    }
  }

}

case class Person(name:String, age:Int)
object Person {
  implicit val format: OFormat[Person] = Json.format[Person]
}

case class RequestData(name: String)
object RequestData {
  implicit val format: OFormat[RequestData] = Json.format[RequestData]
}