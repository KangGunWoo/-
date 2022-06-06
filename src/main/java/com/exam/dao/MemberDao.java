package com.exam.dao;


import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.dto.MemberDto;
import com.exam.dto.MycartDto;

public class MemberDao {
	private static MemberDao mDao;
	private Connection con = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int result;
	Dao dao = new Dao();
	private MemberDao() {
		
	}
	
	
	public static synchronized MemberDao getInstance() {
		if (mDao == null) {
			mDao = new MemberDao();
		}
		return mDao;
	}
	
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int join(MemberDto mDto) {
		System.out.println(con);
		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		query.append("insert into member").append(" values (?, ?, ?, ?)");
		System.out.println(query.toString());

		System.out.println(mDto.getPw());
		System.out.println(mDto.getName());
		System.out.println(mDto.getEmail());
		try {
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, mDto.getId());
			pstmt.setString(2, mDto.getPw());
			pstmt.setString(3, mDto.getName());
			pstmt.setString(4, mDto.getEmail());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		
	
		} finally {
			this.close(con, pstmt, null);
		}
		
		
		return result;
	}
	
	
public int login(String id, String pw) {
	con = this.dao.getConnection();
	StringBuffer query = new StringBuffer();
	query.append("SELECT pw").append(" FROM member").append(" WHERE ID = ?");
	try {
		pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if (rs.next()) {
			if(rs.getString("pw").equals(pw)) {
				return 1;
			} else {
				return 0;
			}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		this.close(con, pstmt, rs);
	}
	return -1;
}

}