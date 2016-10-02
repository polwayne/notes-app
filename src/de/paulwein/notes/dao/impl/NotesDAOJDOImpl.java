package de.paulwein.notes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.NotesDAO;
import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

public class NotesDAOJDOImpl implements NotesDAO {

	PersistenceManagerFactory mPmf = null;
	
	public NotesDAOJDOImpl(){
		mPmf = PMF.get();
	}
	
	@Override
	public String getSupportedEntity() {
		return Note.class.getName();
	}

	@Override
	public Key createNotesList(String userId, String name) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();

		NotesList notesList = new NotesList();
		notesList.setName(name);
		notesList.setUserId(userId);
		
		try{
			notesList = pm.makePersistent(notesList);
		} finally{
			pm.close();
		}
		
		return notesList.getKey();
	}

	@Override
	public void deleteNotesList(NotesList notesList) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		try {
			pm.currentTransaction().begin();

			notesList = pm.getObjectById(NotesList.class, notesList.getKey());
			pm.deletePersistent(notesList);

			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new DAOException();
		} finally {
			pm.close();
		}
	}

	@Override
	public void updateNotesList(NotesList notesList) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		String name = notesList.getName();
		String userId = notesList.getUserId();
		List<Note> notes = notesList.getNotes();

		try {
			pm.currentTransaction().begin();
			
			notesList = pm.getObjectById(NotesList.class, notesList.getKey());
			notesList.setName(name);
			notesList.setUserId(userId);
			notesList.setNotes(notes);
			pm.makePersistent(notesList);
			
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new DAOException();
		} finally {
			pm.close();
		}
	}

	@Override
	public void addNote(Key listId, Note note) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();

		try {
			pm.currentTransaction().begin();
			
			NotesList notesList = pm.getObjectById(NotesList.class, listId);
			List<Note> notes = notesList.getNotes();			
			if(notes == null){
				notes = new ArrayList<Note>();
			}
			notes.add(note);
			notesList.setNotes(notes);
			pm.makePersistent(notesList);  
			
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			ex.printStackTrace();
			throw new DAOException();
		} finally {
			pm.close();
		}
	}

	@Override
	public void deleteNote(Note note) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		Key parentKey = note.getKey().getParent();
		try{
			pm.currentTransaction().begin();

			NotesList notesList = pm.getObjectById(NotesList.class, parentKey);
			note = pm.getObjectById(Note.class, note.getKey());
			notesList.getNotes().remove(note);
			pm.makePersistent(notesList);

			pm.currentTransaction().commit();
		}catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new DAOException();
		} finally {
			pm.close();
		}
	}

	@Override
	public void updateNote(Note note) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		Key parentKey = note.getKey().getParent();
		try{
			pm.currentTransaction().begin();

			NotesList notesList = pm.getObjectById(NotesList.class, parentKey);
			notesList.getNotes().remove(note);
			notesList.getNotes().add(note); // TODO maybe findObjectById(note.getKey) before add/remove
			pm.makePersistent(notesList);

			pm.currentTransaction().commit();
		}catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new DAOException();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotesList> fetchNotesLists(String userId) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		Query q = pm.newQuery(NotesList.class,"userId == userIdParam");
		q.declareParameters("String userIdParam");
		List<NotesList> results = (List<NotesList>) q.execute(userId);
		return results;
	}

	@Override
	public NotesList loadNotesList(Key key) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		NotesList notesList = null;
		try {
			notesList = pm.getObjectById(NotesList.class, key);
			notesList.getNotes(); // lazyfetch
		} catch (Exception ex) {
			throw new DAOException();
		} finally {
			pm.close();
		}
		return notesList;
	}

	@Override
	public Note loadNote(Key key) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		Note note = null;
		try {
			note = pm.getObjectById(Note.class, key);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DAOException();
		} finally {
			pm.close();
		}
		return note;
	}

	@Override
	public List<Note> search(String search, String userId) throws DAOException {
		PersistenceManager pm = mPmf.getPersistenceManager();
		Query q = pm.newQuery(Note.class,"note.startsWith(\"" + search + "\")");
		@SuppressWarnings("unchecked")
		List<Note> results = (List<Note>) q.execute();
		return results;
	}

}
