public class Pacman{
    private int x;
    private int z;
    private boolean haut;
    private boolean bas;
    private boolean droite;
    private boolean gauche;

    public Pacman(int x, int z) {
        this.x = x;
        this.z = z;
        this.haut = false;
        this.bas = false;
        this.droite = false;
        this.gauche = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isHaut() {
        return haut;
    }

    public void setHaut(boolean haut) {
        this.haut = haut;
    }

    public boolean isBas() {
        return bas;
    }

    public void setBas(boolean bas) {
        this.bas = bas;
    }

    public boolean isDroite() {
        return droite;
    }

    public void setDroite(boolean droite) {
        this.droite = droite;
    }

    public boolean isGauche() {
        return gauche;
    }

    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }
}

