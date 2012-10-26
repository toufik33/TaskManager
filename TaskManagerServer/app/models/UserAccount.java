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
	public String nickname;
	@Required
	public String password;
	
	public static Finder<Long,UserAccount> find = new Finder( Long.class, UserAccount.class );
	
	public static List<UserAccount> findAll()
	{
		return find.all();
	}

	    /**
	     * Retrieve a User from email.
	     */
	public static UserAccount findByEmail(String email) 
	{
		return find.where().eq("mail", email).findUnique();
	}
    
		/**
	     * Retrieve a User from nickname.
	     */
	public static UserAccount findByNickname(String nickname) 
	{
		return find.where().eq("nickname", nickname).findUnique();
	}

    /**
     * Authenticate a User.
     */
    public static UserAccount authenticateMail(String email, String password) {
        return find.where()
            .eq("mail", email)
            .eq("password", password)
            .findUnique();
    }

/**
     * Authenticate a User.
     */
    public static UserAccount authenticateNickname(String nickname, String password) {
        return find.where()
            .eq("nickname", nickname)
            .eq("password", password)
            .findUnique();
    }

}
