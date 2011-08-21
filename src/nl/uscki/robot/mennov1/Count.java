package nl.uscki.robot.mennov1;

public class Count extends Command {

	@Override
	public String execute(String[] args) {
		return("There are currently " + MennoV1.master.listenerBots.size() + " bot(s) loaded.");
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName() + ": gives the current number of bots loaded";
	}

}
