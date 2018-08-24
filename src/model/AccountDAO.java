package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements BaseDAO {
	
	/**
	 * ユーザログイン
	 * @param lid ログイン名
	 * @param pass パスワード文字
	 * @return ログインできた場合はアカウント情報オブジェクトを返します。
	 */
	public static Account select(String lid, String pass) {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = " SELECT " + 
					"	 a.id, lid, name, pass, tid, caption " + 
					"	FROM " + 
					"	 account as a " + 
					"	 join team as t " + 
					"	 on t.id = a.tid " + 
					"	 where lid = ? and pass = ? ";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, lid);
			stat.setString(2, pass);
			ResultSet res = stat.executeQuery();
			
			while( res.next() ) {
				return new Account(
						res.getInt("a.id"),
						res.getString("lid"),
						res.getString("pass"),
						res.getString("name"),
						res.getInt("tid"),
						res.getString("caption") );
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( conn != null ) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * チーム一覧を取得します。 
	 * @return DBに登録済みのチーム一覧。
	 */
	public static List<Team> getTeamlist() {
		
		List<Team> list = new ArrayList<Team>();
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = " SELECT " + 
					"	 * " + 
					"	FROM " + 
					"	 team "; 
			PreparedStatement stat = conn.prepareStatement(sql);
			ResultSet res = stat.executeQuery();
			
			while( res.next() ) {
				list.add(new Team(
						res.getInt("id"),
						res.getString("caption")));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( conn != null ) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * アカウントを追加します。
	 * @param lid ログインID(文字列)
	 * @param pass パスワード
	 * @param name ユーザ名
	 * @param tid チームID
	 * @return 登録成功時 true
	 */
	public static boolean add(String lid, String pass, String name, int tid ) {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = "insert into account (lid, pass, name, tid) "
					+ "values( ?, ?, ?, ? )"; 
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, lid);
			stat.setString(2, pass);
			stat.setString(3, name);
			stat.setInt(4, tid);
			
			int result = stat.executeUpdate();
			return result == 1;
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( conn != null ) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
