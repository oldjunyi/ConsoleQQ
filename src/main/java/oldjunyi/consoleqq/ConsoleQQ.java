package oldjunyi.consoleqq;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpServer;

public class ConsoleQQ {
	
	public static QQClient client;
	
    public static void main(String[] args) {
    	client = new QQClient();
    	client.add("oldjunyi.consoleqq.modules.DiceRoller");
    	client.start();
    	try {
			HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 23200), 100);
			server.createContext("/", new WebController());
			server.setExecutor(null);
			server.start();
		} catch (Exception e) {
			ConsoleQQ.report(e);
		}
    }
    
    public static Logger getLogger() {
    	return Logger.getLogger(ConsoleQQ.class);
    }
    
    public static void report(Exception e) {
    	getLogger().error(e.getMessage());
		StackTraceElement[] stack = e.getStackTrace();
		for (int i = 0; i < stack.length; i++)
			getLogger().error("    " + stack[i].toString());
    }
	
}
