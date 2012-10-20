package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import models.Project;
import models.Task;
import models.UserAccount;

public class Application extends Controller {

	static Form<Task> taskForm = form(Task.class);
	static Form<Project> projectForm = form(Project.class);

	public static Result index() {
		return redirect(routes.Application.tasks());
	}

	public static Result tasks() {
		return ok(
				tasks.render(Task.findAll(), taskForm, UserAccount.findAll(), Project.findAll())
				//views.html.index.render(Task.findAll(), taskForm, UserAccount.findAll())
				);
	}

	public static Result newTask() {
		Form<Task> filledForm = taskForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return badRequest(
					tasks.render(Task.findAll(), filledForm, UserAccount.findAll(), Project.findAll())
					//views.html.index.render(Task.findAll(), filledForm, UserAccount.findAll())
					);
		} else {
			Task.create(filledForm.get());
			return redirect(routes.Application.tasks());  
		}
	}

	public static Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.Application.tasks());
	}
	
	public static Result projects() {
		return ok(
				projects.render(Project.findAll(), projectForm)
				//views.html.index.render(Task.findAll(), taskForm, UserAccount.findAll())
				);
	}
	
	public static Result newProject() {
		Form<Project> filledForm = projectForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return badRequest(
					projects.render(Project.findAll(), filledForm)
					//views.html.index.render(Task.findAll(), filledForm, UserAccount.findAll())
					);
		} else {
			Project.create(filledForm.get());
			return redirect(routes.Application.projects());  
		}
	}
	
	public static Result deleteProject(Long id) {
		Project.delete(id);
		return redirect(routes.Application.projects());
	}

}