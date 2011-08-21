package nl.uscki.robot.mennov1;

public class Count extends Command {

	@Override
	public int execute(String[] args) {
		System.out.println("There are currently " + MennoV1.master.listenerBots.size() + "bot(s) loaded.");
		return 0;
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName() + ": gives the current number of bots loaded";
	}

}
