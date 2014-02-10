package controllers;

import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.login;
import views.html.blogs.*;

public class Blogs extends Controller {
	
	public static Result index(Long id){
		Blog blog = Blog.find.byId(id);
		return ok(index.render(blog, Application.sessionUser()));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result create(){
		return ok(create.render(Form.form(BlogForm.class), Application.sessionUser()));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result save(){
		Form<BlogForm> form = Form.form(BlogForm.class).bindFromRequest();
		if (form.hasErrors()){
			return badRequest(create.render(form, Application.sessionUser()));
		}else{
			Blog blog = Blog.create(form.get().title, form.get().text, session("name"));
			return redirect(routes.Blogs.index(blog.id));
		}
	}
	
	public static class BlogForm{
		public String title;
		public String text;
		
		public String validate(){
			if (title == null){
				return "need title";
			}
			if (text == null){
				text = "";
			}
			return null;
		}
	}

}
