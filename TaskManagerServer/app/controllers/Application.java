package controllers;

import play.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import models.Project;
import models.Task;
import models.UserAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

public class Application extends Controller {

	static Form<Task> taskForm = form(Task.class);
	static Form<Project> projectForm = form(Project.class);

	public static Result index() {
		return redirect(routes.Application.tasks());
	}

	// @BodyParser.Of(Json.class)
	public static Result tasksJson() {

		List<Task> tasks = Task.findAll();
		return ok(Json.toJson(tasks));
	}

	public static Result tasks() {
		List<Task> ttasks = Task.findAll();

		return ok(
		// tasks.render(Task.findAll(), taskForm, UserAccount.findAll(),
		// projects)
		// tasks.render(Task.findAll(), taskForm, UserAccount.findAll(),
		// Project.findAll())
		// tasks.render(Task.findAll(), taskForm, UserAccount.findAll())
		tasks.render(Task.findAll(), taskForm)
		// views.html.index.render(Task.findAll(), taskForm,
		// UserAccount.findAll())
		);
	}

	public static Result newTask() {
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(
			// tasks.render(Task.findAll(), filledForm, UserAccount.findAll(),
			// Project.findAll())
			// tasks.render(Task.findAll(), filledForm, UserAccount.findAll())
			tasks.render(Task.findAll(), filledForm));
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
		return ok(projects.render(Project.findAll(), projectForm)
		// views.html.index.render(Task.findAll(), taskForm,
		// UserAccount.findAll())
		);
	}

	public static Result newProject() {
		Form<Project> filledForm = projectForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(projects.render(Project.findAll(), filledForm)
			// views.html.index.render(Task.findAll(), filledForm,
			// UserAccount.findAll())
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

	// -- Authentication

	public static class Login {

		public String identifier;
		public String password;

		public String validate() {
			if (UserAccount.authenticateMail(identifier, password) == null
					&& UserAccount.authenticateNickname(identifier, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}

	}

	/**
	 * Login page.
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			String identifier = loginForm.get().identifier;
			UserAccount currentUser;
			if (identifier.contains("@")) {
				currentUser = UserAccount.findByEmail(identifier);
			} else
				currentUser = UserAccount.findByNickname(identifier);
			session("nickname", currentUser.getNickname());
			return redirect(routes.Application.index());
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}

}
