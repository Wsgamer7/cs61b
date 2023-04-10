import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    public SeamCarver(Picture picture) {
        this.picture = picture;
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
        return null;
    }// sequence of indices for horizontal seam
    private void validatePoint(int x, int y) {
        if (x < 0 || x > width() - 1) {
            throw new IndexOutOfBoundsException("out of bounds in horizon");
        }
        if (y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException("out of bounds in vertical");
        }
    }
    public int[] findVerticalSeam(){
        return null;
    }// sequence of indices for vertical seam
    public void removeHorizontalSeam(int[] seam){
        return;
    }// remove horizontal seam from picture
    public void removeVerticalSeam(int[] seam){
        return;
    }// remove vertical seam from picture
}