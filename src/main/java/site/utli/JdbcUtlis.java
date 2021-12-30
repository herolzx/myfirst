package site.utli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取数据库连接
 * 
 * @author 86176
 *
 */
public class JdbcUtlis {
	public static Connection getConnection(String dirver, String url, String user, String password) {
		try {
			Class.forName(dirver);
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据库连接失败");
		}
	}

	/**
	 * 关闭数据库
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
