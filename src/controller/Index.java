package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.Account;
import model.AccountDAO;
import model.Task;
import model.TaskDAO;
import model.Team;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * トップ画面用
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 新規登録用 チーム選択肢
		List<Team> team = AccountDAO.getTeamlist();
		request.setAttribute("team", team);
		
		RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/view/index.jsp");
		disp.forward(request, response);
		return;
	}

	/**
	 * トップ画面からの遷移用<br>
	 * 新規アカウント作成またはログイン処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 必須情報の取得
		String lid = request.getParameter("lid");
		String pass = request.getParameter("pass");
		
		if( lid == null || lid.equals("") || pass == null || pass.equals("")) {
			request.setAttribute("msg", "ログインできませんでした");
			doGet(request, response);
			return;
		}
		
		// 分岐
		String act = request.getParameter("act");
		if( act != null ) {
			switch( act ) {
			case "create":{
				String name = request.getParameter("name");
				String stid = request.getParameter("tid");
				if( name == null || name.equals("")) {
					// 空白はNG
					throw new NullPointerException();
				}
				try {
					int tid = Integer.parseInt(stid);
					if( !AccountDAO.add( lid, pass, name, tid ) ) {
						break;
					}
				}catch (NumberFormatException e) {
					request.setAttribute("msg", "アカウントの作成ができませんでした<br />チームの選択が不正です");
					e.printStackTrace();
				}catch (NullPointerException e) {
					request.setAttribute("msg", "アカウントの作成ができませんでした");
					e.printStackTrace();
				}
			}	// break; fall down - continue login task...
			case "login":{
				Account user = AccountDAO.select(lid, pass);
				if( user != null ) {
					
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					System.out.println("session saved user");
					
					RequestDispatcher disp = request.getRequestDispatcher("Main");
					disp.forward(request, response);
					return;
				}else {
					request.setAttribute("msg", "ログインに失敗しました");
				}
			}	break;
			}
		}		
		System.out.println("not login");
		doGet(request, response);
		return;
	}

}
