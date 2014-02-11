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
	
	@Security.Authenticated(Secured.class)
	public static Result edit(Long id){
		Blog blog = Blog.find.byId(id);
		if(Secured.belongTo(blog, session("name"))){
			Form<BlogForm> form = Form.form(BlogForm.class).fill(new BlogForm(blog.title, blog.text));
			return ok(edit.render(id, form, Application.sessionUser()));
		}else{
			return redirect(routes.Blogs.index(id));
		}
	}
	
	@Security.Authenticated(Secured.class)
	public static Result saveEdit(Long id){
		Blog blog = Blog.find.byId(id);
		if(Secured.belongTo(blog, session("name"))){
			Form<BlogForm> form = Form.form(BlogForm.class).bindFromRequest();
			if (form.hasErrors()){
				return badRequest(edit.render(id, form, Application.sessionUser()));
			}else{
				Blog.edit(id, form.get().title, form.get().text);
				return redirect(routes.Blogs.index(id));
			}
		}else{
			return redirect(routes.Blogs.index(id));
		}
	}
	
	@Security.Authenticated(Secured.class)
	public static Result delete(Long id){
		Blog blog = Blog.find.byId(id);
		if(Secured.belongTo(blog, session("name"))){
			blog.delete();
			return ok();
		}else{
			return redirect(routes.Blogs.index(id));
		}
	}
	
	public static class BlogForm{
		public String title;
		public String text;
		
		public BlogForm(){
			
		}
		
		public BlogForm(String title, String text){
			this.title = title;
			this.text = text;
		}
		
		public String validate(){
			if ("".equals(title) || title == null){
				return "need title";
			}
			if (text == null){
				text = "";
			}
			return null;
		}
	}

}
