package de.paulwein.notes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.paulwein.notes.dao.DAOException;
import de.paulwein.notes.dao.DAOFactory;

@SuppressWarnings("serial")
public class SettingsServlet extends NotesAppServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		
		try {
			int datasource = DAOFactory.getDataSource();
			req.setAttribute("datasource", datasource);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/settings.jsp");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			resp.sendRedirect("/error");
		}		
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doPost(req, resp);
		
		String persistence = req.getParameter("persistence");
		
		int selectedDataSource = Integer.valueOf(persistence);
		
		try {
			DAOFactory.setDataSource(selectedDataSource);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("/settings");
	}
}
