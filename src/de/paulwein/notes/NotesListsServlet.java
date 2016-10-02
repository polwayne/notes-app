package de.paulwein.notes;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.DAOFactory;
import de.paulwein.notes.dao.NotesDAO;
import de.paulwein.notes.pojo.Note;
import de.paulwein.notes.pojo.NotesList;

@SuppressWarnings("serial")
public class NotesListsServlet extends NotesAppServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO nDao = df.getDAO(Note.class, NotesDAO.class);
			List<NotesList> notesLists = nDao.fetchNotesLists(user.getUserId());
			req.setAttribute("list", notesLists);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/noteslists.jsp");
			requestDispatcher.forward(req, resp);
		} catch (DAOException e) {
			e.printStackTrace();
			errorOccured(resp);
		} catch (ServletException e) {
			errorOccured(resp);
			e.printStackTrace(); 
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doPost(req,resp);

		String name = req.getParameter("name");
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO nDao = df.getDAO(Note.class, NotesDAO.class);
			nDao.createNotesList(user.getUserId(), name);
			doGet(req, resp);
		} catch (DAOException e) {
			e.printStackTrace();
			errorOccured(resp);
		}
	}
}
