package model;

import java.io.Serializable;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String lid;
	private String name;
	private String pass;
	private int teamId;
	private String teamCaption;
	
	/**
	 * JavaBeans用
	 */
	public Account() {
	}
	/**
	 * システムログインユーザアカウント のコンストラクタ
	 * @param id primary key
	 * @param lid ログインID
	 * @param pass パスワード
	 * @param name 名前
	 * @param teamId チームID
	 * @param teamCaption チーム表記文字
	 */
	public Account( int id, String lid, String pass, String name, int teamId, String teamCaption ) {
		this.id = id;
		this.lid = lid;
		this.pass = pass;
		this.name = name;
		this.teamId = teamId;
		this.teamCaption = teamCaption;
	}
	
	public int getId() {
		return this.id;
	}
	public String getLid() {
		return this.lid;
	}
	public String getPass() {
		return this.pass;
	}
	public String getName() {
		return this.name;
	}
	public int getTeamId() {
		return this.teamId;
	}
	public String getTeamCaption() {
		return this.teamCaption;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public void setTeamCaption(String teamCaption) {
		this.teamCaption = teamCaption;
	}

}
