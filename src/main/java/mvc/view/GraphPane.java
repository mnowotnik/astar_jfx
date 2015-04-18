package mvc.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import common.Point;
import common.Segment;

public class GraphPane extends BorderPane {
	private Map<Point, VisiblePoint> visiblePoints;
	private List<VisiblePoint> clickedPoints;
	private Set<VisiblePoint> clickablePoints;
	private Map<Segment, VisibleEdge> edges;
	private Group chosenPath, allElements;
	private double boxWidth, boxHeight;

	public GraphPane(double boxWidth, double boxHeight) {
		visiblePoints = new HashMap<>();
		edges = new HashMap<>();
		clickablePoints = new HashSet<>();
		clickedPoints = new ArrayList<>();
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		allElements = new Group();
		chosenPath = new Group();

		super.getChildren().addAll(chosenPath);
		super.getChildren().addAll(allElements);

	}

	public void showOptimalRoute(List<Point> optimalRoute) {
		Point current = null, previous = null;
		
		if(optimalRoute.size()<2){
			throw new RuntimeException("Lista jest pusta");
		}
		for (Point p : optimalRoute) {
			if(p==null){
				throw new NullPointerException("Punkt jest nullem");
			}
			VisiblePoint vPoint = visiblePoints.get(p);
			allElements.getChildren().remove(vPoint);
			chosenPath.getChildren().add(vPoint);
			vPoint.toFront();

			previous = current;
			current = p;
			if (previous == null) {
				continue;
			}
			Segment e = new Segment(previous, current);
			VisibleEdge vE = edges.get(e);
			
			if(vE == null){
				throw new NullPointerException("Krawedz to null");
			}
			vE.directArrow(previous, current);
			chosenPath.getChildren().add(vE);
			allElements.getChildren().remove(vE);
			vE.toBack();
		}
		chosenPath.toFront();

	}

	public void addEdgeEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		for (VisibleEdge e : edges.values()) {
			e.addEventHandler(eventType, eventHandler);
		}
	}

	public void clearGraph() {
		allElements.getChildren().clear();
		chosenPath.getChildren().clear();
		edges.clear();
		visiblePoints.clear();
		clickablePoints.clear();
		clickedPoints.clear();
	}

	public boolean clickPoint(VisiblePoint vPoint) {
		if (clickedPoints.contains(vPoint)) {
			clickedPoints.remove(vPoint);
			vPoint.check();
			return true;
		} else if (clickedPoints.size() >= 2) {
			return false;
		} else {
			clickedPoints.add(vPoint);
			vPoint.check();
			return true;
		}
	}

	public List<Point> getClickedPoints() {
		List<Point> clickedList = new ArrayList<>();
		for (VisiblePoint p : clickedPoints) {
			clickedList.add(p.getPoint());
		}
		return clickedList;
	}

	public void removePointEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		for (VisiblePoint point : clickablePoints) {
			point.removeEventHandler(eventType, eventHandler);
		}
	}

	public void reset() {

		allElements.getChildren().addAll(chosenPath.getChildren());
		chosenPath.getChildren().clear();
		for (VisiblePoint visP : clickedPoints) {
			visP.check();

		}
		clickedPoints.clear();
		for (VisibleEdge vE : edges.values()) {
			vE.reset();
		}

	}

	public void setEdges(List<Segment> edgeList) {
		for (Segment e : edgeList) {
			VisiblePoint vP1 = visiblePoints.get(e.getA());
			VisiblePoint vP2 = visiblePoints.get(e.getB());
			VisibleEdge visibleEdge = new VisibleEdge(vP1, vP2, e);
			clickablePoints.add(vP1);
			clickablePoints.add(vP2);
			edges.put(e, visibleEdge);
			allElements.getChildren().add(visibleEdge);
		}
		allElements.toFront();

	}

	public void setPoints(List<Point> pointList) {
		for (Point p : pointList) {
			double x = p.getX() * boxWidth;
			double y = p.getY() * boxHeight;
			VisiblePoint visiblePoint = new VisiblePoint(x, y, p);
			// super.getChildren().add(visiblePoint);
			visiblePoints.put(p, visiblePoint);
			allElements.getChildren().add(visiblePoint);
		}
	}

	void addPointEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		if (eventType.equals(MouseEvent.MOUSE_ENTERED)
				|| eventType.equals(MouseEvent.MOUSE_EXITED)) {
			for (VisiblePoint p : visiblePoints.values()) {
				p.addEventHandler(eventType, eventHandler);
			}
		} else if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {
			for (VisiblePoint p : clickablePoints) {
				p.addEventHandler(eventType, eventHandler);
				p.setOpacity(1.0f);
			}
		}
	}

	void pointsToFront() {
		for (VisiblePoint p : clickablePoints) {
			p.toFront();
		}
	}

}
