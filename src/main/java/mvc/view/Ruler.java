package mvc.view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public class Ruler extends Group {

	public Ruler(double width, double height, double length, int points) {
		super();
		ObservableList<Node> rulerList = super.getChildren();

		double wDelta = width / points;
		for (int i = 0; i < points; i++) {
			rulerList.add(new VLine(1, length, wDelta + i * wDelta));
		}
		double hDelta = height / points;
		for (int i = 0; i < points; i++) {
			rulerList.add(new HLine(1, length, hDelta + i * hDelta));
		}

	}

	class VLine extends Line {
		private final double ystart, yend;

		public VLine(double ystart, double yend, double x) {
			super(x, ystart, x, yend);
			super.setStrokeWidth(1);
			this.ystart = ystart;
			this.yend = yend;
		}

		public Line copy(double x) {
			Line line = new Line(x, ystart, x, yend);
			line.setStrokeWidth(1);
			return line;
		}
	}

	class HLine extends Line {
		private final double xstart, xend;

		public HLine(double xstart, double xend, double y) {
			super(xstart, y, xend, y);
			super.setStrokeWidth(1);
			this.xstart = xstart;
			this.xend = xend;

		}

		public Line copy(double y) {
			Line line = new Line(xstart, y, xend, y);
			line.setStrokeWidth(1);
			return line;
		}
	}

}
