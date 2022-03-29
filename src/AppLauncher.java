


public class AppLauncher {

	public static void main(String[] args) {
		Plateau P = new Plateau(4);
		P.genCoord();
		new MainFrame().setVisible(true);
	}

}
