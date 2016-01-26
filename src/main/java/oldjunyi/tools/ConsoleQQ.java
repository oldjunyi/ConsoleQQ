package oldjunyi.tools;

import com.scienjus.smartqq.client.SmartQQClient;


public class ConsoleQQ {
	
    public static void main(String[] args) {
    	SmartQQClient client = new SmartQQClient();
    	ModuleManager module = new ModuleManager(client, 2298512975L);
    	module.add("oldjunyi.tools.modules.DiceRoller");
    	module.add("oldjunyi.tools.modules.BGAutoPT");
    	client.login();
    	client.pollMessage(module);
    }
	
}
