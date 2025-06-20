package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PositionBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.PositionModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PositionCtl", urlPatterns = { "/PositionCtl" })
public class PositionCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("designation"))) {
			request.setAttribute("designation", PropertyReader.getValue("error.require", "Designation Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("openingDate"))) {
			request.setAttribute("openingDate", PropertyReader.getValue("error.require", "OpeningDate "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("requiredExperience"))) {
			request.setAttribute("requiredExperience", PropertyReader.getValue("error.require", "Required Experience"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("condition"))) {
			request.setAttribute("condition", PropertyReader.getValue("error.require", "Condition"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PositionBean bean = new PositionBean();

		System.out.println("requesttttidddddd=>" + request.getParameter("id"));
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println("beannnnnnnnniddddddd=>" + bean.getId());

		System.out.println("requesttttDessssss=>" + request.getParameter("designation"));
		bean.setDesignation(DataUtility.getString(request.getParameter("designation")));
		System.out.println("beannnnnnnnnnDesssss=>" + bean.getDesignation());

		System.out.println("date simple => " + request.getParameter("openingDate"));
		bean.setOpeningDate(DataUtility.getDate(request.getParameter("openingDate")));
		System.out.println("date => " + bean.getOpeningDate());

		System.out.println("reqestrtttt expppppppppp=>" + request.getParameter("requiredExperience"));
		bean.setRequiredExperience(DataUtility.getString(request.getParameter("requiredExperience")));
		System.out.println("beannnnnnnnnnExpppppp" + bean.getRequiredExperience());

		System.out.println("reqqqqqqqqqqqqComnnnnnnnnnnn=>" + request.getParameter("condition"));
		bean.setCondition(DataUtility.getString(request.getParameter("condition")));
		System.out.println("beannnnnnnnnConnnnnnn" + bean.getCondition());

		populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in do post method");

		String op = DataUtility.getString(request.getParameter("operation"));

		PositionModel model = new PositionModel();

		PositionBean bean = (PositionBean) populateBean(request);

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			try {

				if (bean.getId() != 0 && bean.getId() > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Position updated Successfully..!!", request);
					ServletUtility.forward(getView(), request, response);
				} else {
					model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Position Added Successfully..!!", request);
					ServletUtility.forward(getView(), request, response);
				}

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("designation  already exist", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.POSITION_CTL, request, response);
			return;
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in do get method");
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {

			PositionModel model = new PositionModel();

			try {
				PositionBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.POSITION_VIEW;
	}

}