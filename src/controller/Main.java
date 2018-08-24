package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Task;
import model.TaskCategory;
import model.TaskDAO;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account user = (Account)session.getAttribute("user");
		if( user == null ) {
			// session 切れ
			System.out.println("session not found");
			response.sendRedirect("Index");
			return;
		}
		List<TaskCategory> tCate = TaskDAO.getCategory();
		session.setAttribute("tCate", tCate);
		System.out.println("session saved categorys");
		
		List<Task> tasks = TaskDAO.getAssingedTask( user.getId() );
		session.setAttribute("tasks", tasks);
		System.out.println("session saved tasks");
		
		// 各人の提出用画面へ		
		RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/main.jsp");
		disp.forward(request, response);
		return;
	}

}
