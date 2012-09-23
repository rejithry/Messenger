/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.push;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {

	static final String ATTRIBUTE_STATUS = "status";

	/**
	 * Displays the existing messages and offer the option to send a new one.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String message = req.getParameter("message");
		HttpSession ses = req.getSession(false);
		if (ses == null) {
			resp.getWriter().println("You are not logged in");
			resp.getWriter().println(
					"<br><a href=\"login.html\">Click here to login</a>");
		} else {
			String user = ses.getValue("name").toString();
			out.print("<html><body>");
			out.print("<head>");
			out.print("  <title>GCM Demo</title>");
			out.print("  <link rel='icon' href='favicon.png'/>");
			out.print("</head>");
			String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
			if (status != null) {
				out.print(status);
			}
			int total = Datastore.getTotalDevices();
			if (total == 0) {
				out.print("<h2>No devices registered!</h2>");
			} else {
				out.print("<h2>" + total + " device(s) registered!</h2>");
				out.print("<form name='form' method='POST' action='sendAll'>");
				out.print("<input type='text' name='message' readOnly='true' value='" + message + "'/>");
				out.print("<input type='text' name='user' readOnly='true' value='" + user + "'/>");
				out.print("<input type='submit' value='Click again to Send Message' />");
				out.print("</form>");
			}
			out.print("</body></html>");
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}
