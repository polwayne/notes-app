package de.paulwein.notes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.paulwein.notes.dao.DAOFactory;

@SuppressWarnings("serial")
public class HomeServlet extends NotesAppServlet {
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		// switch Datasource --> maybe the user changed
		DAOFactory.initDataSource();		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/home.jsp");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			resp.sendRedirect("/error");
		}
	}
}
