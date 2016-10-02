package de.paulwein.notes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.appengine.api.datastore.Key;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.NotesDAO;
import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

public class NotesDAOJPAImpl implements NotesDAO {
	
	private EntityManagerFactory mEmf;
	
	public NotesDAOJPAImpl(){
		mEmf = EMF.get();
	}

	@Override
	public String getSupportedEntity() {
		return Note.class.getName();
	}

	@Override
	public Key createNotesList(String userId, String name) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		
		NotesList notesList = new NotesList();
		notesList.setName(name);
		notesList.setUserId(userId);
		
		try{
			em.getTransaction().begin();
			em.persist(notesList);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		return notesList.getKey();
	}

	@Override
	public void deleteNotesList(NotesList notesList) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		
		try{
			em.getTransaction().begin();
			em.remove(notesList);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

	@Override
	public void updateNotesList(NotesList notesList) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		
		String name = notesList.getName();
		String userId = notesList.getUserId();
		List<Note> notes = notesList.getNotes();
		
		try{
			em.getTransaction().begin();
			notesList = em.find(NotesList.class, notesList.getKey());
			notesList.setName(name);
			notesList.setUserId(userId);
			notesList.setNotes(notes);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

	@Override
	public void addNote(Key listId, Note note) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			NotesList notesList = em.find(NotesList.class, listId);
			
			List<Note> notes = notesList.getNotes();			
			if(notes == null){
				notes = new ArrayList<Note>();
			}
			notes.add(note);
			notesList.setNotes(notes);
			em.getTransaction().commit();
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace();
			throw new DAOException();
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteNote(Note note) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		Key parentKey = note.getKey().getParent();
		try{
			em.getTransaction().begin();

			NotesList notesList = em.find(NotesList.class, parentKey);
			note = em.find(Note.class, note.getKey());
			notesList.getNotes().remove(note);

			em.getTransaction().commit();
		}catch (Exception ex) {
			em.getTransaction().rollback();
			throw new DAOException();
		} finally {
			em.close();
		}
	}

	@Override
	public void updateNote(Note note) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		Key parentKey = note.getKey().getParent();
		try{
			em.getTransaction().begin();

			NotesList notesList = em.find(NotesList.class, parentKey);
			notesList.getNotes().remove(note);
			notesList.getNotes().add(note);

			em.getTransaction().commit();
		}catch (Exception ex) {
			em.getTransaction().rollback();
			throw new DAOException();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotesList> fetchNotesLists(String userId) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		Query query = em.createQuery("select n from " + NotesList.class.getName() + " n where n.userId = :userId");
		query.setParameter("userId", userId);
		List<NotesList> notes = query.getResultList();
		em.close();
		return notes;
	}

	@Override
	public NotesList loadNotesList(Key key) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		NotesList notesList = null;
		try{
			notesList = em.find(NotesList.class, key);
			notesList.getNotes(); // lazy loading
			em.detach(notesList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			em.close();
		}
		return notesList;
		
	}

	@Override
	public Note loadNote(Key key) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		Note note = null;
		try{
		note = em.find(Note.class, key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			em.close();
		}
		return note;
	}

	
	@Override
	public List<Note> search(String search, String userId) throws DAOException {
		EntityManager em = mEmf.createEntityManager();
		TypedQuery<Note> query = em.createQuery("SELECT n FROM " +  Note.class.getName() + " n WHERE note LIKE '" + search + "%'", Note.class);
		List<Note> results = (List<Note>) query.getResultList();
		em.close();
		return results;  
	}

}
