package oldjunyi.consoleqq.modules;

import oldjunyi.consoleqq.MetaMessage;
import oldjunyi.consoleqq.MetaMessage.MessageType;

public class BaseModule {
	
	public boolean enabled = true;
	
	public void onMessage(MetaMessage msg) {
		String ret = parse(msg);
		if (ret.isEmpty()) return;
		if (msg.type == MessageType.USER) msg.client.sendMessageToFriend(msg.userID, ret);
		if (msg.type == MessageType.GROUP) msg.client.sendMessageToGroup(msg.groupID, ret);
		if (msg.type == MessageType.DISCUSS) msg.client.sendMessageToDiscuss(msg.discussID, ret);
	}
	
	public String parse(MetaMessage msg) {
		return "";
	}
	
}
