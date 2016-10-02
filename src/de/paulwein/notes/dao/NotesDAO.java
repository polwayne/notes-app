package de.paulwein.notes.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

public interface NotesDAO extends DAO{
	
	public static final String ENTITY_KIND_NOTESLIST = NotesList.class.getSimpleName();
	public static final String ENTITY_KIND_NOTE = Note.class.getSimpleName();
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_USERID = "userId";
	public static final String PROPERTY_NOTES = "notes";
	public static final String PROPERTY_SUBJECT = "subject";
	public static final String PROPERTY_NOTE = "note";
	public static final String PROPERTY_DATE = "date";
	
	// NotesList Manipulation
	public Key createNotesList(String userId, String name) throws DAOException;
	public void deleteNotesList(NotesList notesList) throws DAOException;
	public void updateNotesList(NotesList notesList) throws DAOException;
	
	// Notes manipulation
	public void addNote(Key listId, Note note) throws DAOException;
	public void deleteNote(Note note) throws DAOException; // get Parent key with getParent
	public void updateNote(Note note) throws DAOException;
	
	// Loading 
	public List<NotesList> fetchNotesLists(String userId) throws DAOException;
	public NotesList loadNotesList(Key key) throws DAOException;
	public Note loadNote(Key key) throws DAOException;
	
	public List<Note> search(String search, String userId) throws DAOException;
	
	
}
