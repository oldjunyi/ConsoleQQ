package oldjunyi.tools.modules;

public class BGAutoPT extends BaseModule {
	
	@Override
	String parse(String content) {
		content = removeSpace(content);
		content = toLowerCase(content);
		String[] forbidden = {"姜凯", "猛犸", "jk", "oldjunyi", "军医"};
		String[] forbiddenHead = {"猛", "萌", "蒙", "孟", "猛"};
		String[] forbiddenTail = {"马", "码", "蚂", "玛", "犸", "杩", "馬", "獁"};
		if (!content.contains("bg")) return "";
		for (int i = 0; i < forbidden.length; i++)
			if (content.contains(forbidden[i])) return "";
		for (String x: forbiddenHead)
		for (String y: forbiddenTail)
			if (content.contains(x + y)) return "";
		return "pt"; 
	}
	
	String removeSpace(String content) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (!Character.isSpaceChar(c)) ret.append(c);
		}
		return ret.toString();
	}
	
	String toLowerCase(String content) {
		content = content.toLowerCase();
		String x = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";
		String y = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
		String z = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < 26; i++) {
			content.replace(x.charAt(i), z.charAt(i));
			content.replace(y.charAt(i), z.charAt(i));
		}
		return content;
	}
}
