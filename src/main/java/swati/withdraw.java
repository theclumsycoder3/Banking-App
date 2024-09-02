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


public class withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String registerDriver="oracle.jdbc.driver.OracleDriver";
	private static final String url="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username="system";
	private static final String password="mudmud";
   
    public withdraw() {
        super();
      
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		int num=Integer.parseInt(request.getParameter("num"));
		int amt=Integer.parseInt(request.getParameter("bal"));
		
		try {
			Class.forName(registerDriver);
			Connection con=DriverManager.getConnection(url,username,password);
			
			String query="update account set balance=balance-? where accnum=?";
			
			PreparedStatement ps =con.prepareStatement(query);
			
			ps.setInt(1,amt);
			ps.setInt(2,num);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows>0) {
				out.println("<h1>Amount withdrawn Sucessfully !</h1>");	
				RequestDispatcher rd=request.getRequestDispatcher("success.html");
				rd.include(request, response);		
			} else {
				out.println("<h1>Invalid credentials! Try again</h1>");
				RequestDispatcher rd=request.getRequestDispatcher("withdraw.html");
				rd.forward(request, response);

			}
			
			con.close();
			out.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		
		
	}
