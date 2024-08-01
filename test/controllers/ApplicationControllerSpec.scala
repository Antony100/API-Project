package controllers

import baseSpec.BaseSpecWithApplication
import models.DataModel
import play.api.test.FakeRequest
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContentAsEmpty, Result}
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
      val updatedDataModel: DataModel = DataModel(
        "abcd",
        "updated test name",
        "updated test description",
        200
      )
      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)


      val updateRequest: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(updatedDataModel))
      val updateResult: Future[Result] = TestApplicationController.update("abcd")(updateRequest)

      status(createdResult) shouldBe Status.CREATED
      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[DataModel] shouldBe updatedDataModel
    }


    "ApplicationController.delete()" should {

      "delete a book in the database by id" in {
        // Create a record first
        val createRequest: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
        val createdResult: Future[Result] = TestApplicationController.create()(createRequest)

        // Ensure the record is created
        status(createdResult) shouldBe Status.CREATED

        // Delete the record
        val deleteRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(DELETE, s"/api/${dataModel._id}")
        val deleteResult: Future[Result] = TestApplicationController.delete("abcd")(deleteRequest)

        // Verify the delete operation
        status(deleteResult) shouldBe Status.ACCEPTED
      }
    }
  }


  override def beforeEach(): Unit = await(repository.deleteAll())
  override def afterEach(): Unit = await(repository.deleteAll())

}
