package GUI;

import javax.swing.ImageIcon;


public enum Avatar{
	//aiham
	DEFAULT("rabbit1.jpg"),
	RENARD("rabbit10.jfif"),
	CHOUETTE("rabbit11.webp"),
	COCHON_D_INDE("rabbit12.jpg"),
	YEUX_ROUGES("rabbit13.jpg"),
	ESCARGOTS("rabbit14.jpg"),
	FLEUR("rabbit15.webp"),
	GOUTES("rabbit16.webp"),
	CHATON("rabbit17.webp"),
	CHAT_TETE("rabbit18.jpg"),
	MONGOLFIERERS("rabbit19.jpg"),
	LUNE("rabbit2.jfif"),
	LION("rabbit3.jfif"),
	CHAT_PATAPOUF("rabbit4.jpg"),
	OISEAU("rabbit5.jfif"),
	ROSE("rabbit6.jpg"),
	CHEVAL("rabbit7.webp"),
	LOUP("rabbit8.jfif"),
	CHAT_JAUNE("rabbit9.jpg");
	
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