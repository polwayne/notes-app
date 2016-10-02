package de.paulwein.notes.pojo;

import java.util.List;

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
public class NotesList {
	
   
	// JPA Annotations
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// JDO Annotations
	@PrimaryKey  
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;   
	 
	@Basic // JPA
	@Persistent // JDO
	private String name;
	
	@Basic // JPA
	@Persistent // JDO  
	private String userId;
	 
	@Basic
	@Persistent
	private List<Note> notes;
	
	@Transient
	@NotPersistent
	private List<Key> noteKeys;

	@Transient
	@NotPersistent
	private String stringKey;
	
	 
	public NotesList(){} 

	public Key getKey() {
		return key;  
	} 

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Key> getNoteKeys() {
		return noteKeys;
	}

	public void setNoteKeys(List<Key> noteKeys) {
		this.noteKeys = noteKeys;
	}
	
	// String for Web 
	// No other possibility to get it as String in jsp
	// Otherwise you would destroy the MVC Pattern
	public String getStringKey() {
		return KeyFactory.keyToString(key);
	} 

}
