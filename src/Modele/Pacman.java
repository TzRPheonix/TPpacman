package Modele;

public class Pacman extends Personnage{


    public Pacman(){
        super(true,0,0,0);
    }

    @Override
    public float getPositionX() {
        return super.getPositionX();
    }

    @Override
    public void setPositionX(float positionX) {
        super.setPositionX(positionX);
    }

    @Override
    public float getPositionY() {
        return super.getPositionY();
    }

    @Override
    public void setPositionY(float positionY) {
        super.setPositionY(positionY);
    }

    @Override
    public float getPositionZ() {
        return super.getPositionZ();
    }

    @Override
    public void setPositionZ(float positionZ) {
        super.setPositionZ(positionZ);
    }

    @Override
    public boolean isVulnerable() {
        return super.isVulnerable();
    }

    @Override
    public void setVulnerable(boolean vulnerable) {
        super.setVulnerable(vulnerable);
    }

    public Pacman(boolean b, float positionX, float positionZ, float positionY) {
        super(b, positionX, positionZ, positionY);
    }
}
