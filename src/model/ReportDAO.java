package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements BaseDAO {

	/**
	 * レポート提出
	 * @param acountId レポート提出者識別子
	 * @param rep レポートデータ
	 * @return 提出に成功した場合 true
	 */
	public static boolean submit( int acountId, Report rep ) {
		
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = " insert into report (aid, day, todayProg, finalProg, complete, remains, comments, doing, tcatid ) " + 
					" values( ?, current_date, ?, ?, ?, ?, ?, ?, ? )"; 
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, acountId );
			stat.setInt(2, rep.getTodayProgress() );
			stat.setInt(3, rep.getFinalProgress() );
			stat.setString(4, rep.getComplete() );
			stat.setString(5, rep.getRemains() );
			stat.setString(6, rep.getComment() );
			stat.setString(7, rep.getDoing() );
			stat.setInt(8, rep.getCategoryID() );
			
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
	/**
	 * 日報を更新します。
	 * @param accountId アカウント一意識別子
	 * @param rep レポート
	 * @return 更新に成功した場合 true
	 */
	public static boolean updateReport(int accountId, Report rep) {
		return false;
	}
	/**
	 * 提出済みの日報一覧を取得します
	 * 日付降順(最新の登録情報を先に表示)
	 * @param accountId アカウントID
	 * @return 日報一覧
	 */
	public static List<Report> getlist( int accountId ){
		List<Report> list = new ArrayList<Report>();
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
					CS,
					USER,
					PASS );
			String sql = " select * from " + 
					" (SELECT day, todayProg, finalProg, complete, remains, comments, doing, tcatid " + 
					" FROM report  " + 
					" WHERE aid = ?  " + 
					" ) as r " + 
					" join tcategory as t  " + 
					" on r.tcatid = t.id  " + 
					" order by day desc "; 
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, accountId);
			
			ResultSet res = stat.executeQuery();
			
			
			while( res.next() ) {
				list.add(
						new Report(
								res.getInt("tcatid"), 
								res.getString("doing"), 
								res.getInt("todayProg"), 
								res.getInt("finalProg"), 
								res.getString("complete"), 
								res.getString("remains"), 
								res.getString("comments"),
								res.getDate("day"),
								res.getString("t.caption") ));
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
