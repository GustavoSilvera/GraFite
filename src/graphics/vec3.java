package graphics;

public class vec3 {
	private double X, Y, Z;
	public vec3(double x, double y, double z) {
		X = x;
		Y = y;
		Z = z;
	}public vec3(double x, double y) { //(2d vector)
		X = x;
		Y = y;
		Z = 0.0;//defaulted to 0 
	}
	public vec3 times(double s) {//multiply by a scalar
		return new vec3(X * s, Y * s, Z * s);
	}
	public vec3 plus(vec3 v){//add another vector
		return new vec3(X + v.X, Y + v.Y, Z + v.Z);
	}
	public double distance(vec3 v) {
		return Math.sqrt(Math.pow((v.X - X), 2) + Math.pow((v.Y - Y), 2) + Math.pow((v.Z - Z), 2) );
	}public double distanceSqr(vec3 v) {//no sqrt (more optimized)
		return (Math.pow((v.X - X), 2) + Math.pow((v.Y - Y), 2) + Math.pow((v.Z - Z), 2) );
	}
	public double getX() {
		return X;
	}public double getY() {
		return Y;
	}public double getZ() {
		return Z;
	}
	public void setX(double x) {
		X = x;
	}public void setY(double y) {
		Y = y;
	}public void setZ(double z) {
		Z = z;
	}
}
