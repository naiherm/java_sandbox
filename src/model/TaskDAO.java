package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements BaseDAO{

	/**
	 * タスクカテゴリ一覧を取得します。
	 * @return タスクカテゴリ一覧
	 */
	public static List<TaskCategory> getCategory() {
		
		List<TaskCategory> list = new ArrayList<TaskCategory>();
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
					"	 tcategory "
					+ " order by id"; 
			PreparedStatement stat = conn.prepareStatement(sql);
			ResultSet res = stat.executeQuery();
			
			while( res.next() ) {
				list.add(new TaskCategory(
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
	 * 指定されたアカウントに割り振られている タスクの一覧を取得します。
	 * @param accountId アカウント一意識別子
	 * @return タスク一覧
	 */
	public static List<Task> getAssingedTask(int accountId){
		List<Task> list = new ArrayList<Task>();
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = " select c.caption, t.caption, t.discription, t.priority, t.progress " + 
					" from " + 
					" (SELECT " + 
					"	 aid, tdid " + 
					"	FROM " + 
					"	 responsible " + 
					"	 where aid = ? " + 
					" ) as r " + 
					" join taskdetails as t " +
					" on r.tdid = t.id " + 
					" join tcategory as c " + 
					" on c.id = t.cateId" + 
					" order by t.priority asc, t.progress asc "; 
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, accountId);
			
			ResultSet res = stat.executeQuery();
			
			while( res.next() ) {
				list.add(new Task(
						res.getString("c.caption"),
						res.getString("t.caption"),
						res.getString("t.discription"),
						res.getInt("t.priority"),
						res.getInt("t.progress")
						));
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
}
