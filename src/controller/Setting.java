package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Setting
 */
@WebServlet("/Setting")
public class Setting extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String act = request.getParameter("act");
		if( act != null) {
			switch( act ) {
			case "logout":{
				// ログアウト処理
				HttpSession session = request.getSession();
				session.removeAttribute("user");
				session.removeAttribute("team");
				
				request.setAttribute("msg", "ログアウトしました");
				RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/logout.jsp");
				disp.forward(request, response);
				return;
			}	//break;
			case "change":{
				//TODO: 変更画面へ
			}	break;
			}
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/index.jsp");
		disp.forward(request, response);
		return;
		
	}

}
