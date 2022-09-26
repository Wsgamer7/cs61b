public class TestPlanet {
    public static void main(String[] args) {
        String imgFileName = "jupiter.gif";
        Planet p1 = new Planet(0, 0, 4, 5, 6, imgFileName);
        Planet p2 = new Planet(3, 4, 1, 1, 19, imgFileName);
        Planet p3 = new Planet(-4, 3, -9, -3, 7, imgFileName);
        Planet[] allPlanets = {p2, p3};
        double fX = p1.calcNetForceExertedByX(allPlanets);
        double fY = p1.calcNetForceExertedByY(allPlanets);
        p1.update(1, fX, fY);
        System.out.println(p1.xxPos);
    }
}
