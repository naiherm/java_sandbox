package timerApp;

import java.io.Serializable;

/**
 * 時刻を文字列表現として取り扱うためのユーティリティクラス
 * @author Med.
 */
public class Time implements Serializable{
	private static final long serialVersionUID = 1L;
	private int hour = 0;
	private int minu = 0;
	public Time() {
		;
	}
	public Time( int hour, int minu ) {
		this.hour = hour;
		this.minu = minu;
	}
	public int getHour() {
		return this.hour;
	}
	public void setHour(int hour) {
		if( hour < 0 ) {
			return;
		}
		if( hour > 24) {
			return;
		}
		this.hour = hour;
	}
	public int getMinu() {
		return this.minu;
	}
	public void setMinu(int minu) {
		if( minu < 0 ) {
			return;
		}
		if( minu > 60) {
			return;
		}
		this.minu = minu;
	}
	public long getTimeMill() {
		return (((hour * 60) + minu ) * 60 ) * 1000;
	}
	/**
	 * 00:00 形式の時刻表現文字列 を取得します。
	 * @return 
	 */
	public String getTimeString() {
		return String.format( "%1$02d:%2$02d", this.hour, this.minu );
	}
	/**
	 * 時刻文字列を元に、時刻値を更新します
	 * @param str 00:00 形式の時刻表現文字列
	 */
	public void setTimeString(String str) {
		if( str == null ) {
			return;
		}
		str = str.trim();
		String[] time = str.split(":");
		if( time.length < 2 ) {
			// フォーマット異常は無視
			return;
		}
		try {
			this.setHour( Integer.parseInt( time[0] ));
			this.setMinu( Integer.parseInt( time[1] ));
		}catch (Exception e) {
			// 異常は無視
			return;
		}
	}
}
