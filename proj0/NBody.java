import java.util.ArrayList;

public class NBody {
    public static double readRadius(String filename){
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];
        for(int i = 0; i < num; i ++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel= in.readDouble();
            double yyVel= in.readDouble();
            double mass = in.readDouble();
            String img = "images/" + in.readString();
            Planet thisPlanet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
            planets[i] = thisPlanet;
        }
        return planets;
    }
    public static void main(String[] args){
        String starfield  = "images/starfield.jpg";
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double universeRadius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.setScale(-universeRadius, universeRadius);
        StdDraw.enableDoubleBuffering();
        for(double t = 0; t < T; t += dt){
            double[] xForce = new double[planets.length];
            double[] yForce = new double[planets.length];
//            StdDraw.clear();
            for (int i = 0; i < planets.length; i++) {
                double totalXF = planets[i].calcNetForceExertedByX(planets);
                double totalYF = planets[i].calcNetForceExertedByY(planets);
                xForce[i] = totalXF;
                yForce[i] = totalYF;
            }
            StdDraw.picture(0, 0, starfield);
            for (int i = 0; i < planets.length; i++){
                planets[i].update(dt, xForce[i], yForce[i]);
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universeRadius);
        for (Planet planet : planets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planet.xxPos, planet.yyPos, planet.xxVel,
                    planet.yyVel, planet.mass, planet.imgFileName);
        }
    }
}
