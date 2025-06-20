package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.RouteBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class RouteModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_route");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(RouteBean bean) throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_route values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
		pstmt.setInt(4, bean.getAllowedSpeed());
		pstmt.setString(5, bean.getVehicleId());

		int i = pstmt.executeUpdate();
		System.out.println("Route Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(RouteBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_route where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Route delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(RouteBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_route set name = ?, startDate = ?, allowedSpeed = ?, vehicleId = ? where id = ?");

		pstmt.setString(1, bean.getName());
		pstmt.setDate(2, new java.sql.Date(bean.getStartDate().getTime()));
		pstmt.setInt(3, bean.getAllowedSpeed());
		pstmt.setString(5, bean.getVehicleId());

		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("route update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public RouteBean findByPK(long pk) throws Exception {

		String sql = "select * from st_route where id = ?";
		RouteBean bean = null;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new RouteBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setStartDate(rs.getDate(3));
			bean.setAllowedSpeed(rs.getInt(4));
			bean.setVehicleId(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(RouteBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_route where 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}
			if (bean.getStartDate() != null && bean.getStartDate().getTime() > 0) {
				Date d = new Date(bean.getStartDate().getTime());
				sql.append(" AND startDate = '" + d + "'");
				System.out.println("done");
			}
			if (bean.getAllowedSpeed() > 0) {
				sql.append(" AND allowedSpeed like '" + bean.getAllowedSpeed() + "%'");
			}

			if (bean.getVehicleId() != null && bean.getVehicleId().length() > 0) {
				sql.append(" AND vehicleId like '" + bean.getVehicleId() + "%'");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new RouteBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setStartDate(rs.getDate(3));
			bean.setAllowedSpeed(rs.getInt(4));
			bean.setVehicleId(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_route");

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_route", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			RouteBean bean = new RouteBean();

			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setStartDate(rs.getDate(3));
			bean.setAllowedSpeed(rs.getInt(4));
			bean.setVehicleId(rs.getString(5));
			list.add(bean);

		}

		rs.close();

		return list;
	}

}
