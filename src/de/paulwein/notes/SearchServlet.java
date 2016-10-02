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

@SuppressWarnings("serial")
public class SearchServlet extends NotesAppServlet {	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doPost(req, resp);
		
		String search = req.getParameter("search");
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			NotesDAO notesDAO = df.getDAO(Note.class, NotesDAO.class);
			List<Note> notes = notesDAO.search(search, user.getUserId());
			req.setAttribute("list", notes);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/search.jsp");
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
