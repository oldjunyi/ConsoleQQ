package oldjunyi.tools.modules;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;


public class BaseModule implements MessageCallback {
	
	public SmartQQClient client;
	public boolean enabled = true;
	
	@Override
	public void onMessage(Message message) {
		String ret = parse(message.content);
		if (!ret.isEmpty()) client.sendMessageToFriend(message.userId, ret);
	}
	
	@Override
	public void onGroupMessage(GroupMessage message) {
		String ret = parse(message.content);
		if (!ret.isEmpty()) client.sendMessageToGroup(message.groupId, ret);
	}
	
	@Override
	public void onDiscussMessage(DiscussMessage message) {
		String ret = parse(message.content);
		if (!ret.isEmpty()) client.sendMessageToDiscuss(message.discussId, ret);
	}
	
	String parse(String content) {
		return "";
	}
}
