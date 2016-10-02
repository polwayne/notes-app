package de.paulwein.notes;

import java.io.IOException;
import java.util.Date;

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
public class CreateNoteServlet extends NotesAppServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		
		String keyString = req.getParameter("key");
		
		String action = req.getParameter("action");
		if(action != null && action.equals("edit")){
			req.setAttribute("edit", "edit");
			Key noteKey = KeyFactory.stringToKey(keyString);
			try {
				DAOFactory df = DAOFactory.getInstance();
				NotesDAO notesDAO = df.getDAO(Note.class, NotesDAO.class);
				Note note = notesDAO.loadNote(noteKey);
				req.setAttribute("note",note);
			} catch (DAOException e) {
				errorOccured(resp);
			}
		}
		
		req.setAttribute("key", keyString);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/create.jsp");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			resp.sendRedirect("/error");
		}		
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doPost(req, resp);
		
		String subject = req.getParameter("subject");
		String note = req.getParameter("note");
		String keyString = req.getParameter("key");
		Key listKey = KeyFactory.stringToKey(keyString);
		
		Note n = new Note();
		n.setDate(new Date());
		n.setSubject(subject);
		n.setNote(note);
		String action = req.getParameter("action");
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO notesDAO = df.getDAO(Note.class, NotesDAO.class);
			if(action != null && action.equals("edit")){
				n.setKey(listKey);
				notesDAO.updateNote(n);
				keyString = KeyFactory.keyToString(listKey.getParent());
			}
			else
				notesDAO.addNote(listKey, n);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("/noteslist?key=" + keyString);
	}
}
