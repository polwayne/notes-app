package de.paulwein.notes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.NotesDAO;
import de.paulwein.notes.dao.Transformer;
import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

public class NotesDAODSImpl implements NotesDAO {

	private DatastoreService datastore;
	
	public NotesDAODSImpl(){
		 datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	@Override
	public String getSupportedEntity() {
		return Note.class.getName();
	}

	@Override
	public Key createNotesList(String userId, String name) throws DAOException {
		NotesList notesList = new NotesList();
		notesList.setUserId(userId);
		notesList.setName(name);
		
		Entity entity = Transformer.notesList2Entity(notesList);
		
		Key key = datastore.put(entity);
		
		return key;
	}

	@Override
	public void deleteNotesList(NotesList notesList) throws DAOException {
		datastore.delete(notesList.getNoteKeys());
		datastore.delete(notesList.getKey());
	}

	@Override
	public void updateNotesList(NotesList notesList) throws DAOException {
		Entity entity = Transformer.notesList2Entity(notesList);
		datastore.put(entity);
	}

	@Override
	public void addNote(Key listId, Note note) throws DAOException {
		try {
			Transaction tx = datastore.beginTransaction();
			
			Entity entity = datastore.get(listId);
			NotesList notesList = Transformer.entity2NotesList(entity);
			
			Entity noteEntity = Transformer.note2Entity(note,listId);
			Key key = datastore.put(noteEntity);
			
			List<Key> keyList = notesList.getNoteKeys();
			if(keyList == null)
				keyList = new ArrayList<Key>();
			keyList.add(key);
			notesList.setNoteKeys(keyList);
			entity = Transformer.notesList2Entity(notesList);
			datastore.put(entity);
			tx.commit();
			
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("Entity not found");
		}

	}

	@Override
	public void deleteNote(Note note) throws DAOException {
		try {
			Transaction tx = datastore.beginTransaction();
			Key noteKey = note.getKey();
			Key parentKey = noteKey.getParent();
			Entity entity = datastore.get(parentKey);
			NotesList notesList = Transformer.entity2NotesList(entity);

			datastore.delete(noteKey);
			
			List<Key> keyList = notesList.getNoteKeys();
			if(keyList == null)
				keyList = new ArrayList<Key>();
			keyList.remove(noteKey);
			notesList.setNoteKeys(keyList);
			
			entity = Transformer.notesList2Entity(notesList);
			datastore.put(entity);
			tx.commit();
			
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("Entity not found");
		}
	}

	@Override
	public void updateNote(Note note) throws DAOException {
		try {
			Transaction tx = datastore.beginTransaction();
			Key noteKey = note.getKey();
			Key parentKey = noteKey.getParent();
			Entity entity = Transformer.note2Entity(note, parentKey);
			
			datastore.put(entity);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		}
	}

	@Override
	public List<NotesList> fetchNotesLists(String userId) throws DAOException {
		Filter userFilter = new FilterPredicate(PROPERTY_USERID, FilterOperator.EQUAL, userId);
		Query query = new Query(ENTITY_KIND_NOTESLIST).setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(query);
		
		List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
		List<NotesList> notesLists = new ArrayList<NotesList>();
		for(Entity e : entities){
			NotesList notesList = Transformer.entity2NotesList(e);
			notesLists.add(notesList);
		}
		
		return notesLists;
	}

	@Override
	public NotesList loadNotesList(Key key) throws DAOException {
		try {
			Entity entity = datastore.get(key);
			NotesList notesList = Transformer.entity2NotesList(entity);
			Query query = new Query(ENTITY_KIND_NOTE).setAncestor(notesList.getKey());
			PreparedQuery pq = datastore.prepare(query);
			
			List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
			List<Note> notes = new ArrayList<Note>();
			
			for(Entity e : entities){
				Note note = Transformer.entity2Note(e);
				notes.add(note);
			}
			
			notesList.setNotes(notes);
			
			return notesList;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("Entity not found!");
		}
	}

	@Override
	public Note loadNote(Key key) throws DAOException {
		try {
			Entity entity = datastore.get(key);
			Note note = Transformer.entity2Note(entity);
			return note;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new DAOException();
		}
	}

	@Override
	public List<Note> search(String search, String userId) throws DAOException {
		Filter userFilter = new FilterPredicate(PROPERTY_USERID, FilterOperator.EQUAL, userId);
		Query query = new Query(ENTITY_KIND_NOTESLIST).setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(query);
		
		List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
		List<Note> results = new ArrayList<Note>();
		for(Entity e : entities){
			Filter searchFilter1 = new FilterPredicate(PROPERTY_NOTE, FilterOperator.GREATER_THAN_OR_EQUAL,search);
			Filter searchFilter2 = new FilterPredicate(PROPERTY_NOTE, FilterOperator.LESS_THAN, search + "\ufffd");
			Filter searchFilter = CompositeFilterOperator.and(searchFilter1,searchFilter2);
			Query q = new Query(ENTITY_KIND_NOTE,e.getKey()).setFilter(searchFilter);
			PreparedQuery prepq = datastore.prepare(q);
			List<Entity> notes = prepq.asList(FetchOptions.Builder.withDefaults());
			for(Entity eNote : notes){
				Note note = Transformer.entity2Note(eNote);
				results.add(note);
			}
		}
		
		return results;
	}

}
