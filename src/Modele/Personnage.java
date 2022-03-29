package Modele;



public class Personnage {

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(float positionZ) {
        this.positionZ = positionZ;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    private float positionX;
    private float positionY;
    private float positionZ;

    private boolean vulnerable;


    public Personnage(boolean b,float positionX,float positionZ,float positionY) {

        this.vulnerable = b;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
    }
}
