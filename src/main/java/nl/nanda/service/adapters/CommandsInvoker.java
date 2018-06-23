package nl.nanda.service.adapters;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

import nl.nanda.domain.Command;

@Component
public class CommandsInvoker {
	   
  
	public CommandsInvoker() {
		
	}
	
	@Subscribe
	public void startEvent(Command command) {		
		command.create();
	}

}
