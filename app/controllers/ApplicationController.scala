package controllers

import play.api.mvc.{BaseController, ControllerComponents, Results}
import repositories.DataRepository

import javax.inject.Inject


class ApplicationController @Inject()(val controllerComponents: ControllerComponents, val dataRepository: DataRepository) extends BaseController {

  def index() = Action {
    Results.Ok
  }

  def create() = TODO

  def read(id: String) = TODO

  def update(id: String) = TODO

  def delete(id: String) = TODO
}