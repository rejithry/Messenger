package com.test.messenger;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query("User");

		q.setFilter(new FilterPredicate("user", Query.FilterOperator.EQUAL,
				userName));

		PreparedQuery pq = datastore.prepare(q);
		if (!pq.asIterator().hasNext()) {
			resp.getWriter().println("User doesnt exist");
			resp.getWriter().println(
					"<br><a href=\"login.html\">Click here to try again</a>");
		} else {
			for (Entity result : pq.asIterable()) {
				if (((String) result.getProperty("passWord")).equals(passWord)) {
					resp.getWriter().println("Login succeeded");
					HttpSession session = req.getSession(true);
					session.putValue("name",userName);
					resp.getWriter()
							.println(
									"<br><a href=\"message.html\">Click here to send a message</a>");
				} else {
					resp.getWriter().println("Invalid password");
					resp.getWriter()
							.println(
									"<br><a href=\"login.html\">Click here to try again</a>");
				}
			}

		}
	}

}
