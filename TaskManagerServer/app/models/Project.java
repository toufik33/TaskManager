package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Project extends Model
{

	@Id
	public Long id;
	@Required
	public String label;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	public List<Task> tasks;
}
