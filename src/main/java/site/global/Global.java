package site.global;

import java.sql.Connection;

import site.utli.JdbcUtlis;

/**
 * 存储公共变量
 */
public class Global {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/warehouse2021?characterEncoding=UTF-8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
	public static final String USER = "root";
	public static final String PASSWORD = "123456";

	/**
	 * 获取数据库连接
	 */
	public static Connection getConnection() {
		return JdbcUtlis.getConnection(Global.DRIVER, Global.JDBC_URL, Global.USER, Global.PASSWORD);
	}
}
