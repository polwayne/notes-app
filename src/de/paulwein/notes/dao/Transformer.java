package de.paulwein.notes.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

public class Transformer {

	public static Entity note2Entity(Note note, Key parent){
		Entity entity = null;
		
		if(note.getKey() == null)
			entity = new Entity(NotesDAO.ENTITY_KIND_NOTE,parent);
		else
			entity = new Entity(note.getKey());
		
		entity.setProperty(NotesDAO.PROPERTY_DATE, note.getDate());
		entity.setProperty(NotesDAO.PROPERTY_SUBJECT, note.getSubject());
		entity.setProperty(NotesDAO.PROPERTY_NOTE, note.getNote());

		return entity;
	}
	
	public static Note entity2Note(Entity entity){
		Key key = entity.getKey();
		Date date = (Date) entity.getProperty(NotesDAO.PROPERTY_DATE);
		String subject = (String) entity.getProperty(NotesDAO.PROPERTY_SUBJECT);
		String note = (String) entity.getProperty(NotesDAO.PROPERTY_NOTE);

		Note n = new Note();
		n.setKey(key);
		n.setDate(date);
		n.setSubject(subject);
		n.setNote(note);
		
		return n;
	}
	
	public static Entity notesList2Entity(NotesList notesList){
		Entity entity = null;
		
		if(notesList.getKey() == null)
			entity = new Entity(NotesDAO.ENTITY_KIND_NOTESLIST);
		else
			entity = new Entity(notesList.getKey());
		
		entity.setProperty(NotesDAO.PROPERTY_USERID, notesList.getUserId());
		entity.setProperty(NotesDAO.PROPERTY_NAME, notesList.getName());
		
		if(notesList.getNoteKeys() != null)
			entity.setProperty(NotesDAO.PROPERTY_NOTES, notesList.getNoteKeys());
		else if(notesList.getNotes() != null){
			List<Key> noteKeys = new ArrayList<Key>();
			for(Note n : notesList.getNotes()){
				noteKeys.add(n.getKey());
			}			
			entity.setProperty(NotesDAO.PROPERTY_NOTES, noteKeys);
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public static NotesList entity2NotesList(Entity entity){
		Key key = entity.getKey();
		String userId = (String) entity.getProperty(NotesDAO.PROPERTY_USERID);
		String name = (String) entity.getProperty(NotesDAO.PROPERTY_NAME);
		List<Key> notesKeys = (List<Key>) entity.getProperty(NotesDAO.PROPERTY_NOTES);

		NotesList notesList = new NotesList();
		notesList.setKey(key);
		notesList.setUserId(userId);
		notesList.setName(name);
		notesList.setNoteKeys(notesKeys);
		
		return notesList;
	}
	
	
}
