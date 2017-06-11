package countmeup.countmeup;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * This class is the part where we start up our web server in embedded mode. We also publish the startServer, 
 * stopServer and isRunning methods outside, in order to be used for test cases. 
 * */
public class AppServer 
{	
	private static Server server = null;
	public static void main( String[] args ){
		startServer();//Start server from main code
	}
	
	/**
	 *Start the jetty server used for the countmeup application and init the necessary configurations.
	 * **/
	public static void startServer(){
		ResourceConfig config = new ResourceConfig();
		config.packages("countmeup.countmeup");
		ServletHolder servlet = new ServletHolder(new ServletContainer(config));


		server = new Server(8181);
		ServletContextHandler context = new ServletContextHandler(server, "/*");
		context.addServlet(servlet, "/*");


		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
			if(server != null && server.isRunning()){
				server.destroy();
			}
		} 
	}
	
	/**
	 * Necessary method to terminate the jetty web server
	 * */
	public static void shutDownServer(){
		if(server != null){
			try{
				server.stop();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * For situations like initializing a test case it is necessary to check whether the server is started completely before running the test cases.
	 * */
	public static boolean isRunning(){
		return server != null && server.isStarted();
	}
}
