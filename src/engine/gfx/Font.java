package engine.gfx;

import java.util.ArrayList;

public class Font {
	public static final Font STANDARD = new Font("/fonts/standard.png");
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	private ArrayList<FontSymbol> symbols;
	private String fontText;

	public Font(String path) {
		fontImage = new Image(path);
		fontText = " !\"#˅%+'()*+,-./0123456789:;<=>?☺ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ~_";
		int symbolCount = fontText.length();
		offsets = new int[symbolCount];
		widths = new int[symbolCount];
		
		int unicode = 0;
		
		for(int i = 0; i < fontImage.getWidth(); i++) {
			if(fontImage.getPixels()[i] == 0xff0000ff) {
				offsets[unicode] = i;
			}
			if(fontImage.getPixels()[i] == 0xffffff00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
		symbols = new ArrayList<FontSymbol>();
		
		for(int i = 0; i< symbolCount; i++) {
			int[] temp = new int[widths[i]* fontImage.getHeight()] ;
			int id = 0;
			for(int y = 0; y < fontImage.getHeight(); y++) {
				for(int x = 0; x < widths[i]; x++) {
					
					temp[id] = fontImage.getPixels()[(x + offsets[i]) + y * fontImage.getWidth()];
					id++;
				}
			
			}
			symbols.add(new FontSymbol(
					temp, 
					widths[i], 
					fontImage.getHeight()));
		}
	}
	
	public FontSymbol getSymbolPixels(char s) {
		s = Character.toUpperCase(s);
		int index = fontText.indexOf(s);
		if(index >= 0) {
			return symbols.get(index);
		}else {
			return new FontSymbol(new int[0],0,0);
		}
	}
	
	public boolean isCharInFont(char ch) {
		ch = Character.toUpperCase(ch);
		if(fontText.indexOf(ch)>=0) {
			return true;
		}
		return false;
		
	}
	
}
