package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class VehicleModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from vehicles");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(VehicleBean bean) throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into vehicles values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getNumber());
		pstmt.setDate(3, new java.sql.Date(bean.getPurchaseDate().getTime()));
		pstmt.setDouble(4, bean.getInsuranceAmount());
		pstmt.setString(5, bean.getColour());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(VehicleBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from vehicles where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(VehicleBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update vehicles set number = ?, purchaseDate = ?, insuranceAmount = ?, colour = ? where id = ?");

		pstmt.setString(1, bean.getNumber());
		pstmt.setDate(2, new java.sql.Date(bean.getPurchaseDate().getTime()));
		pstmt.setDouble(3, bean.getInsuranceAmount());
		pstmt.setString(4, bean.getColour());

		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public VehicleBean findByPK(long pk) throws Exception {

		String sql = "select * from vehicles where id = ?";
		VehicleBean bean = null;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new VehicleBean();
			bean.setId(rs.getInt(1));
			bean.setNumber(rs.getString(2));
			bean.setPurchaseDate(rs.getDate(3));
			bean.setInsuranceAmount(rs.getDouble(4));
			bean.setColour(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(VehicleBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from vehicles where 1=1");
		if (bean != null) {

			if (bean.getNumber() != null && bean.getNumber().length() > 0) {
				sql.append(" AND Number like '" + bean.getNumber() + "%'");
			}

			if (bean.getPurchaseDate() != null && bean.getPurchaseDate().getTime() > 0) {
				Date d = new Date(bean.getPurchaseDate().getTime());
				sql.append(" AND purchaseDate = '" + d + "'");

				if (bean.getInsuranceAmount() > 0) {
					sql.append(" AND InsuranceAmount like '" + bean.getInsuranceAmount() + "%'");
				}

				if (bean.getColour() != null && bean.getColour().length() > 0) {
					sql.append(" AND Colour like '" + bean.getColour() + "%'");
				}

				System.out.println("done");
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

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new VehicleBean();
			bean.setId(rs.getInt(1));
			bean.setNumber(rs.getString(2));
			bean.setPurchaseDate(rs.getDate(3));
			bean.setInsuranceAmount(rs.getDouble(4));
			bean.setColour(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from vehicles");

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			VehicleBean bean = new VehicleBean();

			bean.setId(rs.getInt(1));
			bean.setNumber(rs.getString(2));
			bean.setPurchaseDate(rs.getDate(3));
			bean.setInsuranceAmount(rs.getDouble(4));
			bean.setColour(rs.getString(5));


			list.add(bean);

		}

		rs.close();

		return list;
	}

	public VehicleBean findByPk(long pk) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "root", "root");
		PreparedStatement ps = conn.prepareStatement("select * from vehicles where id = ?");

		ps.setLong(1, pk);
		ResultSet rs = ps.executeQuery();

		VehicleBean bean = null;
		if (rs.next()) {
			bean = new VehicleBean();
			bean.setId(rs.getInt(1));
			bean.setNumber(rs.getString(2));
			bean.setPurchaseDate(rs.getDate(3));
			bean.setInsuranceAmount(rs.getDouble(4));
			bean.setColour(rs.getString(5));
		}
		rs.close();
		return bean;

	}

	
}