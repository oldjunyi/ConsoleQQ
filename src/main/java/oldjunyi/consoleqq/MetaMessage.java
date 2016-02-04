package oldjunyi.consoleqq;

import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.Font;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

public class MetaMessage {
	
	public enum MessageType {
		USER, GROUP, DISCUSS
	}
	public MessageType type;
	public long discussID, groupID, userID;
	public long timeStamp;
	public String content;
	public Font font;
	public SmartQQClient client;
	
	public MetaMessage(Message msg, SmartQQClient client) {
		this.client = client;
		type = MessageType.USER;
		discussID = groupID = -1;
		userID = msg.getUserId();
		timeStamp = msg.getTime();
		content = msg.getContent();
		font = msg.getFont();
	}
	
	public MetaMessage(GroupMessage msg, SmartQQClient client) {
		this.client = client;
		type = MessageType.GROUP;
		discussID = -1;
		groupID = msg.getGroupId();
		userID = msg.getUserId();
		timeStamp = msg.getTime();
		content = msg.getContent();
		font = msg.getFont();
	}
	
	public MetaMessage(DiscussMessage msg, SmartQQClient client) {
		this.client = client;
		type = MessageType.DISCUSS;
		groupID = -1;
		discussID = msg.getDiscussId();
		userID = msg.getUserId();
		timeStamp = msg.getTime();
		content = msg.getContent();
		font = msg.getFont();
	}
	
}
