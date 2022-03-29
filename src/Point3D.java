public class Point3D {
    private int Px;
    private int Py;
    private int Pz;

    public Point3D(int px, int py, int pz) {
        Px = px;
        Py = py;
        Pz = pz;
    }

    public int getPx() {
        return Px;
    }

    public void setPx(int px) {
        Px = px;
    }

    public int getPy() {
        return Py;
    }

    public void setPy(int py) {
        Py = py;
    }

    public int getPz() {
        return Pz;
    }

    public void setPz(int pz) {
        Pz = pz;
    }

    @Override
    public String toString() {
        return "Point3D{" + Px + " | " + Py + " | "  + Pz +
                '}';
    }
}
