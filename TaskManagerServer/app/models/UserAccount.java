package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class UserAccount extends Model
{
	@Id
	public Long id;
	@Email @Required
	public String mail;
	@Required 
	public String nickName;
	@Required
	public String password;
	
	public static Finder<Long,UserAccount> find = new Finder( Long.class, UserAccount.class );
	
	public static List<UserAccount> findAll()
	{
		return find.all();
	}
}
