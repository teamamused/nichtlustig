package teamamused.client.gui.welcome;

import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.IPlayer;

public class WelcomeModel extends AbstractModel {
	
	IPlayer player;
	
	public WelcomeModel() {
		super();
		this.player = Client.getInstance().getPlayer();
	}

}
