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

@SuppressWarnings("serial")
public class NoteServlet extends NotesAppServlet {
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		
		String key = req.getParameter("key");		
		Key mKey = KeyFactory.stringToKey(key);
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO nDao = df.getDAO(Note.class, NotesDAO.class);
			Note note = nDao.loadNote(mKey);

			req.setAttribute("note", note);
			req.setAttribute("key", key);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/note.jsp");
			requestDispatcher.forward(req, resp); 
		
		} catch (DAOException e) {
			errorOccured(resp);
		} catch (ServletException e) {
			errorOccured(resp);
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doPost(req, resp);
		
		String action = req.getParameter("action");
		String keyString = req.getParameter("key");
		Key mKey = KeyFactory.stringToKey(keyString);
		
		if(action.equals("delete")){
			try {
				DAOFactory df = DAOFactory.getInstance();
				NotesDAO nDao = df.getDAO(Note.class, NotesDAO.class);
				Note note = new Note();
				note.setKey(mKey);
				nDao.deleteNote(note);
				resp.sendRedirect("noteslists");
			} catch (Exception e) {
				e.printStackTrace();
//				errorOccured(resp);
			}
			
		}
		
		
	}
}
