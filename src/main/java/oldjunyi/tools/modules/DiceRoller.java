package oldjunyi.tools.modules;

import java.util.LinkedList;
import java.util.Random;

public class DiceRoller extends BaseModule {

	public LinkedList<String> tokens;
	Random random = new Random();
	
	@Override
	public String parse(String content) {
		StringBuilder ret = new StringBuilder();
		ret.append(content + " = ");
		if (!content.matches("^[0-9dD+\\-\\*/() ]+$")) return "";
		if ( content.matches("^.*[dD]+$")) return "";
		if (!content.contains("d") && !content.contains("D")) return "";
		tokens = new LinkedList<String>();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (Character.isDigit(c)) {
				StringBuilder builder = new StringBuilder();
				builder.append(c);
				while (i + 1 < content.length() && Character.isDigit(content.charAt(i + 1)))
					builder.append(content.charAt(++i));
				tokens.add(builder.toString());
			} else if (!Character.isSpaceChar(c)) {
				tokens.add(String.valueOf(c));
			}
		}
		tokens.add("#");
		try {
			int dice = expr();
			if (!tokens.getFirst().equals("#")) throw new Exception("Invalid Expression");
			ret.append(dice);
		} catch (Exception e) {
			ret.append(e.getMessage());
		}
		return ret.toString();
	}
	
	int expr() throws Exception {
		int ret = term();
		String o = tokens.getFirst();
		while (o.equals("+") || o.equals("-")) {
			tokens.removeFirst();
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
			if (o.equals("*")) ret *= dice();
			if (o.equals("/")) ret /= dice();
			o = tokens.getFirst();
		}
		return ret;
	}
	
	int dice() throws Exception {
		int sgn = 1;
		String o = tokens.getFirst();
		if (o.equals("+") || o.equals("-")) {
			if (o.equals("+")) sgn = +1;
			if (o.equals("-")) sgn = -1;
			tokens.removeFirst();
			o = tokens.getFirst();
		}
		int count = 1;
		if (!o.equalsIgnoreCase("d")) {
			count = factor();
			o = tokens.getFirst();
		}
		if (!o.equalsIgnoreCase("d")) return sgn * count;
		tokens.removeFirst();
		int faces = dice();
		if (count < 0 || count > 100000000) throw new Exception("Dice Range Overflow");
		if (faces < 1 || faces > 100000000) throw new Exception("Dice Range Overflow");
		int ret = 0;
		for (int i = 0; i < count; i++)
			ret += random.nextInt(faces) + 1;
		return sgn * ret;
	}
	
	int factor() throws Exception {
		String o = tokens.getFirst();
		if (o.equals("(")) {
			tokens.removeFirst();
			int ret = expr();
			if (!tokens.getFirst().equals(")")) throw new Exception("Invalid Expression");
			tokens.removeFirst();
			return ret;
		}
		if (o.matches("^[0-9]+$")) {
			int ret = Integer.parseInt(o);
			tokens.removeFirst();
			return ret;
		}
		throw new Exception("Invalid Expression");
	}

}
