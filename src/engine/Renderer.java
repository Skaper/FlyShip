package engine;

import java.awt.image.DataBufferInt;

import engine.gfx.*;

public class Renderer {

	private int camX, camY;
	private int pW, pH;
	private int[] p;
	private Font font = Font.STANDARD;
	public Renderer(GameEngine gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for(int i = 0; i<p.length; i++) {
			p[i] = 0xFF;
		}
	}

	public void setPixel(int x, int y, int value) {
		x -= camX;
		y -= camY;
		int alpha = (value >> 24) & 0xff;
		if((x<0 || x>=pW || y<0 || y >=pH) || alpha==0) {
			return;
		}
		int index = x + y * pW;

		if(alpha == 255) {
			p[index] = value;
		}else {
			int pixelColor = p[index];
			int newRed = ((pixelColor >> 16) & 0xff) - 
					(int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff))  * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) -
					(int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff))  * (alpha / 255f));
			
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha /255f));
			p[index] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}

	public void drawImageUI(Sprite sprite){
		sprite.position.x = camX;
		sprite.position.y = camY;
		if (!((Math.abs(sprite.position.x  + sprite.getWidth()/2f - getCamCenterX()) < sprite.getWidth()/2f + pW/2f) &&
				(Math.abs(sprite.position.y + sprite.getHeight()/2f - getCamCenterY()) <  sprite.getHeight()/2f + pH/2f)))
		{
			return;
		}
		drawImageLogic(sprite);
	}
	public void drawImage(Sprite sprite){drawImageLogic(sprite);}

	public void drawTextUI(String text, float posX, float posY, float size, int color){
		posX+=camX;
		posY+=camY;
		drawTextLogic(text, posX, posY, size, color);

	}
	public void drawText(String text, float posX, float posY, float size, int color){drawTextLogic(text, posX, posY, size, color);}


	public void drawRectUI(float startX, float startY, float endX, float endY, int color){
		startX+=camX;
		startY+=camY;
		endX+=camX;
		endY+=camY;
		drawRectLogic(startX, startY, endX, endY, color);
	}
	public void drawRect(float startX, float startY, float endX, float endY, int color){drawRectLogic(startX, startY, endX, endY, color);}

	public void drawCircleUI(float centerX, float centerY, float radius, int color){
		centerX +=camX;
		centerY +=camY;
		drawCircleLogic(centerX, centerY, radius, color);
	}
	public void drawCircle(float centerX, float centerY, float radius, int color){drawCircleLogic(centerX, centerY, radius, color);}

	private void drawImageLogic(Sprite sprite) {
	    if(sprite == null) return;

		int offX = Math.round(sprite.position.x);
		int offY = Math.round(sprite.position.y);

		int newWidth = sprite.getWidth();
		int newHeight = sprite.getHeight();

		for(int y=0; y < newHeight; y++) {
			if(y+offY > pH + camY) break;
			else if(y+offY < camY) continue;
			for(int x = 0; x < newWidth; x++) {
				if(x + offX > pW + camX) break;
				else if(x + offX < camX) continue;
				setPixel(x + offX, y+offY, sprite.getPixel(x + y * sprite.getWidth()));
			}
		}
	}
	
	private void drawTextLogic(String text, float posX, float posY, float size, int color) {
		int offX = Math.round(posX);
		int offY = Math.round(posY);
		text = text.toUpperCase();
		int offset = 0;
		int offsetY = 0;
		int offSetDefault = font.getSymbolPixels(' ').getHeight();
		for(char ch: text.toCharArray()) {
			FontSymbol sym = font.getSymbolPixels(ch);
			if(ch == '\n') {
				offsetY += offSetDefault;
				offset = 0;
				continue;
			}
			sym.scale(size);
			int h = sym.getHeight();
			int w = sym.getWidth();
			for(int y = 0; y <  h; y++) {
				if(y+offY > pH + camY)break;
				else if(y+offY + offsetY < camY) continue;
				for(int x = 0; x < w; x++) {
					if(x + offX > pW + camX) break;
					else if(x + offX + offset < camX) continue;
					if(sym.getPixel(x  + y * w) == 0xffffffff) {
						setPixel(x+offX+offset, y + offY + offsetY, color);
					}
				}
			}
			offset += w;
		}
		 
	}

	private void drawRectLogic(float startX, float startY, float endX, float endY, int color){
		int stX = Math.round(startX);
		int stY = Math.round(startY);
		int enX = Math.round(endX);
		int enY = Math.round(endY);

		for(int y = stY; y <=  enY; y++) {
			if(y > pH + camY)break;
			else if(y < camY) continue;
			setPixel(stX, y, color);
			setPixel(enX, y, color);
		}

		for(int x = stX; x <= enX; x++) {
			if(x > pW + camX) break;
			else if(x < camX) continue;
			setPixel(x, stY, color);
			setPixel(x, enY, color);
		}
	}
	private   void drawCircleLogic(float centerX, float centerY, float radius, int color)
	{
		int xc = Math.round(centerX);
		int yc = Math.round(centerY);
		int r = Math.round(radius);

		int x = 0, y = r;
		int d = 3 - 2 * r;
		while (y >= x)
		{
			x++;
			if (d > 0)
			{
				y--;
				d = d + 4 * (x - y) + 10;
			}
			else {
				d = d + 4 * x + 6;
			}
			setPixel(xc+x, yc+y, color);
			setPixel(xc-x, yc+y, color);
			setPixel(xc+x, yc-y, color);
			setPixel(xc-x, yc-y, color);
			setPixel(xc+y, yc+x, color);
			setPixel(xc-y, yc+x, color);
			setPixel(xc+y, yc-x, color);
			setPixel(xc-y, yc-x, color);
		}
	}

	public int getCamCenterX(){
		return Math.round(camX+pW/2f);
	}
	public int getCamCenterY(){
		return Math.round(camY+pH/2f);
	}
	public void setCamX(int camX) {
		this.camX = camX;
	}
	public void setCamY(int camY) {
		this.camY = camY;
	}
	public int getCamX() {
		return camX;
	}
	public int getCamY() {
		return camY;
	}


}


