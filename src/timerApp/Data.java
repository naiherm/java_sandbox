package timerApp;

import java.io.Serializable;
import java.util.Calendar;

public class Data implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final int READY = 2;	//開始直前時刻(分)
	private final int LIMIT = 10;	//終了直前時刻(分)
	
	/**
	 * 見出し文字
	 */
	private String caption;
	/**
	 * 開始時刻
	 */
	private Time st;
	/**
	 * 終了時刻
	 */
	private Time ed;
	/**
	 * 使用/不使用フラグ
	 */
	private boolean enable;
	
	
	/**
	 * シリアライザ用 デフォルトコンストラクタ
	 */
	public Data() {
		this.caption = "no caption";
		this.st = new Time();
		this.ed = new Time();
		this.enable = false;
	}
	/**
	 * コンストラクタ
	 * @param caption 見出し文字
	 * @param st 開始時刻
	 * @param ed 終了時刻
	 * @param enable 使用/不使用フラグ
	 */
	public Data( String caption, 
			Time st,
			Time ed,
			boolean enable ) {
		this.caption = caption;
		this.st = st;
		this.ed = ed;
		this.enable = enable;
	}
	/**
	 * 時分秒 をミリ秒単位で取得します
	 * @param cal 対象となる時刻が記録されているカレンダー
	 * @return ミリ秒単位での時分秒
	 */
	private static long getNow( Calendar cal ) {
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minu = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		return (((hour * 60) + minu) * 60 + sec) * 1000;
	}
	/**
	 * 開始直前か
	 * @param cal 対象となる時刻が記録されているカレンダー
	 * @return 開始直前なら true
	 */
	public boolean isReady( Calendar cal ) {
		return st.getTimeMill() - (READY * 60 * 1000) <= getNow( cal );  
	}
	/**
	 * 終了済みか
	 * @param cal 対象となる時刻が記録されているカレンダー
	 * @return 終了済みの場合 true
	 */
	public boolean isOver( Calendar cal ) {
//		if( !this.enable ) {
//			return true;
//		}
		return ed.getTimeMill() <= getNow( cal ); 
	}
	/**
	 * 終了時間直前の予定刻を超えたか
	 * @param cal 対象となる時刻が記録されているカレンダー
	 * @return 予定刻を超えた場合 true
	 */
	public boolean isLimit(Calendar cal ) {
		if( !inTime(cal) ) {
			return false;
		}
		return ed.getTimeMill() - (LIMIT * 60 * 1000) <= getNow( cal ); 
	}
	/**
	 * 範囲時間内か
	 * @param cal 対象となる時刻が記録されているカレンダー
	 * @return 範囲時間内の場合 true
	 */
	public boolean inTime( Calendar cal ) {
		long now = getNow(cal);
		if( st.getTimeMill() > now ) {
			return false;
		}
		if( ed.getTimeMill() < now ) {
			return false;
		}
		return true;
	}
	/**
	 * 見出し文字取得
	 * @return 見出し文字
	 */
	public String getCaption() {
		return this.caption;
	}
	/**
	 * 見出し文字設定
	 * @param caption 設定する文字
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * 開始時刻取得
	 * @return 開始時刻
	 */
	public Time getSt() {
		return this.st;
	}
	/**
	 * 
	 * @param st
	 */
	public void setSt(Time st) {
		this.st = st;
	}
	public Time getEd() {
		return this.ed;
	}
	public void setEd(Time ed) {
		this.ed = ed;
	}
	public boolean isEnable() {
		return this.enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 開始時刻文字列の取得
	 * @return 00:00 の形式 の開始時刻文字列
	 */
	public String getStartTime() {
		return this.st.getTimeString();
	}
	/**
	 * 終了時刻文字列の取得
	 * @return 00:00 の形式 の終了時刻文字列
	 */
	public String getEndTime() {
		return this.ed.getTimeString();
	}
	/**
	 * 開始時刻文字列の設定
	 * @param aValue 00:00 の形式 の開始時刻文字列
	 */
	public void setStartTime(String aValue) {
		this.st.setTimeString(aValue);
	}
	/**
	 * 終了時刻文字列の設定
	 * @param aValue 00:00 の形式 の終了時刻文字列
	 */
	public void setEndTime(String aValue) {
		this.ed.setTimeString(aValue);
	}
	
}

