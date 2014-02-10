package models;

import javax.persistence.*;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;
import play.data.format.*;

@Entity
public class User extends Model {
	
	@Id
	public String name;
	
	@Constraints.Required
	public String password;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	public List<Blog> blogs;
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	
	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
	
	public static User authenticate(String name, String password){
		return find.where().eq("name", name).eq("password", password).findUnique();
	}
}
