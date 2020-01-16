package engine.gfx;

public class FontSymbol {
	private int[] pixels;
	private int[] defaultPixels;
	private int default_width;
	private int default_height;
	private int width;
	private int height;

	public FontSymbol(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.defaultPixels = pixels;
		this.default_width = width;
		this.width = width;
		this.default_height = height;
		this.height = height;
	}
	
	public void scale(float scale) {		
		int w2 = (int)(default_width *scale);
		int h2 = (int)(default_height *scale);
		
	    int[] temp = new int[w2*h2] ;
	    double x_ratio = default_width /(double)w2 ;
	    double y_ratio = default_height /(double)h2 ;
	    double px, py ; 
	    for (int i=0;i<h2;i++) {
	        for (int j=0;j<w2;j++) {
	            px = Math.floor(j*x_ratio) ;
	            py = Math.floor(i*y_ratio) ;
	            temp[(i*w2)+j] = defaultPixels[(int)((py* default_width)+px)] ;
	        }
	    }
	    this.width = w2;
		this.height = h2;
		this.pixels = temp;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	public boolean isEmpty() {
		if(width == 0) {
			return true;
		}
		return false;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public int getDefaultWidth() {
		return default_width;
	}

	public void setDefaultWidth(int default_width) {
		this.default_width = default_width;
	}

	public int getDefaultHeight() {
		return default_height;
	}

	public void setDefaultHeight(int default_height) {
		this.default_height = default_height;
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

	public int[] getDefaultPixels() {
		return defaultPixels;
	}
}
