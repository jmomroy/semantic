package com.unsa.home;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import com.google.appengine.api.utils.SystemProperty;

import java.sql.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class WebappServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		
		String url = null;
		if (SystemProperty.environment.value() ==
			SystemProperty.Environment.Value.Production) {
			// Connecting from App Engine.
			// Load the class that provides the "jdbc:google:mysql://"
			// prefix.
			try {
				Class.forName("com.mysql.jdbc.GoogleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			url =
			"jdbc:google:mysql://genial-caster-784:bd-proyecto-final-pa/proyecto?user=root";
		} else {
			 // Connecting from an external network.
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url = "jdbc:mysql://173.194.83.76:3306/proyecto?user=root";
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String name="";
		try {
			ResultSet rs = conn.createStatement().executeQuery(
			"SELECT id, name FROM users WHERE username = '"+username+"' AND password = '"+password+"'");
		
			while (rs.next()) 
			{
				name = rs.getString(2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			resp.setContentType("text/html");
			resp.getWriter().println("<html>");
			resp.getWriter().println("<head>");
			resp.getWriter().println("</head>");
			resp.getWriter().println("Bienvenido, "+name);
			resp.getWriter().println("<body>");
			resp.getWriter().println("<p><a href='/'>Logout</a></p>");
			resp.getWriter().println("<form action = 'suma' method='POST'>");
			resp.getWriter().println("<input type ='text' name='suma'> + ");
			resp.getWriter().println("<input type ='text' name='sumando'> ");
			resp.getWriter().println("<input type ='submit' value='Total' name='submit'> ");
			resp.getWriter().println("</form>");
			resp.getWriter().println("</body>");
			resp.getWriter().println("</html>");
			
	}
}
