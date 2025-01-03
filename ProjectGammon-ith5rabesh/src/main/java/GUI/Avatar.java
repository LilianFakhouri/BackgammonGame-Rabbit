package GUI;

import javax.swing.ImageIcon;


public enum Avatar{
	//aiham
	DEFAULT("default.png"),
	RENARD("asleep_renard.png"),
	CHOUETTE("chouette.png"),
	COCHON_D_INDE("choupi.png"),
	YEUX_ROUGES("dark_eyes.png"),
	ESCARGOTS("escargot.png"),
	FLEUR("fleur.png"),
	GOUTES("goutes.png"),
	CHATON("mini.png"),
	CHAT_TETE("miou.png"),
	MONGOLFIERERS("Mongolfieres.png"),
	LUNE("night.png"),
	LION("old_lion.png"),
	CHAT_PATAPOUF("Patapouf.png"),
	OISEAU("piou.png"),
	ROSE("rose.png"),
	CHEVAL("wind_horse.png"),
	LOUP("wolf.png"),
	CHAT_JAUNE("yellow_cat.png");
	
	private final String path;
	private final ImageIcon icon;
	
    Avatar(String fileName) {
        this.path = "/images/avatars/" + fileName; // Assuming images are in a folder named "avatars"
        this.icon = new ImageIcon(Avatar.class.getResource(this.path)); // Use getResource
    }


	public ImageIcon getIcon() {
		return icon;
	}
	public String getPath() {
		return path;
	}
}