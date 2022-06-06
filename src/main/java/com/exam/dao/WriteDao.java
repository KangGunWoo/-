package com.exam.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.exam.dto.MycartDto;
import com.exam.dto.WriteDto;

import oracle.sql.DATE;


public class WriteDao {
	
	private static WriteDao wDao;
	private Connection con = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int result;
	Dao dao = new Dao();
	public static synchronized WriteDao getInstance() {
		if (wDao == null) {
			wDao = new WriteDao();
		}
		return wDao;
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
	public int join(WriteDto wDto) {
		System.out.println(con);
		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		query.append("insert into Write").append(" values (Write_ID.NEXTVAL,?, ?,TO_CHAR(sysdate,'RRRR-MM-DD HH24:MI:SS'), ?,?)");
		System.out.println(query.toString());
		try {
			int a =0;
			pstmt = con.prepareStatement(query.toString());
		
			pstmt.setString(1, wDto.getTitle());
			pstmt.setString(2, wDto.getContent());
			pstmt.setInt(3, wDto.getHit());
			pstmt.setString(4, wDto.getWriter());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		
			e.printStackTrace();
		
	
		} finally {
			this.close(con, pstmt, null);
		}
		
		
		return result;
	}
	public List<WriteDto> selectList(){
		List<WriteDto> list = new ArrayList<>();
		
		try {
			Connection conn = this.dao.getConnection();
			String sql ="SElECT * FROM Write order by idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				WriteDto writedto = new WriteDto();
			
			
				writedto.setIdx(rs.getInt("idx"));
				writedto.setTitle(rs.getString("title"));
				writedto.setContent(rs.getString("content"));
				writedto.setData(rs.getDate("data"));
				writedto.setHit(rs.getInt("hit"));
				writedto.setWriter(rs.getString("writer"));
				list.add(writedto);
				System.out.println(rs.getDate("data")+"데이터값은 과연?");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	public int deleteper(WriteDto wDto) {
		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		int idx = wDto.getIdx();

	
		query.append("delete Write where idx="+idx);
	
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
	public int getCount(){
		int count = 0;
		String sql = "select count(*) from Write";
		try {
			con = dao.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(con, pstmt, null);
		}
		return count; // 총 레코드 수 리턴
	}
	public int Update(WriteDto wDto){
		Connection con = this.dao.getConnection();
		StringBuffer query = new StringBuffer();
		query.append("UPDATE write SET title=?, CONTENT=? WHERE IDX=?");

	
		

		try {
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, wDto.getTitle());
			pstmt.setString(2, wDto.getContent());
			pstmt.setInt(3, wDto.getIdx());
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
