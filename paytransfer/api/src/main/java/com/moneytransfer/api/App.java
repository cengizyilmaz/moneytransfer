package com.moneytransfer.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.moneytransfer.api.handler.JettyServer;
import com.moneytransfer.api.handler.ServerTemplate;

/**
 * 
 * The main method is executed Jetty Http Server in a thread. The REST requests
 * are handled through this server and forward to Service classes
 */
public class App {
	static int port = 8080;
	public static void main(String[] args) {
		
		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		Callable<ServerTemplate> task = () -> {
			JettyServer server = new JettyServer();
			server.execute(port);
			return server;
		};

		ExecutorService server = Executors.newCachedThreadPool();
		server.submit(task);

	}
}
