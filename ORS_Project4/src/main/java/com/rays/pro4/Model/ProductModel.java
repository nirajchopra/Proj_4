
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

public class ProductModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_prod");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(ProductBean bean) throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_prod values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getCustomerName());
		pstmt.setString(3, bean.getProductName());
		pstmt.setInt(4, bean.getProductPrice());
		pstmt.setDate(5, new java.sql.Date(bean.getPruchaseDate().getTime()));

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(ProductBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_prod where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(ProductBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_prod set customerName = ?, productName = ?, productPrice = ?, pruchaseDate = ? where id = ?");

		pstmt.setString(1, bean.getCustomerName());
		pstmt.setString(2, bean.getProductName());
		pstmt.setInt(3, bean.getProductPrice());
		pstmt.setDate(4, new java.sql.Date(bean.getPruchaseDate().getTime()));
		
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public ProductBean findByPK(long pk) throws Exception {

		String sql = "select * from st_prod where id = ?";
		ProductBean bean = null;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ProductBean();
			bean.setId(rs.getLong(1));
			bean.setCustomerName(rs.getString(2));
			bean.setProductName(rs.getString(3));
			bean.setProductPrice(rs.getInt(4));
			bean.setPruchaseDate(rs.getDate(5));

		}

		rs.close();

		return bean;
	}

	public List search(ProductBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_prod where 1=1");
		if (bean != null) {

			if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
				sql.append(" AND customerName like '" + bean.getCustomerName() + "%'");
			}

			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" AND ProductName like '" + bean.getProductName() + "%'");
			}

			if (bean.getProductPrice()  > 0) {
				sql.append(" AND productPrice like '" + bean.getProductPrice() + "%'");
			}

			if (bean.getPruchaseDate() != null && bean.getPruchaseDate().getTime() > 0) {
				Date d = new Date(bean.getPruchaseDate().getTime());
				sql.append(" AND pruchaseDate = '" + d + "'");
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

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ProductBean();
			bean.setId(rs.getLong(1));
			bean.setCustomerName(rs.getString(2));
			bean.setProductName(rs.getString(3));
			bean.setProductPrice(rs.getInt(4));
			bean.setPruchaseDate(rs.getDate(5));
			

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_prod");

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st_prod", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			ProductBean bean = new ProductBean();

			bean.setId(rs.getLong(1));
			bean.setCustomerName(rs.getString(2));
			bean.setProductName(rs.getString(3));
			bean.setProductPrice(rs.getInt(4));
			bean.setPruchaseDate(rs.getDate(5));
			

			list.add(bean);

		}

		rs.close();

		return list;
	}

	public void delete(VehicleBean deletebean) {
		// TODO Auto-generated method stub
		
	}

	public List search(VehicleBean bean, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
