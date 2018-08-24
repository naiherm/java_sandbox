package controller;

import java.io.IOException;

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
 * Servlet implementation class SubmitReport
 */
@WebServlet("/SubmitReport")
public class SubmitReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Account user =  (Account)session.getAttribute("user");
		if( user == null ) {
			// session 切れ
			System.out.println("session not found");
			response.sendRedirect("Index");
			return;
		}
		
		String catid = request.getParameter("catid");
		String doing = request.getParameter("doing");
		String tProg = request.getParameter("todayProgress");
		String fProg = request.getParameter("finalProgress");
		String complete = request.getParameter("complete");
		String remains = request.getParameter("remains");
		String comment = request.getParameter("comment");
		
		Report rep = null;
		int categoryID = 0;
		int tProgress = 0;
		int fProgress = 0;
		
		try {
			categoryID = Integer.parseInt(catid);
			tProgress = Integer.parseInt(tProg);
			fProgress = Integer.parseInt(fProg);

			rep = new Report(categoryID, doing, tProgress, fProgress, complete, remains, comment);
			
			if(		doing == null || doing.equals("")
					||	complete == null || complete.equals("")
					||	remains == null || remains.equals("")
					||	comment == null || comment.equals("") ) {

				// 空欄チェックに通過しなかった場合
				request.setAttribute("msg", "未記入の項目があります");
				throw new NullPointerException();
			}
			
			boolean result = ReportDAO.submit(user.getId(), rep );
			
			if( result ) {
				// 登録成功
				request.setAttribute("msg", "正常に登録されました<br />ログアウトします<br />");
				
				RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/logout.jsp");
				disp.forward(request, response);
				return;
			}else {
				System.out.println("NG");
				request.setAttribute("msg", "登録に失敗しました");
			}
			
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			//e.printStackTrace();
		}
		
		// 登録できなかった場合の復帰ルート
		if( rep == null ) {
			// 復元可能な情報は回収
			rep = new Report();
			rep.setComment(comment);
			rep.setComplete(complete);
			rep.setDoing(doing);
			rep.setRemains(remains);
		}
		// やり直しのために登録画面に戻す。
		request.setAttribute("report", rep);
		RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/main.jsp");
		disp.forward(request, response);
		return;
	
	}

}
