package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
/**
 * レポート登録用データ JavaBeans対応
 * @author Med
 */
public class Report implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int categoryID;
	private String doing;
	private int todayProgress;
	private int finalProgress;
	private String complete;
	private String remains;
	private String comment;
	private Date day;
	private String categoryName;
	
	/**
	 * デフォルトコンストラクタ 。文字列空白を設定。
	 */
	public Report() {
		this.doing = "";
		this.complete = "";
		this.remains = "";
		this.comment = "";
	}
	/**
	 * データ登録用コンストラクタ
	 * @param categoryID タスクカテゴリID
	 * @param doing 本日作業内容
	 * @param tProgress 本日進捗度
	 * @param fProgress 全体進捗度
	 * @param complete 完成内容
	 * @param reamains 残作業内容
	 * @param comment 雑感コメント
	 */
	public Report(
			int categoryID,
			String doing,
			int tProgress,
			int fProgress,
			String complete,
			String reamains,
			String comment ) {
		this.categoryID = categoryID;
		this.doing = doing;
		this.todayProgress = tProgress;
		this.finalProgress = fProgress;
		this.complete = complete;
		this.remains = reamains;
		this.comment = comment;
	}

	/**
	 * 過去ログ取得用コンストラクタ
	 * @param categoryID タスクカテゴリID
	 * @param doing 本日作業内容
	 * @param tProgress 本日進捗度
	 * @param fProgress 全体進捗度
	 * @param complete 完成内容
	 * @param reamains 残作業内容
	 * @param comment 雑感コメント
	 * @param day 日付
	 * @param categoryName カテゴリ表示名
	 */
	public Report(
			int categoryID,
			String doing,
			int tProgress,
			int fProgress,
			String complete,
			String reamains,
			String comment,
			Date day,
			String categoryName
			) {
		this.categoryID = categoryID;
		this.doing = doing;
		this.todayProgress = tProgress;
		this.finalProgress = fProgress;
		this.complete = complete;
		this.remains = reamains;
		this.comment = comment;
		
		this.day = day;
		this.categoryName = categoryName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getCategoryID() {
		return this.categoryID;
	}
	public String getDoing() {
		return this.doing;
	}
	public int getTodayProgress() {
		return this.todayProgress;
	}
	public int getFinalProgress() {
		return this.finalProgress;
	}
	public String getComplete() {
		return this.complete;
	}
	public String getRemains() {
		return this.remains;
	}
	public String getComment() {
		return this.comment;
	}
	public Date getDay( ) {
		return this.day;
	}
	public String getCategoryName( ) {
		return this.categoryName;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public void setDoing(String doing) {
		this.doing = doing;
	}
	public void setTodayProgress(int todayProgress) {
		this.todayProgress = todayProgress;
	}
	public void setFinalProgress(int finalProgress) {
		this.finalProgress = finalProgress;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public void setRemains(String remains) {
		this.remains = remains;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public void setDay( Date day ) {
		this.day = day;
	}
	public void setCategoryName( String categoryName ) {
		this.categoryName = categoryName;
	}

	
}
