package cc.pp.weixin.biz.jhs.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cc.pp.weixin.constant.Business;
import cc.pp.weixin.constant.CustomConstant;


/**
 * 业务控制：
 * 1、开关控制；
 * 2、日志输出控制；
 * @author wgybzb
 * @since  2013-04-06
 */
public class DynamicController extends HttpServlet {
	private static Logger logger = Logger.getLogger(DynamicController.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String method = request.getParameter("method");
			// 是否向微信公众平台注册开发者账号
			if ("regdev".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.OPEN_SWITCH, value)){
					Business.REGISTER_DEVELOPER_SWITCH = Boolean.TRUE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.TRUE);
				}else if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.CLOSE_SWITCH, value)){
					Business.REGISTER_DEVELOPER_SWITCH = Boolean.FALSE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.FALSE);
				}
			}

			// 是否开启请求校验，防止无效的调用
			if ("verify".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.OPEN_SWITCH, value)){
					Business.REQUEST_VERIFY_SWITCH = Boolean.TRUE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.TRUE);
				}else if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.CLOSE_SWITCH, value)){
					Business.REQUEST_VERIFY_SWITCH = Boolean.FALSE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.FALSE);
				}
			}

			// 是否开启请求校验，防止无效的调用
			if ("loginfo".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.OPEN_SWITCH, value)){
					Business.LOG_INFO_SWITCH = Boolean.TRUE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.TRUE);
				}else if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.CLOSE_SWITCH, value)){
					Business.LOG_INFO_SWITCH = Boolean.FALSE;
					response.getOutputStream().println("It is setted successfully,current value is " + Boolean.FALSE);
				}
			}

			// 是否开启本地开发调试，用模拟数据进行调试
			if ("dev".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.OPEN_SWITCH, value)){
					Business.OPEN_MOCKUP_SWITCH = Boolean.TRUE;
					Business.REQUEST_VERIFY_SWITCH = Boolean.FALSE;
					Business.JU_UID = 216;
					Business.REPLYSERVICE_HOST = "http://test.rp.svc.pp.cc";
					response.getOutputStream().println("Open Dev Confige Successfully");
				}else if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.CLOSE_SWITCH, value)){
					Business.OPEN_MOCKUP_SWITCH = Boolean.FALSE;
					Business.REQUEST_VERIFY_SWITCH = Boolean.TRUE;
					Business.JU_UID = CustomConstant.JU_UID;
					Business.REPLYSERVICE_HOST = "http://duihua.svc.pp.cc";
					response.getOutputStream().println("Close Dev Confige Successfully");
				}
			}

			// 是否开启测试环境进行测试环境测试
			if ("test".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.OPEN_SWITCH, value)){
					Business.JU_UID = 216;
					Business.REPLYSERVICE_HOST = "http://test.rp.svc.pp.cc";
					response.getOutputStream().println("Open Test Confige Successfully");
				}else if (StringUtils.isNotEmpty(value) && StringUtils.equalsIgnoreCase(Business.CLOSE_SWITCH, value)){
					Business.JU_UID = CustomConstant.JU_UID;
					Business.REPLYSERVICE_HOST = "http://duihua.svc.pp.cc";
					response.getOutputStream().println("Close Test Confige Successfully");
				}
			}

			// 是否开启本地测试，用模拟数据进行调试
			if ("initUid".equalsIgnoreCase(method)) {
				String value = request.getParameter("value");
				if (StringUtils.isNotEmpty(value)){
					Business.JU_UID = Integer.valueOf(value);
					response.getOutputStream().println("It is setted successfully,current value is " + Business.JU_UID);
				}
			}
		} catch (Exception e) {
			logger.error("When DynamicController Process Error", e);
			response.getOutputStream().println("When  DynamicController error");
		}
	}
}
