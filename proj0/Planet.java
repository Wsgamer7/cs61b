import java.util.Arrays;

public class Planet{
	public static final double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet p){
		return Math.sqrt((p.xxPos - xxPos)*(p.xxPos - xxPos) + (p.yyPos - yyPos)*(p.yyPos - yyPos));
	}
	public double calcForceExertedByX(Planet p){
		double totalForce = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		double dx = p.xxPos - this.xxPos;
		return totalForce * (dx/r);
	}
	public double calcNetForceExertedByX(Planet[] planets){
		double totalForce = 0;
		for (Planet p : planets){
			if(this.equals(p)){
				continue;
			}else{
				totalForce = totalForce + this.calcForceExertedByX(p);
			}
		}
		return totalForce;
	}
	public double calcNetForceExertedByY(Planet[] planets){
		double totalForce = 0;
		for (Planet p : planets){
			if(this.equals(p)){
				continue;
			}else{
				totalForce = totalForce + this.calcForceExertedByY(p);
			}
		}
		return totalForce;
	}
	public void update(double dt, double fX, double fY){
		double aX = fX/mass;
		double aY = fY/mass;
		xxVel = xxVel + aX * dt;
		yyVel = yyVel + aY * dt;
		xxPos = xxPos + xxVel*dt;
		yyPos = yyPos + yyVel*dt;
	}
	public double calcForceExertedByY(Planet p){
		double dx = p.xxPos -this.xxPos;
		double dy = p.yyPos - this.yyPos;
		return this.calcForceExertedByX(p)*(dy/dx);
	}
	public double calcForceExertedBy(Planet p){
		double r = this.calcDistance(p);
		return (G * mass * p.mass)/(r*r);
	}
	public void draw(){
		StdDraw.picture(xxPos, yyPos, imgFileName);
	}
}

