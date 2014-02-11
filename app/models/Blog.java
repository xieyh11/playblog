package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;
import play.data.format.*;

@Entity
public class Blog extends Model {
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String title;
	
	@Constraints.Required
	@Formats.DateTime(pattern="yyyy-mm-dd")
	public Date postDate;
	
	@Constraints.Required
	@ManyToOne
	public User user;
	
	public String text;
	
	public Blog(){
		postDate = new Date();
	}
	
	public Blog(String title, String text, String user){
		this.text = text;
		this.title = title;
		this.user = User.find.ref(user);
		this.postDate = new Date();
	}
	
	public static Finder<Long, Blog> find = new Finder<Long, Blog>(Long.class, Blog.class);
	
	public static Blog create(String title, String text, String user){
		Blog blog = new Blog(title, text, user);
		blog.save();
		return blog;
	}
	
	public boolean belongTo(String name){
		return user.name.equals(name);
	}
	
	public static Blog edit(Long id, String title, String text){
		Blog blog = find.byId(id);
		blog.text = text;
		blog.title = title;
		blog.save();
		return blog;
	}

}
