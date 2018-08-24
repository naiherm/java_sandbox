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
import model.Report;
import model.ReportDAO;

/**
 * Servlet implementation class Log
 */
@WebServlet("/LogShow")
public class LogShow extends HttpServlet {
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
		
		// 過去ログを取得
		List<Report> reps = ReportDAO.getlist(user.getId());
		request.setAttribute("reps", reps);
		
		RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/log.jsp");
		disp.forward(request, response);
		return;
	}

}
