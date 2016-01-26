package com.scienjus.smartqq.model;

/**
 * 字体
 * @author ScienJus
 * @date 15/12/19.
 */
public class Font {

    public static final Font DEFAULT_FONT = defaultFont();

    private static Font defaultFont() {
        Font font = new Font();
        font.color = "000000";
        font.style = new int[]{0, 0, 0};
        font.name = "宋体";
        font.size = 10;
        return font;
    }
    public int[] style;
    public String color;
    public String name;
    public int size;

}
