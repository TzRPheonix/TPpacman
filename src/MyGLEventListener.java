import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import java.util.ArrayList;
import java.util.Arrays;


//Applications implement the GLEventListener interface to perform OpenGL drawing via callbacks.
public class MyGLEventListener implements GLEventListener {


    private int dureAnimation = 0;
    private boolean test = true;
    Entite pacman = new Entite(1, 1);
    int deplaFTick = 0;
    int lockPlateau = 0;
    Plateau P = new Plateau(15); //taille => 10 sinon chiant
    Entite fantome = new Entite(P.getTailleLaby() - 2, P.getTailleLaby() - 2);
    Entite bonusA = new Entite(P.getTailleLaby() - 2, 1);
    Entite bonusB = new Entite(1, P.getTailleLaby() - 2);
    boolean bonusAtaken = false;
    boolean bonusBtaken = false;
    boolean pacmanGauche = false;
    boolean pacmanDroite = false;
    boolean pacmanHaut = false;
    boolean pacmanBas = false;
    boolean pouvoir = false;
    int pouvoirDuration = 0;
    float angleF;
    GLUT glut;
    GLU glu;

    //About the camera and the visualization
    SceneMouseAdapter objectMouse;
    SceneKeyAdapter objectKeys;
    private float camera[] = {0.0f, 9.0f, 0.0f};
    private float view_rotx = 0.0f, view_roty = 0.0f;
    private float scale = 5.0f;
    private float aspect;

    //PredemaxPased colors
    float red[] = {0.8f, 0.1f, 0.0f, 0.7f};
    float green[] = {0.0f, 0.8f, 0.2f, 0.7f};
    float blue[] = {0.2f, 0.2f, 1.0f, 0.7f};

    //Light properties
    float light_ambient[] = {0.0f, 0.0f, 0.0f, 1.0f};
    float light_diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float light_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float light_position[] = {10.0f, 10.0f, 10.0f, 0.0f};
    float light_position2[] = {-10.0f, -10.0f, 10.0f, 0.0f};
    float light_position3[] = {0.0f, 0.0f, -10.0f, 0.0f};


    //////////////////////////////////////////////////////////////////////////////////////////////:
    // TO FILL


    /**
     * The init() method is called when a new OpenGL context is created for the given GLAutoDrawable.
     * Any display lists or textures used during the application's normal rendering loop can be safely
     * initialized in init(). The GLEventListener's init() method may be called more than once during
     * the lifetime of the application. The init() method should therefore be kept as short as possible
     * and only contain the OpenGL initialization required for the display() method to run properly.
     */
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        //For the light and the material
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light_ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, light_ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, light_specular, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light_position2, 0);

        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, light_ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, light_specular, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, light_position3, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHT2);

        //Various tests
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glShadeModel(GL2.GL_SMOOTH);

        objectMouse = new SceneMouseAdapter(this);
        objectKeys = new SceneKeyAdapter(this, pacman);

        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addMouseListener(objectMouse);
            window.addKeyListener(objectKeys);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTMouseAdapter(objectMouse, drawable).addTo(comp);
            new AWTKeyAdapter(objectKeys, drawable).addTo(comp);
        }

        gl.glEnable(GL2.GL_NORMALIZE);

        glut = new GLUT();
        glu = new GLU();

        /////////////////////////////////////////////////////////////////////////////////////
        //TO FILL

        //...
    }


    /**
     * Called when the drawable has been resized
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();

        aspect = (float) width / (float) height;
        gl.glViewport(x, y, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(60.0f, (float) aspect, 0.1f, 100.0f);


        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {

    }


    /**
     * Called to perform per-frame rendering.
     */
    public void display(GLAutoDrawable drawable) {

        // Get the GL corresponding to the drawable we are animating
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        if (lockPlateau == 0) {
            P.genCoord();
        }
        lockPlateau = 1;


        glu.gluLookAt(camera[0], camera[1] + scale, camera[2], //position
                P.getTailleLaby() / 2.0f, 0.0f, 5, //regarde
                0.0f, 1.0f, 0.0f);

        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);


        /////////////////////////////////////////////////////////////////////////////////
        //TO FILL

        for (int i = 0; i < P.getTailleLaby() * 4 - 4; i++) {
            Point3D A1 = new Point3D(P.coordLabyImpo.get(i).getX(), 0, P.coordLabyImpo.get(i).getY()); //devant en bas ?? gauche
            Point3D A2 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 0, P.coordLabyImpo.get(i).getY()); //?? droite
            Point3D A3 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 1, P.coordLabyImpo.get(i).getY()); //en haut
            Point3D A4 = new Point3D(P.coordLabyImpo.get(i).getX(), 1, P.coordLabyImpo.get(i).getY());//a gauche
            Point3D A5 = new Point3D(P.coordLabyImpo.get(i).getX(), 1, P.coordLabyImpo.get(i).getY() + 1);//au fond
            Point3D A6 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 1, P.coordLabyImpo.get(i).getY() + 1);//?? droite
            Point3D A7 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 0, P.coordLabyImpo.get(i).getY() + 1);//en bas
            Point3D A8 = new Point3D(P.coordLabyImpo.get(i).getX(), 0, P.coordLabyImpo.get(i).getY() + 1);//a gauche

            Point3D[] coords = {A1, A2, A3, A4, A5, A6, A7, A8};

            gl.glColor3d(0, 0, 1);
            gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glEnd();
            //Every push needs a pop !
            gl.glPopMatrix();
        }

        for (int i = 0; i < P.getTailleLaby() * P.getTailleLaby(); i++) {
            Point3D A1 = new Point3D(P.coordLabySol.get(i).getX(), -1, P.coordLabySol.get(i).getY()); //devant en bas ?? gauche
            Point3D A2 = new Point3D(P.coordLabySol.get(i).getX() + 1, -1, P.coordLabySol.get(i).getY()); //?? droite
            Point3D A3 = new Point3D(P.coordLabySol.get(i).getX() + 1, 0, P.coordLabySol.get(i).getY()); //en haut
            Point3D A4 = new Point3D(P.coordLabySol.get(i).getX(), 0, P.coordLabySol.get(i).getY());//a gauche
            Point3D A5 = new Point3D(P.coordLabySol.get(i).getX(), 0, P.coordLabySol.get(i).getY() + 1);//au fond
            Point3D A6 = new Point3D(P.coordLabySol.get(i).getX() + 1, 0, P.coordLabySol.get(i).getY() + 1);//?? droite
            Point3D A7 = new Point3D(P.coordLabySol.get(i).getX() + 1, -1, P.coordLabySol.get(i).getY() + 1);//en bas
            Point3D A8 = new Point3D(P.coordLabySol.get(i).getX(), -1, P.coordLabySol.get(i).getY() + 1);//a gauche

            Point3D[] coords = {A1, A2, A3, A4, A5, A6, A7, A8};

            gl.glColor3d(1, 0, 1);
            gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());

            gl.glEnd();

            //Every push needs a pop !


            gl.glPopMatrix();
        }

        for (int i = 0; i < P.coordLabyObs.size(); i++) {
            Point3D A1 = new Point3D(P.coordLabyObs.get(i).getX(), 0, P.coordLabyObs.get(i).getY()); //devant en bas ?? gauche
            Point3D A2 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 0, P.coordLabyObs.get(i).getY()); //?? droite
            Point3D A3 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 1, P.coordLabyObs.get(i).getY()); //en haut
            Point3D A4 = new Point3D(P.coordLabyObs.get(i).getX(), 1, P.coordLabyObs.get(i).getY());//a gauche
            Point3D A5 = new Point3D(P.coordLabyObs.get(i).getX(), 1, P.coordLabyObs.get(i).getY() + 1);//au fond
            Point3D A6 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 1, P.coordLabyObs.get(i).getY() + 1);//?? droite
            Point3D A7 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 0, P.coordLabyObs.get(i).getY() + 1);//en bas
            Point3D A8 = new Point3D(P.coordLabyObs.get(i).getX(), 0, P.coordLabyObs.get(i).getY() + 1);//a gauche

            Point3D[] coords = {A1, A2, A3, A4, A5, A6, A7, A8};

            gl.glColor3d(0, 1, 0);
            gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[2].getPx(), coords[2].getPy() * Math.sqrt(3) / 2, coords[2].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[3].getPx(), coords[3].getPy() * Math.sqrt(3) / 2, coords[3].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[5].getPx(), coords[5].getPy() * Math.sqrt(3) / 2, coords[5].getPz());
            gl.glVertex3d(coords[4].getPx(), coords[4].getPy() * Math.sqrt(3) / 2, coords[4].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());

            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(coords[1].getPx(), coords[1].getPy() * Math.sqrt(3) / 2, coords[1].getPz());
            gl.glVertex3d(coords[6].getPx(), coords[6].getPy() * Math.sqrt(3) / 2, coords[6].getPz());
            gl.glVertex3d(coords[7].getPx(), coords[7].getPy() * Math.sqrt(3) / 2, coords[7].getPz());
            gl.glVertex3d(coords[0].getPx(), coords[0].getPy() * Math.sqrt(3) / 2, coords[0].getPz());


            gl.glEnd();
            gl.glPopMatrix();
        }

        if (pacman.getX() == bonusA.getX() && pacman.getZ() == bonusA.getZ() && !bonusAtaken) { //V??rification position pacman + Bonus encore pr??sent
            bonusAtaken = true;
            pouvoir = true;
            pouvoirDuration = 0;  // prendre des pouvoirs en m??me temps r??initialise la dur??e du pouvoir
        }

        if (pacman.getX() == bonusB.getX() && pacman.getZ() == bonusB.getZ() && !bonusBtaken) {
            bonusBtaken = true;
            pouvoir = true;
            pouvoirDuration = 0;
        }

        ArrayList<Point2D> fantoDeplaImpo = new ArrayList<>();
        //Ajout constant dans une liste des cases autours du fant??me
        ArrayList<Point2D> fantoDepla = new ArrayList<>();
        Point2D hautFantome = new Point2D(fantome.getX() + 1, fantome.getZ());
        Point2D basFantome = new Point2D(fantome.getX() - 1, fantome.getZ());
        Point2D droiteFantome = new Point2D(fantome.getX(), fantome.getZ() + 1);
        Point2D gaucheFantome = new Point2D(fantome.getX(), fantome.getZ() - 1);
        fantoDepla.add(hautFantome);
        fantoDepla.add(basFantome);
        fantoDepla.add(droiteFantome);
        fantoDepla.add(gaucheFantome);


        deplaPacman();

        deplaFantome();



        gl.glColor3d(0, 1, 1);
        gl.glPushMatrix();

        gl.glTranslatef(fantome.getX() + 0.5f, 0, fantome.getZ() + 0.5f);
        glut.glutWireSphere(0.5, 10, 10);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glColor3d(1, 0, 0);
        if (!bonusAtaken) {    //affichage bonus A
            gl.glPushMatrix();

            gl.glTranslatef(bonusA.getX() + 0.5f, 0, bonusA.getZ() + 0.5f);
            glut.glutWireSphere(0.3, 10, 10);

            gl.glEnd();
            gl.glPopMatrix();
        }

        if (!bonusBtaken) {    //affichage bonus B
            gl.glPushMatrix();

            gl.glTranslatef(bonusB.getX() + 0.5f, 0, bonusB.getZ() + 0.5f);
            glut.glutWireSphere(0.3, 10, 10);

            gl.glEnd();
            gl.glPopMatrix();
        }


        //maxPas du jeu sur superposition de pacman et du fant??me -> d??cision selon pouvoir (Pas n??cessaire)
        //if(pacman.getX() == fantome.getX() && fantome.getZ() == pacman.getZ() && !pouvoir) {
        //	System.out.println("Vous avez perdu !");
        //	System.exit(0);
        //}


        double angleDebut = (Math.PI / 6);

        if (pouvoir) {
            gl.glColor3d(1, 1, 1);
            ;
        } else {
            gl.glColor3d(1, 1, 0);
        }


        gl.glTranslatef(pacman.getX() + 0.5f, 0.5f, pacman.getZ() + 0.5f);

        if(pacmanGauche){
            gl.glRotatef(90,0,1,0);
        }
        if(pacmanBas){
            gl.glRotatef(180,0,1,0);
        }
        if(pacmanDroite){
            gl.glRotatef(270,0,1,0);
        }
        if(pacmanHaut){
            gl.glRotatef(360,0,1,0);
        }
        gl.glScaled(0.5, 0.5, 0.5);
        int step = 1;
        int maxStep = 9;


        if (dureAnimation == 15) {
            maxStep = setAnimation();
            setdureAnimation();
        } else {
            dureAnimation += 1;
        }


        double maxPas = (2 * Math.PI - (angleDebut - ((double) step / (double) maxStep)));
        double angle = (Math.PI / 12);
        for (double j = 0; j < Math.PI; j += angle) {
            double b1;
            double a1 = j + angle;
            gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);

            for (double i = angleDebut; i < maxPas; i += angle) {
                b1 = i + angle;
                if (b1 > maxPas) b1 = maxPas;

                gl.glVertex3d(Math.sin(j) * Math.cos(i), Math.cos(j), Math.sin(j) * Math.sin(i));
                gl.glVertex3d(Math.sin(a1) * Math.cos(i), Math.cos(a1), Math.sin(a1) * Math.sin(i));
                gl.glVertex3d(Math.sin(a1) * Math.cos(b1), Math.cos(a1), Math.sin(a1) * Math.sin(b1));
                gl.glVertex3d(Math.sin(j) * Math.cos(b1), Math.cos(j), Math.sin(j) * Math.sin(b1));

            }

            gl.glVertex3d(Math.sin(j) * Math.cos(maxPas), Math.cos(j), Math.sin(j) * Math.sin(maxPas));
            gl.glVertex3d(Math.sin(a1) * Math.cos(maxPas), Math.cos(a1), Math.sin(a1) * Math.sin(maxPas));
            gl.glVertex3d(0, Math.cos(a1), 0);
            gl.glVertex3d(0, Math.cos(j), 0);

            gl.glEnd();

            gl.glPopMatrix();
        }

        if (pacman.getX() == fantome.getX() && fantome.getZ() == pacman.getZ() && pouvoir) {
            System.out.println("Vous avez gagne !");
            System.exit(0);
        }
        angleF += 1f;
        deplaFTick += 1;
        if (pouvoirDuration > 400) {    //Dur??e du pouvoir
            pouvoir = false;
        }
        if (pouvoir) {
            pouvoirDuration += 1;
        }
    }

    public void rotationPacman(String direct){
        if(direct.equals("gauche")){
            pacmanGauche = true;
            pacmanBas = false;
            pacmanHaut = false;
            pacmanDroite = false;
        }
        if(direct.equals("droite")){
            pacmanGauche = false;
            pacmanBas = false;
            pacmanHaut = false;
            pacmanDroite = true;
        }
        if(direct.equals("haut")){
            pacmanGauche = false;
            pacmanBas = false;
            pacmanHaut = true;
            pacmanDroite = false;
        }
        if(direct.equals("bas")){
            pacmanGauche = false;
            pacmanBas = true;
            pacmanHaut = false;
            pacmanDroite = false;
        }
    }

    public void deplaPacman() {
        boolean basV = true;
        if (pacman.isBas()) {
            Point2D test = new Point2D(pacman.getX() - 1, pacman.getZ());
            for (int p = 0; p < P.coordLabyObs.size(); p++) {
                if (P.coordLabyObs.get(p).getX() == test.getX() && P.coordLabyObs.get(p).getY() == test.getY()) {
                    basV = false;
                }
            }  //check si la case n'est pas occup??e par un obstacle
            for (int p = 0; p < P.coordLabyImpo.size(); p++) {
                if (P.coordLabyImpo.get(p).getX() == test.getX() && P.coordLabyImpo.get(p).getY() == test.getY()) {
                    basV = false;
                }
            }  //check si la case n'est pas occup??e par un mur
            if (basV) {
                pacman.setX(pacman.getX() - 1);
            }
            rotationPacman("bas");
            pacman.setBas(false);
        }

        boolean hautV = true;
        if (pacman.isHaut()) {
            Point2D test = new Point2D(pacman.getX() + 1, pacman.getZ());
            for (int p = 0; p < P.coordLabyObs.size(); p++) {
                if (P.coordLabyObs.get(p).getX() == test.getX() && P.coordLabyObs.get(p).getY() == test.getY()) {
                    hautV = false;
                }
            }
            for (int p = 0; p < P.coordLabyImpo.size(); p++) {
                if (P.coordLabyImpo.get(p).getX() == test.getX() && P.coordLabyImpo.get(p).getY() == test.getY()) {
                    hautV = false;
                }
            }
            if (hautV) {
                pacman.setX(pacman.getX() + 1);
            }
            rotationPacman("haut");
            pacman.setHaut(false);
        }

        boolean droitV = true;
        if (pacman.isDroite()) {
            Point2D test = new Point2D(pacman.getX(), pacman.getZ() + 1);
            for (int p = 0; p < P.coordLabyObs.size(); p++) {
                if (P.coordLabyObs.get(p).getX() == test.getX() && P.coordLabyObs.get(p).getY() == test.getY()) {
                    droitV = false;
                }
            }
            for (int p = 0; p < P.coordLabyImpo.size(); p++) {
                if (P.coordLabyImpo.get(p).getX() == test.getX() && P.coordLabyImpo.get(p).getY() == test.getY()) {
                    droitV = false;
                }
            }
            if (droitV) {
                pacman.setZ(pacman.getZ() + 1);
            }
            rotationPacman("droite");
            pacman.setDroite(false);
        }

        boolean gaucheV = true;
        if (pacman.isGauche()) {
            Point2D test = new Point2D(pacman.getX(), pacman.getZ() - 1);
            for (int p = 0; p < P.coordLabyObs.size(); p++) {
                if (P.coordLabyObs.get(p).getX() == test.getX() && P.coordLabyObs.get(p).getY() == test.getY()) {
                    gaucheV = false;
                }
            }
            for (int p = 0; p < P.coordLabyImpo.size(); p++) {
                if (P.coordLabyImpo.get(p).getX() == test.getX() && P.coordLabyImpo.get(p).getY() == test.getY()) {
                    gaucheV = false;
                }
            }
            if (gaucheV) {
                pacman.setZ(pacman.getZ() - 1);
            }
            rotationPacman("gauche");
            pacman.setGauche(false);
        }
    }

    public void deplaFantome() {
        ArrayList<Point2D> fantoDeplaImpo = new ArrayList<>();
        //Ajout constant dans une liste des cases autours du fant??me
        ArrayList<Point2D> fantoDepla = new ArrayList<>();
        Point2D hautFantome = new Point2D(fantome.getX() + 1, fantome.getZ());
        Point2D basFantome = new Point2D(fantome.getX() - 1, fantome.getZ());
        Point2D droiteFantome = new Point2D(fantome.getX(), fantome.getZ() + 1);
        Point2D gaucheFantome = new Point2D(fantome.getX(), fantome.getZ() - 1);
        fantoDepla.add(hautFantome);
        fantoDepla.add(basFantome);
        fantoDepla.add(droiteFantome);
        fantoDepla.add(gaucheFantome);

        String direct = "pas encore choisi";
        if (deplaFTick == 15) { //tic du fantome initialis?? ?? 15 (+ grande valeur = fantome plus lent)
            for (int n = 0; n < fantoDepla.size(); n++) {
                for (int m = 0; m < P.coordLabyObs.size(); m++) {
                    if (P.coordLabyObs.get(m).getX() == fantoDepla.get(n).getX() && P.coordLabyObs.get(m).getY() == fantoDepla.get(n).getY()) {
                        fantoDeplaImpo.add(P.coordLabyObs.get(m));
                    }
                }
            } //ajout dans une liste des positions que le fant??me ne peut pas atteindre en fonction des obstacles
            for (int n = 0; n < fantoDepla.size(); n++) {
                for (int m = 0; m < P.coordLabyImpo.size(); m++) {
                    if (P.coordLabyImpo.get(m).getX() == fantoDepla.get(n).getX() && P.coordLabyImpo.get(m).getY() == fantoDepla.get(n).getY()) {
                        fantoDeplaImpo.add(P.coordLabyImpo.get(m));
                    }
                }
            } //ajout dans une liste des positions que le fant??me ne peut pas atteindre en fonction des murs
            ArrayList<String> deplaListe = new ArrayList<>(Arrays.asList("bas", "haut", "droite", "gauche"));
            for (int i = 0; i < fantoDeplaImpo.size(); i++) {
                if (fantoDeplaImpo.get(i).getX() == basFantome.getX() && fantoDeplaImpo.get(i).getY() == basFantome.getY()) {
                    deplaListe.remove("bas");
                }
                if (fantoDeplaImpo.get(i).getX() == hautFantome.getX() && fantoDeplaImpo.get(i).getY() == hautFantome.getY()) {
                    deplaListe.remove("haut");
                }
                if (fantoDeplaImpo.get(i).getX() == droiteFantome.getX() && fantoDeplaImpo.get(i).getY() == droiteFantome.getY()) {
                    deplaListe.remove("droite");
                }
                if (fantoDeplaImpo.get(i).getX() == gaucheFantome.getX() && fantoDeplaImpo.get(i).getY() == gaucheFantome.getY()) {
                    deplaListe.remove("gauche");
                }
            }    // trie de la liste en y laissant que les choix possible pour le fantome
            int choixRandom = (int) (Math.random() * deplaListe.size());
            // choix al??atoire dans les choix restants
            deplaFTick = 0;
            direct = deplaListe.get(choixRandom);
            if (direct.equals("bas")) {
                fantome.setX(fantome.getX() - 1);
            }
            if (direct.equals("haut")) {
                fantome.setX(fantome.getX() + 1);
            }
            if (direct.equals("droite")) {
                fantome.setZ(fantome.getZ() + 1);
            }
            if (direct.equals("gauche")) {
                fantome.setZ(fantome.getZ() - 1);
            }
        }
    }


    public int setAnimation() {
        int steps = 0;
        if (test) {
            steps = 1;
            this.test = false;
            return steps;
        } else {
            steps = 9;
            this.test = true;
            return steps;
        }
    }


    //GETTER AND SETTER
    //*************************************************************
    public float getView_rotx() {
        return view_rotx;
    }

    public void setdureAnimation() {
        this.dureAnimation = 0;
    }

    public void setView_rotx(float view_rotx) {
        this.view_rotx = view_rotx;
    }

    public float getView_roty() {
        return view_roty;
    }

    public void setView_roty(float view_roty) {
        this.view_roty = view_roty;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale2) {
        this.scale = scale2;
    }


}