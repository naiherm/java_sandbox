package model;

public interface BaseDAO {
	/**
	 * JDBCドライバ文字列
	 */
	static final String DRIVER = "com.mysql.jdbc.Driver";
	/**
	 * 接続文字列
	 */
	static final String CS = "jdbc:mysql://localhost:3306/test"
			+ "?useUnicode=true"
			+ "&characterEncoding=utf8"
			+ "&useSSL=false";
	/**
	 * ユーザ名
	 */
	static final String USER = "root";
	/**
	 * パスワード
	 */
	static final String PASS = "";
}
