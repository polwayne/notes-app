package de.paulwein.notes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AboutServlet extends NotesAppServlet {
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/about.jsp");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			resp.sendRedirect("/error");
		}
	}
}
