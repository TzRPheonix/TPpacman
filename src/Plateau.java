import java.util.ArrayList;

public class Plateau {
    private int tailleLaby;
    public ArrayList<Point2D> coordLabyImpo; //Permet d'avoir les bords du plateau
    public ArrayList<Point2D> coordLabySol; //Permet d'avoir le sol du plateau
    public ArrayList<Point2D> coordLabyObs; //Permet d'avoir les obstacles du plateau

    public Plateau(int tailleLaby) {
        this.tailleLaby = tailleLaby;
        this.coordLabySol = new ArrayList<>();
        this.coordLabyImpo = new ArrayList<>();
        this.coordLabyObs = new ArrayList<>();
    }

    public void genCoord(){
        for(int i = 0; i<tailleLaby; i++){
            coordLabyImpo.add(new Point2D(i,0));
        }
        for(int i = 1; i<tailleLaby; i++){
            coordLabyImpo.add(new Point2D(0,i));
            coordLabyImpo.add(new Point2D(i,tailleLaby-1));
        }
        for(int i = 1; i<tailleLaby-1; i++){
            coordLabyImpo.add(new Point2D(tailleLaby-1,i));
        }
        for(int i = 0; i<tailleLaby;i++){
            for(int j = 0; j<tailleLaby;j++){
                coordLabySol.add(new Point2D(i,j));
            }
        }
        for (int i = 0; i < tailleLaby; i++) {
            for (int j = 0; j < tailleLaby; j += 2) {
                if (i > 1 && i < tailleLaby - 2 && i != (tailleLaby - 1) / 2 && i != (tailleLaby + 1) / 2) {
                    coordLabyObs.add(new Point2D((i), (j)));
                }
            }
        }
    }



    public int getTailleLaby() {
        return tailleLaby;
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "tailleLaby=" + tailleLaby +
                ", coordLabyImpo=" + coordLabyImpo +
                '}';
    }
}
