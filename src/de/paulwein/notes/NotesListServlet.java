package de.paulwein.notes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.DAOFactory;
import de.paulwein.notes.dao.NotesDAO;
import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

@SuppressWarnings("serial")
public class NotesListServlet extends NotesAppServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		
		String keyString = req.getParameter("key");
		Key listKey = KeyFactory.stringToKey(keyString);
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO nDao = df.getDAO(Note.class, NotesDAO.class);
			NotesList notesList = nDao.loadNotesList(listKey);
			req.setAttribute("key", keyString);
			req.setAttribute("list", notesList.getNotes());
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/noteslist.jsp");
			requestDispatcher.forward(req, resp); 
		} catch (DAOException e) {
			e.printStackTrace();
			errorOccured(resp);
		} catch (ServletException e) {
			errorOccured(resp);
			e.printStackTrace();
		}
		
	}
}
