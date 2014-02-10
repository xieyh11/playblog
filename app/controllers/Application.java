package controllers;

import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(Blog.find.all(), sessionUser()));
    }
    
    public static class Login {
    	public String name;
    	public String password;
    	
    	public String validate(){
    		if (User.authenticate(name, password) == null){
    			return "Invalid user or password";
    		}
    		return null;
    	}
    }
    
    public static Result login() {
    	if(session("name") != null){
    		return redirect(routes.Application.index());
    	}
    	return ok(login.render(Form.form(Login.class)));
    }
    
    public static Result logout(){
    	session().clear();
    	return redirect(routes.Application.index());
    }
    
    public static Result authenticate() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if (loginForm.hasErrors()){
    		return badRequest(login.render(loginForm));
    	} else {
    		session().clear();
    		session("name", loginForm.get().name);
    		return redirect(routes.Application.index());
    	}
    }
    
    public static User sessionUser(){
    	return (session("name") == null)?null:User.find.byId(session("name"));
    }

}
