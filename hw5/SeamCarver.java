import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    private int[][] fromMatrix;
    private double[][] energyUntil;
    public SeamCarver(Picture picture) {
        this.picture = picture;
        intiEnergyMatrix();
    }
    private void intiEnergyMatrix() {
        energyMatrix = new double[width()][height()];
        fillEnergyMatrix();
    }
    public Picture picture() {
        return picture;
    } // current picture
    public int width() {
        return picture.width();
    }// width of current picture
    public int height(){
        return picture.height();
    } // height of current picture
    public double energy(int x, int y) {
        return energyMatrix[x][y];
    }// energy of pixel at column x and row y
    private void fillEnergyMatrix() {
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energyMatrix[x][y] = deltaXSquare(x, y) + deltaYSquare(x, y);
            }
        }
    }
    private double deltaXSquare(int x, int y) {
        validatePoint(x, y);
        //deltaX
        int xLeft = x - 1;
        int xRight = x + 1;
        if (xLeft < 0) {
            xLeft = width() - 1;
        }
        if (xRight > width() - 1) {
            xRight = 0;
        }
        return deltaSquare(xLeft, y, xRight, y);
    }
    private double deltaYSquare(int x, int y) {
        validatePoint(x, y);
        int yUp = y - 1;
        int yDown = y + 1;
        if (yUp < 0) {
            yUp = height() - 1;
        }
        if (yDown > height() - 1) {
            yDown = 0;
        }
        return deltaSquare(x, yUp, x, yDown);
    }
    private double deltaSquare(int x1, int y1, int x2, int y2) {
        Color col1 = picture.get(x1, y1);
        Color col2 = picture.get(x2, y2);
        return distSquareOf2Col(col1, col2);
    }
    private double distSquareOf2Col(Color col1, Color col2) {
        double redDiff = col1.getRed() - col2.getRed();
        double greenDiff = col1.getGreen() - col2.getGreen();
        double blueDiff = col1.getBlue() - col2.getBlue();
        return Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2);
    }
    public int[] findHorizontalSeam() {
        Picture transposed = transpose(picture);
        SeamCarver seamCarverTrans = new SeamCarver(transposed);
        return seamCarverTrans.findVerticalSeam();
    }// sequence of indices for horizontal seam
    private Picture transpose(Picture source) {
        Picture target = new Picture(source.height(), source.width());
        for (int y = 0; y < target.height(); y++) {
            for (int x = 0; x < target.width(); x++) {
                Color pixel = source.get(y, x);
                target.set(x, y, pixel);
            }
        }
        return target;
    }
    private void validatePoint(int x, int y) {
        if (x < 0 || x > width() - 1) {
            throw new IndexOutOfBoundsException("out of bounds in horizon");
        }
        if (y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException("out of bounds in vertical");
        }
    }
    public int[] findVerticalSeam(){
        fromMatrix = new int[width()][height()];
        energyUntil = new double[width()][height()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                fromMatrix[x][y] = fillEnergiesVertUntil(x, y);
            }
        }
        int xButton = 0;
        double minTotalEnergies = Double.MAX_VALUE;
        for (int x = 0; x < width(); x++) {
            if (energyUntil[x][height() - 1] < minTotalEnergies) {
                xButton = x;
                minTotalEnergies = energyUntil[x][height() - 1];
            }
        }
        int[] idsVerSeam = new int[height()];
        idsVerSeam[height() - 1] = xButton;
        for (int y = height() - 2; y >= 0; y--) {
            idsVerSeam[y] = idsVerSeam[y + 1] + fromMatrix[idsVerSeam[y + 1]][y + 1];
        }
        return idsVerSeam;
    }// sequence of indices for vertical seam
    /* return -1, 0, 1 if (x, y) from leftUp, up, RightUp respectively,
    and fill the total energies until (x, y) in energyUntil.
     */
    private int fillEnergiesVertUntil(int x, int y) {
        if (y == 0) {
            energyUntil[x][y] = energy(x, y);
            return 0;
        }
        //get three total energies at head
        Map<Integer, Double> fromEnergies = new HashMap<>();
        fromEnergies.put(0, energyUntil[x][y - 1]);
        if (x == 0) {
            fromEnergies.put(1, energyUntil[x + 1][y - 1]);
        } else if (x == width() - 1) {
            fromEnergies.put(-1, energyUntil[x - 1][y - 1]);
        } else {
            fromEnergies.put(-1, energyUntil[x - 1][y - 1]);
            fromEnergies.put(1, energyUntil[x + 1][y - 1]);
        }
        //find the minimum total energies
        int minFrom = 0;
        double minEnergies = fromEnergies.get(0);
        for (int from : fromEnergies.keySet()) {
            if (minEnergies > fromEnergies.get(from)) {
                minFrom = from;
                minEnergies = fromEnergies.get(from);
            }
        }
        //fill in energyUntil
        energyUntil[x][y] = energy(x, y) + minEnergies;
        return minFrom;
    }
    public void removeHorizontalSeam(int[] seam){
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        intiEnergyMatrix();
    }// remove horizontal seam from picture
    public void removeVerticalSeam(int[] seam){
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        intiEnergyMatrix();
    }// remove vertical seam from picture
}