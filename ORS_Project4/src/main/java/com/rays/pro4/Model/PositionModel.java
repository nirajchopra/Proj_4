package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.PositionBean;
import com.rays.pro4.Util.JDBCDataSource;

public class PositionModel {

	public int nextPk() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from position");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		return pk + 1;
	}

	public void add(PositionBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("insert into position values (?,?,?,?,?)");
		int pk = nextPk();

		pstmt.setLong(1, pk);
		System.out.println("jghhjjhhginnnn model" + bean.getDesignation());
		pstmt.setString(2, bean.getDesignation());
		pstmt.setDate(3, new java.sql.Date(bean.getOpeningDate().getTime()));
		pstmt.setString(4, bean.getRequiredExperience());
		pstmt.setString(5, bean.getCondition());
		int i = pstmt.executeUpdate();

		System.out.println("Data Inserted  Successfully !!!" + i);

	}

	public void update(PositionBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(
				"update position set designation = ?, opening_date = ?, required_experience = ?, condition = ? where id = ?");

		pstmt.setString(1, bean.getDesignation());
		pstmt.setDate(2, new java.sql.Date(bean.getOpeningDate().getTime()));
		pstmt.setString(3, bean.getRequiredExperience());
		pstmt.setString(4, bean.getCondition());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Data Updated Successfully..." + i);

	}

	public void delete(PositionBean bean) throws Exception {
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("delete from position where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Data Deleted Successfully..." + i);
	}

	public List search(PositionBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from position where 1=1");

		if (bean != null) {
			if (bean.getDesignation() != null && bean.getDesignation().length() > 0) {
				sql.append(" and designation like '" + bean.getDesignation() + "%'");
			}
			if (bean.getRequiredExperience() != null && bean.getRequiredExperience().length() > 0) {
				sql.append(" and required_experience like '" + bean.getRequiredExperience() + "%'");
			}
			if (bean.getCondition() != null && bean.getCondition().length() > 0) {
				sql.append(" and `condition` like '" + bean.getCondition() + "%'");
			}
			if (bean.getOpeningDate() != null && ((CharSequence) bean.getOpeningDate()).length() > 0) {
				sql.append(" and opening_date like '" + bean.getOpeningDate() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		System.out.println("sql ==>> " + sql.toString());

		System.out.println(sql.toString());

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		List list = new ArrayList();

		while (rs.next()) {

			bean = new PositionBean();

			bean.setId(rs.getLong(1));
			bean.setDesignation(rs.getString(2));
			bean.setOpeningDate(rs.getDate(3));
			bean.setRequiredExperience(rs.getString(4));
			bean.setCondition(rs.getString(5));
			
			list.add(bean);
		}
		return list;
	}

	public PositionBean findByPk(long id) throws Exception {
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from position where id = ?");
		pstmt.setLong(1, id);

		ResultSet rs = pstmt.executeQuery();

		PositionBean bean = null; // Initialize the bean to null

		if (rs.next()) {
			bean = new PositionBean(); // Create a new instance of positionBean when data is found

			bean.setId(rs.getLong(1));
			bean.setDesignation(rs.getString(2));
			bean.setOpeningDate(rs.getDate(3));
			bean.setRequiredExperience(rs.getString(4));
			bean.setCondition(rs.getString(5));
			
		}

		JDBCDataSource.closeConnection(conn);

		return bean; // Returns null if no record is found, otherwise returns the bean
	}

	public PositionBean findByDesignation(String designation) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from position where designation = ?");

		pstmt.setString(1, designation);

		ResultSet rs = pstmt.executeQuery();

		PositionBean bean = null;

		while (rs.next()) {
			bean = new PositionBean();

			bean.setId(rs.getLong(1));
			bean.setDesignation(rs.getString(2));
			bean.setOpeningDate(rs.getDate(3));
			bean.setRequiredExperience(rs.getString(4));
			bean.setCondition(rs.getString(5));
			
		}

		JDBCDataSource.closeConnection(conn);

		return bean;
	}

	public List list() throws Exception {
		return search(null, 0, 0);
	}

	public void delete(int int1) {
		// TODO Auto-generated method stub

	}

}