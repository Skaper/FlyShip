package engine.gfx;

import engine.ResourceLoader;

public class Sprite{
	private String imagePath;
	private int width,height;
	public Vector2 position;
	private int center_x = 0;
	private int center_y = 0;
	private int[] pixels;
	public static final float SCALE_DEFAULT = 1f;
	private float scaleW = SCALE_DEFAULT;
	private float scaleH = SCALE_DEFAULT;
	private int currentAngle = 0;

	public Sprite(Image image){
		imagePath = image.getImagePath();
		position = new Vector2();
		pixels =image.getPixelsCopy();
		width = image.getWidth();
		height = image.getHeight();
		center_x = width/2;
		center_y = height/2;
	}

	public Sprite(String path, Vector2 position) {
		imagePath = path;
		this.position = position.clone();
		Image tempImg = ResourceLoader.getImage(path);
		this.height = tempImg.getHeight();
		this.width = tempImg.getWidth();
		this.pixels = tempImg.getPixelsCopy();
		center_x = width/2;
		center_y = height/2;

	}

	public Sprite(int width, int height, Vector2 position){
		imagePath = null;
		this.position = position.clone();
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
		center_x = width/2;
		center_y = height/2;
	}


	public void setOn(Sprite img, Vector2 position){

		int _offX = Math.round(position.x);
		int _offY = Math.round(position.y);

		int newX = 0;
		int newY = 0;

		int newWidth = img.getWidth();
		int newHeight = img.getHeight();

		for(int y=newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + _offX, y+_offY, new Color(img.getPixels()[x + y * img.getWidth()]));

			}
		}
	}

	public void rotate(int angle){
		if(angle == currentAngle) return;
		if(imagePath == null) return;
		angle =(angle %= 360) >= 0 ? angle : (angle + 360);
		this.pixels = ResourceLoader.getImage(imagePath).rotate(angle, center_x, center_y, scaleH, scaleW);
		currentAngle = angle;
	}

	public void scale(float scaleW,float scaleH) {
		if(scaleW == this.scaleW && scaleH == this.scaleH) return;
		this.scaleH = scaleH;
		this.scaleW = scaleW;
		Image scaledImage = ResourceLoader.getImage(imagePath).getScaled(scaleW, scaleH);
		this.pixels = scaledImage.getScalePixels();
		this.width = scaledImage.getScaledWidth();
		this.height = scaledImage.getScaledHeight();
		this.center_x = width/2;
		this.center_y = height/2;
	}

	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0x00ffffff;
		}
	}

	public void setPixel(int x, int y, Color c1){
		if((x<0 || x>=width || y<0 || y >=width) || c1.getA() == 0) {
			return;
		}

		int index = x + y * width;
		if(c1.getA() == 255) {
			pixels[index] = c1.getARGB();
			return;
		}

		Color c0 = new Color(pixels[index]);

		double totalAlpha = c0.getA() + c1.getA();
		double weight0 = c0.getA() / totalAlpha;
		double weight1 = c1.getA() / totalAlpha;

		double r = weight0 * c0.getR() + weight1 * c1.getR();
		double g = weight0 * c0.getG() + weight1 * c1.getG();
		double b = weight0 * c0.getB() + weight1 * c1.getB();
		double a = Math.max(c0.getA(), c1.getA());

		Color newC =  new Color( (int) a, (int) r, (int) g, (int) b);
		pixels[index] = newC.getARGB();
	}

	public boolean isClicked(boolean mouseEvent, int x, int y) {
		return mouseEvent 
				&& x >= this.position.x && x <= this.position.x + this.width
				&& y >= this.position.y && y <= this.position.y + this.height;
	}
	public boolean inFocus(int x, int y) {
		return  x >= this.position.x && x <= this.position.x + this.width
				&& y >= this.position.y && y <= this.position.y + this.height;
	}

	public void setPos(Vector2 position) {
		this.position = position;
	}
	
	public void move(Vector2 position) {
		this.position.plus(position);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}
	public int getPixel(int index){
		return pixels[index];
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public float getScaleW() {
		return scaleW;
	}

	public float getScaleH() {
		return scaleH;
	}

	public float getPosX() {
		return position.x;
	}

	public void setPosX(float posX) {
		this.position.x = posX;
	}

	public float getPosY() {
		return position.y;
	}

	public void setPosY(float posY) {
		this.position.y = posY;
	}

	@Override
	public String toString() {
		return imagePath + " |x:" + position.x + " y:" + position.y + "| W:H=" + width+":"+height+"| Scale WxH: " + scaleW +"x" + scaleW;
	}
}
