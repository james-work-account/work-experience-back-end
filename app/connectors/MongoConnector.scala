package connectors

import controllers.Person
import javax.inject.Singleton
import play.api.libs.json.Json
import reactivemongo.api._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, Macros}
import reactivemongo.play.json._

import scala.concurrent.Future

@Singleton
class MongoConnector {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val mongoUri = "mongodb://localhost:27017/work-experience?authMode=scram-sha1"

  private val driver = MongoDriver()
  private val parsedUri = MongoConnection.parseURI(mongoUri)
  private val connection = parsedUri.map(driver.connection)
  private val futureConnection = Future.fromTry(connection)

  private def peopleDB: Future[DefaultDB] = futureConnection.flatMap(_.database("work-experience"))

  private def peopleCollection: Future[BSONCollection] = peopleDB.map(_.collection("people"))

  implicit def postReader: BSONDocumentReader[Person] = Macros.reader[Person]

  def getAllPeople: Future[List[Person]] = {
    peopleCollection.flatMap(_.find(Json.obj(), None)(JsObjectWriter, JsObjectWriter).cursor[Person]()
      .collect[List](-1, Cursor.FailOnError[List[Person]]()))
  }

}
