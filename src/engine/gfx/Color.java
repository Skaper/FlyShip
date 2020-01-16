package engine.gfx;

public class Color {
    private int a;
    private int r;
    private int g;
    private int b;
    private int color;

    public Color(){
        setARGB(0, 0, 0,0);
        this.a = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }
    public Color(int color){
        this.color = color;
        this.a = 0xFF & (color >> 24);
        this.r = 0xFF & ( color >> 16);
        this.g = 0xFF & (color >> 8 );
        this.b = 0xFF & (color >> 0 );
    }

    public Color(int r, int g, int b){
        this.a = 255;
        this.r = r;
        this.b = b;
        this.g = g;
        color = (255 << 24 | r << 16 | g << 8 | b);
    }

    public Color(int a, int r, int g, int b){
        this.a = a;
        this.r = r;
        this.b = b;
        this.g = g;
        color = (a << 24 | r << 16 | g << 8 | b);
    }

    public void setRGB(int r, int g, int b){
        this.a = 255;
        this.r = r;
        this.b = b;
        this.g = g;
        color = (255 << 24 | r << 16 | g << 8 | b);
    }
    public void setARGB(int a, int r, int g, int b){
        this.a = a;
        this.r = r;
        this.b = b;
        this.g = g;
        color = (a << 24 | r << 16 | g << 8 | b);
    }

    public int getARGB(){
        color = (a << 24 | r << 16 | g << 8 | b);
        return  color;
    }

    public int getRGB(){
        color = (255 << 24 | r << 16 | g << 8 | b);
        return  color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        if(a<0x00) a = 0x00;
        else if(a>0xFF) a = 0xFF;
        this.a = a;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        if(r<0x00) r = 0x00;
        else if(r>0xFF) r = 0xFF;
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        if(g<0x00) g = 0x00;
        else if(g>0xFF) g = 0xFF;
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        if(b<0x00) b = 0x00;
        else if(b>0xFF) b = 0xFF;
        this.b = b;
    }
}
