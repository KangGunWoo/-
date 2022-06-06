package com.exam.dao;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.naming.factory.MailSessionFactory;


import com.exam.dto.MemberDto;
import com.exam.dto.MycartDto;

public class MycartDao {


	private static MycartDao cDao;
	private Connection con = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int result;
	Dao dao = new Dao();

	public static synchronized MycartDao getInstance() {
		if (cDao == null) {
			cDao = new MycartDao();
		}
		return cDao;
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
	public int join(MycartDto cDto) {
		System.out.println(con);

		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		query.append("insert into cart").append(" values (?,?, ?, ?, ?)");
		System.out.println(query.toString());

		try {
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setInt(1, cDto.getDelNum());
			pstmt.setInt(2, 1);
			pstmt.setString(3, cDto.getImg());
			pstmt.setString(4, cDto.getTitle());
			pstmt.setString(5, cDto.getPrice());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		
			e.printStackTrace();
		
	
		} finally {
			this.close(con, pstmt, null);
		}
		
		
		return result;
	}
	public List<MycartDto> selectList(){
		List<MycartDto> list = new ArrayList<>();
		
		try {
			Connection conn = this.dao.getConnection();
			String sql ="SElECT * FROM cart";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MycartDto cartdto = new MycartDto();
			
			
				cartdto.setNum(rs.getInt("Num"));
				cartdto.setImg(rs.getString("img"));
				cartdto.setTitle(rs.getString("title"));
				cartdto.setPrice(rs.getString("price"));
				
				list.add(cartdto);
				System.out.println(list.size() + "¥ı«œ±‚");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	public int delete(MycartDto cDto) {
		System.out.println(con+"µÙ∏Æ∆Æ ø¨∞·");

		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		query.append("delete cart where Num=1");
		System.out.println(query.toString());

		try {
			pstmt = con.prepareStatement(query.toString());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		
			e.printStackTrace();
		
	
		} finally {
			this.close(con, pstmt, null);
		}
		
		
		return result;
	}

	public int deleteper(MycartDto cDto) {
		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		int Del = cDto.getDelNum();
	System.out.println(Del+"µ®");
	
	
		query.append("delete cart where DelNum="+Del);
	
		System.out.println(query.toString());

		try {
			pstmt = con.prepareStatement(query.toString());
		
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println(e);
		
			e.printStackTrace();
		
	
		} finally {
			this.close(con, pstmt, null);
		}
		
		
		return result;
	}
	}

