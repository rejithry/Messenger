package com.test.messenger;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class SignUpServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		String userName = req.getParameter("username");
		String passWord1 = req.getParameter("password1");
		String passWord2 = req.getParameter("password2");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query("User");

		q.setFilter(new FilterPredicate("user",
			     Query.FilterOperator.EQUAL,
			     userName));

		PreparedQuery pq = datastore.prepare(q);

		if (pq.asIterator().hasNext()) {
			resp.getWriter().println("User already exists");
			resp.getWriter()
					.println(
							"<br><a href=\"signup.html\">Click here to go to try again</a>");
			resp.getWriter()
					.println(
							"<br><a href=\"index.html\">Click here to go to home page</a>");
		}

		else if (passWord1.length() == 0 || passWord1.length() == 0
				|| !passWord1.equals(passWord2)) {
			resp.getWriter().println("Passwords doesnt match or empty");
			resp.getWriter()
					.println(
							"<br><a href=\"signup.html\">Click here to go to try again</a>");
			resp.getWriter()
					.println(
							"<br><a href=\"index.html\">Click here to go to home page</a>");
		} else if (passWord1.equals(passWord2)) {
			Entity user = new Entity("User", userName);
			user.setProperty("user", userName);
			user.setProperty("passWord", passWord1);

			datastore.put(user);

			resp.getWriter().println("User created");
			resp.getWriter()
					.println(
							"<br><a href=\"index.html\">Click here to go to home page</a>");
		} else {
			resp.getWriter().println("Error adding user");
			resp.getWriter()
					.println(
							"<br><a href=\"signup.html\">Click here to go to try again</a>");
			resp.getWriter()
					.println(
							"<br><a href=\"index.html\">Click here to go to home page</a>");
		}

	}
}
