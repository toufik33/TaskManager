package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Project extends Model
{

	@Id
	public Long id;
	@Required
	public String label;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	public List<Task> tasks;
	
	public static Finder<Long,Project> find = new Finder( Long.class, Project.class );

	public static List<Project> findAll() 
	{
		return find.all();
	}
	
	public static void delete(Long id) 
	{
		find.ref(id).delete();
	}
	
	public static void create(Project project) 
	{
		project.save();
	}
}
