package oldjunyi.consoleqq.modules;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

import oldjunyi.consoleqq.MetaMessage;

public class DiceRoller extends BaseModule {
	
	public static class LastDice {
		String tag;
		long timeStamp;
		public LastDice(String tag) {
			this.tag = tag;
		}
	}

	public LinkedList<String> tokens;
	Random random = new Random();
	TreeMap<Long, LastDice> history = new TreeMap<Long, LastDice>();
	LastDice lastDice;
	StringBuilder tag;
	
	public boolean isDiceDigit(char c) {
		return Character.isDigit(c) || c == 'd' || c == 'D';
	}
	
	@Override
	public String parse(MetaMessage msg) {
		String content = msg.content;
		if (!content.matches("^[0-9dD+\\-\\*/() ]+$")) return "";
		if (!content.contains("d") && !content.contains("D")) return "";
		tokens = new LinkedList<String>();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (isDiceDigit(c)) {
				StringBuilder builder = new StringBuilder();
				builder.append(c);
				while (i + 1 < content.length() && isDiceDigit(content.charAt(i + 1))) {
					if (!Character.isDigit(c) &&
						!Character.isDigit(content.charAt(i + 1))) return "";
					builder.append(content.charAt(++i));
				}
				tokens.add(builder.toString());
			} else if (!Character.isSpaceChar(c)) {
				tokens.add(String.valueOf(c));
			}
		}
		tokens.add("#");
		try {
			lastDice = history.get(msg.userID);
			if (lastDice == null || (msg.timeStamp - lastDice.timeStamp) > 3600) lastDice = new LastDice("d6");
			StringBuilder ret = null;
			while (!tokens.getFirst().equals("#")) {
				if (ret == null) ret = new StringBuilder(); else ret.append("\n");
				tag = new StringBuilder();
				tag.append(" = " + expr());
				ret.append(tag);
			}
			lastDice.timeStamp = msg.timeStamp;
			history.put(msg.userID, lastDice);
			return ret.toString();
		} catch (Exception e) {
			return content + " = " + e.getMessage();
		}
	}
	
	int expr() throws Exception {
		int ret = term();
		String o = tokens.getFirst();
		while (o.equals("+") || o.equals("-")) {
			tokens.removeFirst();
			tag.append(o);
			if (o.equals("+")) ret += term();
			if (o.equals("-")) ret -= term();
			o = tokens.getFirst();
		}
		return ret;
	}
	
	int term() throws Exception {
		int ret = dice();
		String o = tokens.getFirst();
		while (o.equals("*") || o.equals("/")) {
			tokens.removeFirst();
			tag.append(o);
			if (o.equals("*")) ret *= dice();
			if (o.equals("/")) ret /= dice();
			o = tokens.getFirst();
		}
		return ret;
	}
	
	int dice() throws Exception {
		String o = tokens.getFirst();
		if (o.equals("(")) {
			tokens.removeFirst();
			tag.append(o);
			int ret = expr();
			o = tokens.getFirst();
			if (!o.equals(")")) throw new Exception("Missing Right Parenthesis");
			tokens.removeFirst();
			tag.append(o);
			return ret;
		}
		int sgn = 1;
		if (o.equals("+") || o.equals("-")) {
			tokens.removeFirst();
			tag.append(o);
			if (o.equals("+")) sgn = +1;
			if (o.equals("-")) sgn = -1;
			o = tokens.getFirst();
		}
		if (o.contains("d") || o.contains("D")) {
			tokens.removeFirst();
			String mark = null;
			if (o.equalsIgnoreCase("d")) {
				tag.append(o + "(");
				tag.append(o = lastDice.tag);
				tag.append(")");
			} else {
				tag.append(mark = o);
			}
			if (o.startsWith("d") || o.startsWith("D")) o = "1" + o;
			String[] tags = o.split("[dD]");
			if (tags.length != 2) throw new Exception("Invalid Expression");
			if (tags[0].length() >= 10 || tags[1].length() >= 10) throw new Exception("Number Overflow");
			int count = Integer.parseInt(tags[0]);
			int faces = Integer.parseInt(tags[1]);
			if (count < 0 || count > 100000000 ||
				faces < 1 || faces > 100000000) throw new Exception("Number Overflow");
			if (mark != null) lastDice.tag = mark;
			int ret = 0;
			for (int i = 0; i < count; i++)
				ret += random.nextInt(faces) + 1;
			return sgn * ret;
		} else if (o.matches("^\\d+$") && o.length() < 10) {
			tokens.removeFirst();
			tag.append(o);
			int count = Integer.parseInt(o);
			if (count < 0 || count > 100000000) throw new Exception("Number Overflow");
			return sgn * count;
		}
		throw new Exception("Invalid Expression");
	}

}
