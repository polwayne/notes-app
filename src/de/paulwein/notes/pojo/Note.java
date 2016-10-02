package de.paulwein.notes.pojo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Entity
@PersistenceCapable(detachable="true")
public class Note {
	
	// JPA Annotations  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// JDO Annotations
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	    
	@Basic // JPA
	@Persistent // JDO
	private Date date;  
	  
	@Basic
	@Persistent
	private String subject;  

	@Basic
	@Persistent
	private String note; 
	
	@Transient
	@NotPersistent
	private String stringKey;
	
	public Note() {}

	public Date getDate() {
		return date; 
	}
   
	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}  

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	} 
	
	public Key getKey(){
		return key;
	}
	
	// String for Web 
	// No other possibility to get it as String in jsp
	// Otherwise you would destroy the MVC Pattern
	public String getStringKey() {
		return KeyFactory.keyToString(key);
	} 

	public void setKey(Key key) {
		this.key = key;
	}
	
	@Override
	public boolean equals(Object object){
		
		if(object instanceof Note){
			if(((Note) object).getKey().equals(key))
				return true;
		}
		
		return false;
	}

	


}
