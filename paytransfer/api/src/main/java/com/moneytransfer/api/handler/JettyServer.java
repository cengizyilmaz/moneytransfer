package com.moneytransfer.api.handler;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.moneytransfer.api.service.AccountService;
import com.moneytransfer.api.service.CustomerService;
import com.moneytransfer.api.service.TransactionService;

public class JettyServer implements ServerTemplate {

	public void execute(int port) {
		Server server = new Server(port);

        ServletContextHandler ctx = 
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
                
        ctx.setContextPath("/");
        
        server.setHandler(ctx);

       ServletHolder serHol = ctx.addServlet("org.glassfish.jersey.servlet.ServletContainer", "/api/*");
       serHol.setInitOrder(0);

       serHol.setInitParameter("jersey.config.server.provider.classnames",CustomerService.class.getCanonicalName()+","+AccountService.class.getCanonicalName()+","+TransactionService.class.getCanonicalName());
     /*   serHol.setInitParameter("jersey.config.server.provider.packages", 
        		"com.moneytransfer.api.service"
             );
      */
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
        	ex.printStackTrace();
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            server.destroy();
        }
		
	}

}
