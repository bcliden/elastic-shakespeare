package org.bliden.shakespeare.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.bliden.shakespeare.esService;

@WebListener
public class ContextListener implements ServletContextListener {
	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("^^^^^^^^^^^ STARTING SERVLET ^^^^^^^^^^");
		ServletContext context = sce.getServletContext();
		esService es = new esService();
		context.setAttribute("elasticsearch", es);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("vvvvvvvvvvv CLOSING SERVLET vvvvvvvvvvvv");
		ServletContext context = sce.getServletContext();
		esService es = (esService) context.getAttribute("elasticsearch");
		es.releaseClient();
	}

};
