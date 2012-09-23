package com.test.messenger;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();
		if (session == null) {
			
		}
		else {
			session.invalidate();
		}
		resp.setContentType("text/html");
		resp.getWriter().println("Logged out");
		resp.getWriter().println(
				"<br><a href=\"login.html\">Click here to login</a>");
	}
}