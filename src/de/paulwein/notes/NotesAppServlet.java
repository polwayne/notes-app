package de.paulwein.notes;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class NotesAppServlet extends HttpServlet {
	
	// Accessible for Multitenancy DAO Queries
	protected User user = null;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		checkAuthentication(resp);
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		checkAuthentication(resp);
		
	}
	
	/** Check User Authentication
	 * Saves User member when succeeds
	 * Redirects to login page if no user is logged in
	 * @param resp
	 * @throws IOException
	 */
	private void checkAuthentication(HttpServletResponse resp) throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		user = userService.getCurrentUser();
		if(user == null){
			resp.sendRedirect("/");
		}
	}
	
	// Redirect to error page 
	protected void errorOccured(HttpServletResponse resp) throws IOException{
		resp.sendRedirect("/error");
	}
	
	
}
