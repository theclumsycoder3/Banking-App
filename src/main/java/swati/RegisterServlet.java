package swati;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String registerDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username = "system";
	private static final String password = "mudmud";
  
    public RegisterServlet() {
        super();
       
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		String name=request.getParameter("name");
		String pass=request.getParameter("pass");
		String crpass=request.getParameter("crpass");
		
		if(pass.equals(crpass)){
			try {
				Class.forName(registerDriver);
				Connection con= DriverManager.getConnection(url,username,password);
				
				String query="insert into users values(?,?)";
				
				PreparedStatement ps=con.prepareStatement(query);
				
				ps.setString(1,name);
				ps.setString(2, pass);
				
				int affectedRows = ps.executeUpdate();
				
				if (affectedRows>0) {
					out.println("<h1>Sucessfully Registered!</h1>");	
					RequestDispatcher rd=request.getRequestDispatcher("login.html");
					rd.include(request, response);
				
				} else {
					out.println("<h1>Registration Failed!</h1>");	
					RequestDispatcher rd=request.getRequestDispatcher("register.html");
					rd.forward(request, response);
				}
				
				con.close();
				out.close();
						
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	}
