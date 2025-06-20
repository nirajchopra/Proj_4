package com.rays.proj4.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ProductModel;


/**
 * User Model Test classes.
 * 
 * @author Niraj Chopra
 *
 */
public class TestProductModel {

	public static void main(String[] args) throws Exception {
//		 testAdd();
//		 testDelete();
//		 testFindByPk();
//		 testUpdate();
//		testSearch();
//		 getList();
	}

	

	private static void getList() throws Exception {
		try {
			ProductBean bean = new ProductBean();
			ProductModel model = new ProductModel();
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test list fail ");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (ProductBean) it.next();
				System.out.println(bean.getId());
				System.out.print(bean.getCustomerName()) ;
				System.out.print(bean.getProductName());
				System.out.print(bean.getProductPrice());
				System.out.print(bean.getPruchaseDate());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	private static void testSearch() throws Exception {
		try {
			ProductBean bean = new ProductBean();
			ProductModel model = new ProductModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (ProductBean) it.next();
				System.out.println(bean.getId() + "    ");
				System.out.println(bean.getCustomerName() + "    ");
				System.out.println(bean.getProductName() + "    ");
				System.out.println(bean.getProductPrice() + "    ");
				System.out.println(bean.getPruchaseDate() + "    ");

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws Exception {
		try {
			ProductBean bean = new ProductBean();
			ProductModel model = new ProductModel();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
			bean = model.findByPK(5L);
			bean.setCustomerName("Anand Choudhary");
			bean.setProductName("Google Pixel");
			bean.setProductPrice(20000);
			bean.setPruchaseDate(sdf.parse("05-07-2023"));
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindByPk() throws Exception {
		try {
			ProductBean bean = new ProductBean();
			long pk = 5L;
			ProductModel model = new ProductModel();
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCustomerName());
			System.out.println(bean.getProductName());
			System.out.println(bean.getProductPrice());
			System.out.println(bean.getPruchaseDate());


		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	

	public static void testDelete() throws Exception {
		ProductBean bean = new ProductBean();
		bean.setId(1L);
		ProductModel model = new ProductModel();
		model.delete(bean);

	}

	public static void testAdd() {
		try {
			ProductBean bean = new ProductBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(1);
			bean.setCustomerName("Kapil Birla");
			bean.setProductName("Samsung");
			bean.setProductPrice(10000);;
			bean.setPruchaseDate(sdf.parse("05-07-2023"));
			
			ProductModel model = new ProductModel();

			long pk = model.add(bean);
			ProductBean addedbean = model.findByPK(pk);
			System.out.println("Test add succ");

			if (addedbean == null) {
				System.out.println("Test add fail");
			}

			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
