package oldjunyi.consoleqq;

import java.util.Map.Entry;
import java.util.TreeMap;

import oldjunyi.consoleqq.MetaMessage.MessageType;
import oldjunyi.consoleqq.modules.BaseModule;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

public class QQClient extends Thread {
	
	public TreeMap<String, BaseModule> modules = new TreeMap<String, BaseModule>();
	public SmartQQClient proxy = null;
	
	@Override
	public void run() {
		while (true) {
			proxy = new SmartQQClient();
			proxy.login(new MessageCallback() {
				@Override
				public void onMessage(Message msg) {
					invoke(new MetaMessage(msg, proxy));
				}
				@Override
				public void onGroupMessage(GroupMessage msg) {
					invoke(new MetaMessage(msg, proxy));
				}
				@Override
				public void onDiscussMessage(DiscussMessage msg) {
					invoke(new MetaMessage(msg, proxy));
				}
			});
		}
	}
	
	public void restart() {
		if (isAlive() && proxy != null) {
			try {
				proxy.close();
				proxy = null;
			} catch (Exception e) {
				ConsoleQQ.report(e);
			}
		}
	}
	
	public void add(String className) {
		try {
			BaseModule module = (BaseModule)Class.forName(className).newInstance();
			modules.put(className, module);
		} catch (Exception e) {
			ConsoleQQ.report(e);
		}
	}
	
	public void invoke(MetaMessage msg) {
		if (msg.type == MessageType.USER) checkCommand(msg);
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			BaseModule module = e.getValue();
			if (module.enabled) module.onMessage(msg);
		}
	}
	
	public void checkCommand(MetaMessage msg) {
		String content = msg.content; 
		if (!content.startsWith("/")) return;
		String[] args = content.split(" "); 
		for (Entry<String, BaseModule> e: modules.entrySet()) {
			String moduleName = e.getKey();
			BaseModule module = e.getValue();
			if (args.length == 2 && args[0].equals("/stop") &&
				moduleName.toLowerCase().contains(args[1].toLowerCase())) {
				module.enabled = false;
				proxy.sendMessageToFriend(msg.userID, moduleName + " stopped");
			}
			if (args.length == 2 && args[0].equals("/start") &&
				moduleName.toLowerCase().contains(args[1].toLowerCase())) {
				module.enabled = true;
				proxy.sendMessageToFriend(msg.userID, moduleName + " started");
			}
		}
	}
	

	
}
