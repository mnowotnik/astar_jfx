package mvc.controller;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public class EventTuple {
	private final EventType<MouseEvent> type;
	private final EventHandler<MouseEvent> handler;

	public EventTuple(EventType<MouseEvent> type,
			EventHandler<MouseEvent> handler) {
		super();
		this.type = type;
		this.handler = handler;
	}

	public EventHandler<MouseEvent> getHandler() {
		return handler;

	}

	public EventType<MouseEvent> getType() {
		return type;
	}

}
