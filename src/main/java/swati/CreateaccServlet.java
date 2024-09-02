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


public class CreateaccServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String registerDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username = "system";
	private static final String password = "mudmud";

    public CreateaccServlet() {
        super();
    
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		int bal = Integer.parseInt(request.getParameter("bal"));
		
		try {
			Class.forName(registerDriver);
			Connection con= DriverManager.getConnection(url,username,password);
			
			String query="insert into account values (?, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1,num);
			ps.setString(2, name);
			ps.setInt(3, bal);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows>0) {
				out.println("<h1>Account created Sucessfully !</h1>");	
				RequestDispatcher rd=request.getRequestDispatcher("success.html");
				rd.include(request, response);		
			} else {
				out.println("<h1>Account Created Failed! Try again</h1>");
				RequestDispatcher rd=request.getRequestDispatcher("createaccount.html");
				rd.forward(request, response);

			}
			
			con.close();
			out.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
