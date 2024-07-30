package controllers

import models.DataModel
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Results}
import repositories.DataRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class ApplicationController @Inject()(val controllerComponents: ControllerComponents, val dataRepository: DataRepository) (implicit val ec: ExecutionContext) extends BaseController {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    dataRepository.index().map{
      case Right(item: Seq[DataModel]) => Ok {Json.toJson(item)}
      case Left(error) => Status(error)(Json.toJson("Unable to find any books"))
    }
  }

  def create() = TODO


  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    dataRepository.read(id).map { item =>
      Ok(Json.toJson(item))
    }
  }

  def update(id: String) = TODO

  def delete(id: String): Action[AnyContent] = Action.async { implicit request =>
    dataRepository.delete(id).map { _ =>
      Accepted
    }
  }
}