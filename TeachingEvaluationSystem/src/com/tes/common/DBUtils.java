package com.tes.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
* @author xl_hsj
* @version 创建时间：2019年11月22日 下午3:41:57
* @ClassName 类名称
* @Description 类描述
*/
public class DBUtils {
	static Connection conn;
	private static DataSource ds;
//	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	static {
		ds = new ComboPooledDataSource();
	}
	
	public static DataSource getDataSource(){
		return ds;
	}
	/**
	 * 当DBUtils需要手动控制事务时，调用该方法获得一个连接
	 * @return
	 * @throws SQLException
	 */
//	public static Connection getConnection() throws SQLException {
//		Connection con = tl.get();
//		if (con == null) {
//			con = ds.getConnection();
//			tl.set(con);
//		}
//		return con;
//	}
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException {
		Connection con = getConnection();
		if (con != null)
			con.setAutoCommit(false);
	}
	/**
	 * 从ThreadLocal中释放并且关闭Connection,并结束事务
	 * @throws SQLException
	 */
	public static void releaseAndCloseConnection() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.commit();
//			tl.remove();
			con.close();
		}
	}
	/**
	 * 事务回滚
	 * @throws SQLException 
	 */
	public static void rollback() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.rollback();
		}
	}
	/**
	 * 获取数据库连接对象
	 * @return
	 */
	public static Connection getConn(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;"
					+ "DatabaseName=JavaWebTest";
			conn = DriverManager.getConnection(url, "sa", "123456");//获取连接
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 关闭数据库连接
	 */
	public static void closeConn(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭数据库连接
	 */
	public static void close(PreparedStatement stmt, Connection conn){
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭PreparedStatment、ResultSet
	 * @param result
	 * @param stmt
	 */
	public static void close(ResultSet result, 
			PreparedStatement stmt) {
		try {
			if (result != null) {
				result.close();				
			}
			if (stmt != null) {
				stmt.close();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void close(ResultSet result, 
			PreparedStatement stmt, Connection connection) {
		try {
			if (result != null) {
				result.close();				
			}
			close(stmt, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public static void closeResultAndConn(ResultSet resultSet, Connection conn){
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/** 调用此方法可以完成对数据库的增、删、改操作
	 * @return
	 */
	public static int executeUpdate(String sql, Object[] params) {
		Connection connection = getConn();
		PreparedStatement pStatement = null;
		int rs = 0;
		try {
			pStatement = connection.prepareStatement(sql);//预编译SQL语句
			//设置参数
			for (int i=1; i<=params.length; i++){
				pStatement.setObject(i, params[i-1]);
			}
			rs = pStatement.executeUpdate();//执行SQL语句
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(null, pStatement);
			closeConn(connection);
		}
		return rs;
	}
	/**
	 * 此方法用来查询
	 * @param sql  查询语句
	 * @param params  Sql语句中的问号（占位符）
	 * @param conn  数据库连接
	 * @return
	 */
	public static ResultSet executeQuery(String sql, Object[] params, Connection conn){
		ResultSet result = null;
		PreparedStatement pstmt = null;
		try {
			//创建语句对象
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i=0; i<params.length; i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			result = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return result;
	}
	
	public static Object query(String sql, ResultSetHandler<?> rsh, Object[] params){
		Connection connection = getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Object obj = null;
		QueryRunner qr = new QueryRunner(getDataSource());
		
		try {
			obj = qr.query(sql, rsh, params);
//			stmt = connection.prepareStatement(sql);
//			for (int i=0; params != null && i<params.length; i++){
//				stmt.setObject(i+1, params[i]);
//			}
//			rs = stmt.executeQuery();
//			obj = rsh.handle(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, stmt, connection);
		}
		
		return obj;		
	}
	
	/**
	 * 查询，其结果集有多条记录,MapListHandler
	 * 将结果集每一行存储到Map集合,键:列名,值:数据
	 * Map集合过多,存储到List集合
	 * @param sqlString  sql语句
	 * @param params  条件值
	 * @return  查询结果
	 */
	public static List<Map<String,Object>> queryMapListHandler(String sqlString, Object params[]) {
		List<Map<String, Object>> listRes = null;
		  try {
		   QueryRunner qRunner = new QueryRunner(ds);
		   listRes = qRunner.query(sqlString, new MapListHandler(), params);
		  } catch (SQLException e) {   
		   // logger.error(e.getMessage());
		   e.printStackTrace();
		  } 
		  return listRes;
	}
	
	/**
	 * MapHandler
	 * 将结果集第一行数据,封装到Map集合中
	 * Map<键,值> 键:列名  值:这列的数据
	 * @param sqlString
	 * @param params
	 * @return
	 */
	public static Map<String, Object> queryMapHandler(String sqlString, Object params[]){
		Map<String, Object> map = null;
		QueryRunner qRunner = new QueryRunner(ds);
		try {
			map = qRunner.query(sqlString, new MapHandler(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 插入数据返回id
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int insertForIndex(String sql, Object[] params) {
		QueryRunner qRunner = new QueryRunner(ds);
		int result = 0;
		try {
			result = qRunner.insert(sql, new ScalarHandler<>(1), params);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return result;
	}
	/**
	 * 执行数据库的增、删、改操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object[] params) {
		int result = 0;
		QueryRunner qRunner = new QueryRunner(ds);
		try {
			result = qRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
