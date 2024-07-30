package controllers

import play.api.mvc.{BaseController, ControllerComponents, Results}

import javax.inject.Inject


class ApplicationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action {
    Results.Ok
  }

  def create() = TODO

  def read(id: String) = TODO

  def update(id: String) = TODO

  def delete(id: String) = TODO
}