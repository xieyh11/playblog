package controllers;

import models.*;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

public class Secured extends Security.Authenticator {
	
	@Override
	public String getUsername(Context ctx){
		return ctx.session().get("name");
	}
	
	@Override
	public Result onUnauthorized(Context ctx){
		return redirect(routes.Application.index());
	}
	
	public static boolean belongTo(Blog blog, String name){
		return blog.belongTo(name);
	}

}
