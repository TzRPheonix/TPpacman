import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;



//Applications implement the GLEventListener interface to perform OpenGL drawing via callbacks.
public class MyGLEventListener implements GLEventListener {

	
	GLUT glut;
	GLU glu;
	
	//About the camera and the visualization
	SceneMouseAdapter objectMouse;
	SceneKeyAdapter objectKeys;
	private float camera [] = {0.0f, 9.0f, 0.0f};
	private float view_rotx = 0.0f, view_roty = 0.0f;
	private float scale = 5.0f;
	private float aspect;
	
	//Predefined colors
	float red[] = { 0.8f, 0.1f, 0.0f, 0.7f };
	float green[] = { 0.0f, 0.8f, 0.2f, 0.7f };
	float blue[] = { 0.2f, 0.2f, 1.0f, 0.7f };

	//Light properties
	float light_ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	float light_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f};
	float light_position[] = { 10.0f, 10.0f, 10.0f, 0.0f };
	float light_position2[] = { -10.0f, -10.0f, 10.0f, 0.0f };
	float light_position3[] = { 0.0f, 0.0f, -10.0f, 0.0f };


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
		objectKeys = new SceneKeyAdapter(this);
		
		if (drawable instanceof Window) {
			Window window = (Window) drawable;
			window.addMouseListener(objectMouse);
			window.addKeyListener(objectKeys);
		} 
		
		else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
			java.awt.Component comp = (java.awt.Component) drawable;
			new AWTMouseAdapter(objectMouse, drawable).addTo(comp);
			new AWTKeyAdapter(objectKeys, drawable).addTo(comp);
		}
		
		gl.glEnable(GL2.GL_NORMALIZE);
		
		glut =  new GLUT();
		glu =  new GLU();
		
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

		aspect = (float)width / (float)height;
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

		Plateau P = new Plateau(15); //taille => 10 sinon chiant
		P.genCoord();

		glu.gluLookAt(camera[0], camera[1]+scale, camera[2], //position
				P.getTailleLaby()/2.0f, 0.0f, 0.0f, //regarde
				0.0f, 1.0f, 0.0f);
		
		gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
		

		
		/////////////////////////////////////////////////////////////////////////////////
		//TO FILL

		for(int i = 0; i< P.getTailleLaby()*4-4;i++) {
			Point3D A1 = new Point3D(P.coordLabyImpo.get(i).getX(), 0, P.coordLabyImpo.get(i).getY()); //devant en bas à gauche
			Point3D A2 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 0, P.coordLabyImpo.get(i).getY()); //à droite
			Point3D A3 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 1, P.coordLabyImpo.get(i).getY()); //en haut
			Point3D A4 = new Point3D(P.coordLabyImpo.get(i).getX(), 1, P.coordLabyImpo.get(i).getY());//a gauche
			Point3D A5 = new Point3D(P.coordLabyImpo.get(i).getX(), 1, P.coordLabyImpo.get(i).getY() + 1);//au fond
			Point3D A6 = new Point3D(P.coordLabyImpo.get(i).getX() + 1, 1, P.coordLabyImpo.get(i).getY() + 1);//à droite
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

		for(int i = 0; i< P.getTailleLaby()* P.getTailleLaby();i++) {
			Point3D A1 = new Point3D(P.coordLabySol.get(i).getX(), -1, P.coordLabySol.get(i).getY()); //devant en bas à gauche
			Point3D A2 = new Point3D(P.coordLabySol.get(i).getX() + 1, -1, P.coordLabySol.get(i).getY()); //à droite
			Point3D A3 = new Point3D(P.coordLabySol.get(i).getX() + 1, 0, P.coordLabySol.get(i).getY()); //en haut
			Point3D A4 = new Point3D(P.coordLabySol.get(i).getX(), 0, P.coordLabySol.get(i).getY());//a gauche
			Point3D A5 = new Point3D(P.coordLabySol.get(i).getX(), 0, P.coordLabySol.get(i).getY() + 1);//au fond
			Point3D A6 = new Point3D(P.coordLabySol.get(i).getX() + 1, 0, P.coordLabySol.get(i).getY() + 1);//à droite
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


		for(int i = 0; i< P.coordLabyObs.size();i++) {
			Point3D A1 = new Point3D(P.coordLabyObs.get(i).getX(), 0, P.coordLabyObs.get(i).getY()); //devant en bas à gauche
			Point3D A2 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 0, P.coordLabyObs.get(i).getY()); //à droite
			Point3D A3 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 1, P.coordLabyObs.get(i).getY()); //en haut
			Point3D A4 = new Point3D(P.coordLabyObs.get(i).getX(), 1, P.coordLabyObs.get(i).getY());//a gauche
			Point3D A5 = new Point3D(P.coordLabyObs.get(i).getX(), 1, P.coordLabyObs.get(i).getY() + 1);//au fond
			Point3D A6 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 1, P.coordLabyObs.get(i).getY() + 1);//à droite
			Point3D A7 = new Point3D(P.coordLabyObs.get(i).getX() + 1, 0, P.coordLabyObs.get(i).getY() + 1);//en bas
			Point3D A8 = new Point3D(P.coordLabyObs.get(i).getX(), 0, P.coordLabyObs.get(i).getY() + 1);//a gauche

			Point3D[] coords = {A1, A2, A3, A4, A5, A6, A7, A8};

			gl.glColor3d(1, 1, 0);
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
	}



	

	

	//GETTER AND SETTER
	//*************************************************************
	public float getView_rotx() {
		return view_rotx;
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