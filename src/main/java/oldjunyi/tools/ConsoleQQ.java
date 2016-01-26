package oldjunyi.tools;

import com.scienjus.smartqq.client.SmartQQClient;


public class ConsoleQQ {
	
    public static void main(String[] args) {
    	SmartQQClient client = new SmartQQClient();
    	ModuleManager module = new ModuleManager(client);
    	module.add("oldjunyi.tools.modules.DiceRoller");
    	module.add("oldjunyi.tools.modules.BGAutoPT");
    	client.login();
    	client.pollMessage(module);
    }
	
}
