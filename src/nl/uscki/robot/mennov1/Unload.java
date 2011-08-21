package nl.uscki.robot.mennov1;

public class Unload extends Command {
	
	@Override
	public int execute(String [] args) {
		System.out.println("Woei, in unload!");
		return 0; //
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName()+ ": unloads a certain modue";
	}

}
