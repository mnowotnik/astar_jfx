package mvc;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mvc.controller.EventTuple;
import mvc.view.Buttons;
import mvc.view.VisibleEdge;
import mvc.view.VisiblePoint;
import util.StringUtil;

import common.Point;

public class Controller {
	private final Model model;
	private final View view;
	private EventTuple clickAction;
	private boolean computed = false;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void init() {
		view.addBtnEvent(MouseEvent.MOUSE_CLICKED, new GoToNavi(), Buttons.NEXT);
	}

	private void handleMouse() {

		clickAction = new EventTuple(MouseEvent.MOUSE_CLICKED, new PointClick());
		view.getNaviView().addPointEvent(clickAction.getType(),
				clickAction.getHandler());
		view.getNaviView().addPointEvent(MouseEvent.MOUSE_EXITED,
				new NodeExit());

		view.getNaviView().addPointEvent(MouseEvent.MOUSE_ENTERED,
				new PointHover());
		view.getNaviView().addEdgeEvent(MouseEvent.MOUSE_ENTERED,
				new EdgeHover());

		view.getNaviView().addNodeExitEvent(MouseEvent.MOUSE_EXITED,
				new NodeExit());

	}

	public class ComputeSolution implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (!computed) {
				List<Point> clickedPoints = view.getNaviView()
						.getClickedPoints();

				if (clickedPoints.size() < 2)
					return;
				if (clickedPoints.size() > 2)
					System.err.println("Clicked points list>2");

				computed = true;

				List<Point> optimalRoute = model.computeSolution(
						clickedPoints.get(0), clickedPoints.get(1));

				view.getNaviView().showOptimalRoute(optimalRoute);
				view.getNaviView().removePointEvent(clickAction.getType(),
						clickAction.getHandler());
			}

		}

	}

	public class EdgeHover implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (event.getSource() instanceof VisibleEdge) {
				VisibleEdge visEdge = (VisibleEdge) event.getSource();
				view.getNaviView().showPopup(visEdge);
				visEdge.toFront();
			}

		}

	}

	public class GoToMenu implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			view.makeMenuView();
			view.addBtnEvent(MouseEvent.MOUSE_CLICKED, new GoToNavi(),
					Buttons.NEXT);
			view.switchToMenu();

		}

	}

	public class GoToNavi implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			String r = view.getMenuView().getRValue();
			String n = view.getMenuView().getNValue();
			if (StringUtil.isDouble(r) && StringUtil.isInteger(n)) {
				double rParam = Double.parseDouble(r);
				int nParam = Integer.parseInt(n);
				model.newNaviModel(nParam, rParam);
				view.makeNaviView(model.getPoints(), model.getSegments());

				handleMouse();
				view.addBtnEvent(MouseEvent.MOUSE_CLICKED, new GoToMenu(),
						Buttons.BACK);
				view.addBtnEvent(MouseEvent.MOUSE_CLICKED, new RollNew(),
						Buttons.ROLL);
				view.addBtnEvent(MouseEvent.MOUSE_CLICKED, new ResetGraph(),
						Buttons.RESET);
				view.addBtnEvent(MouseEvent.MOUSE_CLICKED,
						new ComputeSolution(), Buttons.COMPUTE);

				view.switchToNavi();
                computed = false;
			} else {

				view.getMenuView().showError();
			}

		}

	}

	public class NodeExit implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			if (event.getSource() instanceof VisiblePoint) {
				view.getNaviView().hidePopup();
			} else if (event.getSource() instanceof VisibleEdge) {
				view.getNaviView().hidePopup();
				VisibleEdge visE = (VisibleEdge) event.getSource();
				visE.toBack();
			}

		}

	}

	public class PointClick implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (event.getSource() instanceof VisiblePoint) {

				VisiblePoint visPoint = (VisiblePoint) event.getSource();
				view.getNaviView().clickPoint(visPoint);
			}

		}

	}

	public class PointHover implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (event.getSource() instanceof VisiblePoint) {

				VisiblePoint visPoint = (VisiblePoint) event.getSource();
				visPoint.toFront();
				view.getNaviView().showPopup(visPoint);
			}
		}

	}

	public class ResetGraph implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			view.getNaviView().resetGraph();
			view.getNaviView().removePointEvent(clickAction.getType(),
					clickAction.getHandler());
			clickAction = new EventTuple(MouseEvent.MOUSE_CLICKED,
					new PointClick());
			view.getNaviView().addPointEvent(clickAction.getType(),
					clickAction.getHandler());
			computed = false;


		}

	}

	public class RollNew implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			view.getNaviView().clearGraph();
			model.newGraph();
			view.getNaviView().setPoints(model.getPoints());
			view.getNaviView().setEdges(model.getSegments());
			handleMouse();
			computed = false;
		}

	}

}
