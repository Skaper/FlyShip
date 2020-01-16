package engine.gfx;

public class Vector2 {
	public float x, y;

	public static Vector2 zero(){
		return new Vector2(0, 0);
	}
	public static Vector2 up(){
		return new Vector2(0, -1);
	}
	public static Vector2 down(){
		return new Vector2(0, 1);
	}
	public static Vector2 left(){
		return new Vector2(-1, 0);
	}
	public static Vector2 right(){
		return new Vector2(1, 0);
	}

	public Vector2() {
		this.x = Float.MIN_VALUE;
		this.y = Float.MIN_VALUE;
	}
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	@Override
	public String toString() {
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}

	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}

	public void setPos(Vector2 position){
		this.x = position.x;
		this.y = position.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Vector2 other = (Vector2) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public void copy(Object obj) {
		if (getClass() != obj.getClass())
			return;
		Vector2 other = (Vector2) obj;
		this.x = other.x;
		this.y = other.y;
	}

	public Vector2 clone(){
		return new Vector2(this.x, this.y);
	}

	public void plus(Vector2 position){
		this.x += position.x;
		this.y += position.y;
	}

	public void minus(Vector2 position){
		this.x -= position.x;
		this.y -= position.y;
	}

	public Vector2 getMinus(Vector2 vector2){
		return new Vector2(this.x - vector2.x, this.y - vector2.y);
	}

	public void multiplyBy(float value) {
		this.x *= value;
		this.y *= value;
	}
	public void divideBy(float value) {
		this.x *= value;
		this.y *= value;
	}

	public void rotate(float angle){
		double rad = Math.toRadians(angle);
		this.x = (float)(Math.cos(rad)*this.x - Math.sin(rad)*this.y);
		this.y = (float)(Math.sin(rad)*this.x + Math.cos(rad)*this.y);
	}

	public float length(){
		return (float)Math.sqrt(x*x + y*y);
	}

	public void normal(){
		float length = length();
		x = x / length;
		y = y / length;

	}


}
