package oldjunyi.tools;

import java.util.Map.Entry;
import java.util.TreeMap;

import oldjunyi.tools.modules.BaseModule;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

public class ModuleManager implements MessageCallback {
	
	TreeMap<String, BaseModule> modules = new TreeMap<String, BaseModule>();
	public SmartQQClient client;
	public long adminQQ;
	
	public ModuleManager(SmartQQClient client, long adminQQ) {
		this.client = client;
		this.adminQQ = adminQQ;
	}
	
	public void add(String className) {
		try {
			BaseModule module = (BaseModule)Class.forName(className).newInstance();
			module.client = client;
			modules.put(className, module);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkCommand(String content, long userID) {
		if (!content.startsWith("/")) return;
		String[] args = content.split(" "); 
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			String moduleName = e.getKey();
			BaseModule module = e.getValue();
			if (args.length == 2 && args[0].equals("/stop") &&
				moduleName.toLowerCase().contains(args[1].toLowerCase())) {
				module.enabled = false;
				client.sendMessageToFriend(userID, moduleName + " stopped");
			}
			if (args.length == 2 && args[0].equals("/start") &&
				moduleName.toLowerCase().contains(args[1].toLowerCase())) {
				module.enabled = true;
				client.sendMessageToFriend(userID, moduleName + " started");
			}
		}
	}
	
	@Override
	public void onMessage(Message message) {
		checkCommand(message.content, message.userId);
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			BaseModule module = e.getValue();
			if (module.enabled) module.onMessage(message);
		}
	}

	@Override
	public void onGroupMessage(GroupMessage message) {
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			BaseModule module = e.getValue();
			if (module.enabled) module.onGroupMessage(message);
		}
	}

	@Override
	public void onDiscussMessage(DiscussMessage message) {
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			BaseModule module = e.getValue();
			if (module.enabled) module.onDiscussMessage(message);
		}
	}
}
