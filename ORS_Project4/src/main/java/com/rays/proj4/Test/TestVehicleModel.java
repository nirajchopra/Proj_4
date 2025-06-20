package com.rays.proj4.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.VehicleModel;


/**
 * User Model Test classes.
 * 
 * @author Niraj Chopra
 *
 */
public class TestVehicleModel {

	public static void main(String[] args) throws Exception {
//		 testAdd();
//		 testDelete();
//		 testFindByPk();
//		 testUpdate();
//		testSearch();
		 getList();
	}

	

	private static void getList() throws Exception {
		try {
			VehicleBean bean = new VehicleBean();
			VehicleModel model = new VehicleModel();
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test list fail ");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (VehicleBean) it.next();
				System.out.println(bean.getId());
				System.out.print(bean.getNumber()) ;
				System.out.print(bean.getPurchaseDate());
				System.out.print(bean.getInsuranceAmount());
				System.out.print(bean.getColour());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	private static void testSearch() throws Exception {
		try {
			VehicleBean bean = new VehicleBean();
			VehicleModel model = new VehicleModel();
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
				bean = (VehicleBean) it.next();
				System.out.println(bean.getId());
				System.out.print(bean.getNumber()) ;
				System.out.print(bean.getPurchaseDate());
				System.out.print(bean.getInsuranceAmount());
				System.out.print(bean.getColour());


			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws Exception {
		try {
			VehicleBean bean = new VehicleBean();
			VehicleModel model = new VehicleModel();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
			bean = model.findByPK(1L);
			bean.setNumber("MH 7460");
			bean.setPurchaseDate(sdf.parse("05-07-2023"));
			bean.setInsuranceAmount(3542.5d);
			bean.setColour("Blue");
			
			model.update(bean);

			System.out.println("test update successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindByPk() throws Exception {
		try {
			VehicleBean bean = new VehicleBean();
			long pk = 2L;
			VehicleModel model = new VehicleModel();
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.print(bean.getNumber()) ;
			System.out.print(bean.getPurchaseDate());
			System.out.print(bean.getInsuranceAmount());
			System.out.print(bean.getColour());


		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	

	public static void testDelete() throws Exception {
		VehicleBean bean = new VehicleBean();
		bean.setId(1);;
		VehicleModel model = new VehicleModel();
		model.delete(bean);

	}

	public static void testAdd() throws Exception{
		try {
			VehicleBean bean = new VehicleBean();
			VehicleModel model = new VehicleModel();

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			bean.setId(1);
			bean.setNumber("RN 7465");
			bean.setPurchaseDate(sdf.parse("15-04-2023"));
			bean.setInsuranceAmount(3572.5d);
			bean.setColour("Blue");

			long pk = model.add(bean);
			VehicleBean addedbean = model.findByPK(pk);
			System.out.println("Test add succussfull");

			if (addedbean == null) {
				System.out.println("Test add fail");
			}

			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
