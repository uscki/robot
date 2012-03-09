public Hahabot implements ListensToJeVerliestEvent{
	public Response handleEvents(Event event) {
		if(event instanceof JeVerliestEvent) {
			return new Response("Je zuigt, " + (JeVerliestEvent) event.verliezer + "!");	
		}

		//should never happen
		else return null;
	}
}
