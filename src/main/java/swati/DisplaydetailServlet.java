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

public class DisplaydetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String registerDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username = "system";
	private static final String password = "mudmud";
       
 
    public DisplaydetailServlet() {
        super();

    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		int accnum = Integer.parseInt(request.getParameter("num"));

		
		try {
			Class.forName(registerDriver);
			Connection con= DriverManager.getConnection(url,username,password);
			
			String query="select * from account where accnum=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1,accnum);
			
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int num=rs.getInt("accnum");
				out.println("<h1>Account number: "+num+"</h1>");
				String accname=rs.getString("accname");
				out.println("<h1>Account name: "+accname+"</h1>");
				int balance=rs.getInt("balance");
				out.println("<h1>Balance: "+balance+"</h1>");
				out.println("<a href='success.html'>Home page</a>");
			}else {
				out.println("<h1>Invalid credentials! Try again</h1>");
				RequestDispatcher rd=request.getRequestDispatcher("display.html");
				rd.forward(request, response);
			}
			
			
			con.close();
			out.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
