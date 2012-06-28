package mennov1;
import java.util.EventListener;
import java.util.EventObject;

public interface Listener<SubEvent extends EventObject> extends EventListener {
	public Boolean wants(EventObject e);
	
	public void event(SubEvent e);
}
