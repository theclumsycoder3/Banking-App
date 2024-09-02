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
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String registerDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username = "system";
	private static final String password = "mudmud";
   
    public LoginServlet() {
        super();
       
    }   

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		String name=request.getParameter("name");
		String pass=request.getParameter("pass");
		
		try {
			Class.forName(registerDriver);
			Connection con= DriverManager.getConnection(url,username,password);
			
			String query="select * from users where username=? and password=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1,name);
			ps.setString(2, pass);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				out.println("<h1>Amount added Sucessfully !</h1>");	
				RequestDispatcher rd=request.getRequestDispatcher("success.html");
				rd.forward(request, response);	
			}else {
				out.println("Invalid credentials! Try again!");
				RequestDispatcher rd=request.getRequestDispatcher("display.html");
				rd.include(request, response);
			}
			
			con.close();
			out.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
