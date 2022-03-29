package Modele;

public class Partie {


    private int score;
    private int vie;

    public Partie(int score, int vie) {
        this.score = score;
        this.vie = vie;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }


    public void scoreUp(){
        this.score += 10;
    }

    public void viePerdu(){
        this.vie -= 1;
    }

    public void finish(){
        System.out.println("La partie est finie");

    }
}
