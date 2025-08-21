package android.support.constraint.solver.widgets;

/* loaded from: classes.dex */
public class Rectangle {
    public int height;
    public int width;
    public int x;
    public int y;

    public boolean contains(int i, int i2) {
        return i >= this.x && i < this.x + this.width && i2 >= this.y && i2 < this.y + this.height;
    }

    public int getCenterX() {
        return (this.x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.y + this.height) / 2;
    }

    void grow(int i, int i2) {
        this.x -= i;
        this.y -= i2;
        this.width += i * 2;
        this.height += 2 * i2;
    }

    boolean intersects(Rectangle rectangle) {
        return this.x >= rectangle.x && this.x < rectangle.x + rectangle.width && this.y >= rectangle.y && this.y < rectangle.y + rectangle.height;
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
    }
}
