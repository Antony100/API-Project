package controllers

import baseSpec.BaseSpecWithApplication
import models.DataModel
import play.api.test.FakeRequest
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future

class ApplicationControllerSpec extends BaseSpecWithApplication{

  val TestApplicationController = new ApplicationController(
    component,
    repository,
  )

  private val dataModel: DataModel = DataModel(
    "abcd",
    "test name",
    "test description",
    100
  )
  "ApplicationController .index" should {

    val result = TestApplicationController.index()(FakeRequest())

    "return TODO" in {
      status(result) shouldBe Status.OK
    }
  }

  "ApplicationController .create" should {

    "create a book in the database" in {

      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Status.CREATED
    }
  }

  "ApplicationController .read" should {

    "find a book in the database by id" in {

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)


      val readResult: Future[Result] = TestApplicationController.read("abcd")(FakeRequest())

      status(createdResult) shouldBe Status.CREATED
      status(readResult) shouldBe OK
      contentAsJson(readResult).as[DataModel] shouldBe dataModel
    }
  }

  "ApplicationController .update()" should {

    "update a book in the database by id" in {

    }
  }

  "ApplicationController .delete()" should {

  }

  override def beforeEach(): Unit = await(repository.deleteAll())
  override def afterEach(): Unit = await(repository.deleteAll())

}
