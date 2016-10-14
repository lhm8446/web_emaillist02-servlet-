package com.bit2016.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit2016.emaillist.vo.EmailListVo;

public class EmailListDao {
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection( url, "webdb", "webdb" );
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩실패 : " + e);
		}
		
		return conn;
	}
	public void insert(EmailListVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into emaillist values(emaillist_seq.nextval, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getLastname());
			pstmt.setString(2, vo.getFirstname());
			pstmt.setString(3, vo.getEmail());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		}finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
				if(conn !=null){
				conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}
	public List<EmailListVo> getList(){
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			stmt = conn.createStatement();
			
			String sql = "select no, last_name, first_name, email from emaillist order by no";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				Long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				
				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setLastname(lastName);
				vo.setFirstname(firstName);
				vo.setEmail(email);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("erro : " + e);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
				conn.close();
			}
			}catch(SQLException e){
				System.out.println("erro : " + e);
			}
		}
		
		return list;
	}
}
