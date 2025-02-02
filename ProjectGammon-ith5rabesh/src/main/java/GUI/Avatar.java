package GUI;

import javax.swing.ImageIcon;


public enum Avatar{
	DEFAULT("rabbit1.jpg"),
	RENARD("rabbit100.jpg"),
	CHOUETTE("rabbit101.jpg"),
	COCHON_D_INDE("rabbit12.jpg"),
	YEUX_ROUGES("rabbit13.jpg"),
	ESCARGOTS("rabbit102.jpg"),
	FLEUR("rabbit103.jpg"),
	GOUTES("rabbit104.jpg"),
	CHATON("rabbit105.jpg"),
	CHAT_TETE("rabbit18.jpg"),
	MONGOLFIERERS("rabbit19.jpg"),
	LUNE("rabbit106.jpg"),
	LION("rabbit107.jpg"),
	CHAT_PATAPOUF("rabbit4.jpg"),
	OISEAU("rabiit108.jpg"),
	ROSE("rabbit6.jpg"),
	CHEVAL("rabbit109.jpg"),
	LOUP("rabbitt.jpg"),
	CHAT_JAUNE("rabbit9.jpg");
	
	private final String path;
	private final ImageIcon icon;
	
	Avatar(String path){
		this.path = ImageAvatar.AVATAR_PATH + path;
		this.icon = new ImageIcon(this.path);
	}

	public ImageIcon getIcon() {
		return icon;
	}
	public String getPath() {
		return path;
	}
}