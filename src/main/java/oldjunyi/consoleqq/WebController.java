package oldjunyi.consoleqq;

import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebController implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) {
		System.out.println("incoming exhange at " + exchange.getRequestURI() + " with");
		if (exchange.getRequestURI().toString().equals("/qqbot/restart")) {
			if (ConsoleQQ.client != null) ConsoleQQ.client.restart();
		}
		try {
			byte[] content = "<html> <head> <meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\" /> <meta http-equiv=\"Pragma\" content=\"no-cache\" /> <meta http-equiv=\"Expires\" content=\"0\" /> <meta name=\"viewport\" content=\"width=device-width,target-densitydpi=high-dpi,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\" /> <meta name=”MobileOptimized” content=\"320\" /> <meta name=”Iphone-content” content=\"320\" /> <meta name=”format-detection” content=\"telephone=no\" /> <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" /> <script src=\"/jquery-1.12.0.min.js\"></script> <script> $(document).ready(function() { setTimeout('reload()', 2000); }); function reload() { self.location='/qqbot'; } </script> </head> <body> <div id=\"content\">载入中...</div> </body></html>".getBytes("UTF-8");
			exchange.sendResponseHeaders(200, content.length);
			OutputStream os = exchange.getResponseBody();
			os.write(content);
			os.close();
		} catch (Exception e) {
			ConsoleQQ.report(e);
		}
	}

}
