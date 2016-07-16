package com.glen.Tools;

public class Matrix4f {

    private int x;
    private int y;
    private int Width;
    private int Height;

    public Matrix4f(int x, int y, int Width, int Height){
        this.x = x;
        this.y = y;
        this.Width = Width;
        this.Height = Height;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }
}
