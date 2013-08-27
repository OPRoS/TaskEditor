package kr.re.etri.tpl.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.Path;
import org.eclipse.draw2d.graph.ShortestPathRouter;

/**
 * Container Figure 에서 Connection 을 child Figure 주위의 최단 경로를 계산하여
 * Connection 을 배치한다.
 * @author sfline
 */
public final class ShortestConnectionRouter
	extends AbstractRouter
{
	private static Logger logger = Logger.getLogger(ShortestConnectionRouter.class);
	/**
	 * 
	 * @author sfline
	 *
	 */
	private class LayoutTracker extends LayoutListener.Stub {
		public void postLayout(IFigure container) {
			processLayout();
		}
		public void remove(IFigure child) {
			removeChild(child);
		}
		public void setConstraint(IFigure child, Object constraint) {
			addChild(child);
		}
	}
	
	private int spacing = 4;
	private Map constraintMap = new HashMap();
	private Map figuresToBounds;
	private Map connectionToPaths;
	private boolean isDirty;
	private HashMap<IFigure, ShortestPathRouter> containerList = new HashMap<IFigure, ShortestPathRouter>();
	private HashMap<ShortestPathRouter, ArrayList<Rectangle>> algorithmToObstacle = new HashMap<ShortestPathRouter, ArrayList<Rectangle>>();
	private HashMap<Connection, ArrayList<Rectangle>> connectionToObstacle = new HashMap<Connection, ArrayList<Rectangle>>();
	private Set staleConnections = new HashSet();
	private LayoutListener listener = new LayoutTracker();

	private FigureListener figureListener = new FigureListener() {
		public void figureMoved(IFigure source) {
			Rectangle newBounds = source.getBounds().getCopy();

			ShortestPathRouter algorithm;
			algorithm = containerList.get(source.getParent());
			if(algorithm == null) {
				return;
			}
			if (algorithm.updateObstacle((Rectangle)figuresToBounds.get(source), newBounds)) {
				queueSomeRouting();
				isDirty = true;
			}
				
			figuresToBounds.put(source, newBounds);
		}
	};
	private boolean ignoreInvalidate;
	/**
	 * Creates a new shortest path router with the given container. The container
	 * contains all the figure's which will be treated as obstacles for the connections to
	 * avoid. Any time a child of the container moves, one or more connections will be
	 * revalidated to process the new obstacle locations. The connections being routed must
	 * not be contained within the container.
	 * 
	 * @param container the container
	 */
	public ShortestConnectionRouter() {
		isDirty = false;
	}
	
	public void addContainer(IFigure container) {
		ShortestPathRouter algorithm = new ShortestPathRouter();
		algorithm.setSpacing(spacing);
		containerList.put(container, algorithm);
		algorithmToObstacle.put(algorithm, new ArrayList<Rectangle>());
	}
	
	public void removeContainer(IFigure container) {
		ShortestPathRouter algorithm;
		algorithm = containerList.remove(container);
		algorithmToObstacle.remove(algorithm);
	}
	
	void addChild(IFigure child) {
		if (connectionToPaths == null)
			return;
		if (figuresToBounds.containsKey(child)){
			return;
		}
		Rectangle bounds = child.getBounds().getCopy();

		ShortestPathRouter algorithm;
		algorithm = containerList.get(child.getParent());
		if(algorithm == null) {
			return;
		}
		algorithm.addObstacle(bounds);
		figuresToBounds.put(child, bounds);
		child.addFigureListener(figureListener);

		isDirty = true;
	}
	
	private void hookAll() {
		figuresToBounds = new HashMap();
		Set<IFigure> containerSet = containerList.keySet();
		for(IFigure container : containerSet) {
			for (int i = 0; i < container.getChildren().size(); i++) {
				addChild((IFigure)container.getChildren().get(i));
			}
			container.addLayoutListener(listener);
		}
	}
	
	private void unhookAll() {
		Set<IFigure> containerSet = containerList.keySet();
		for(IFigure container : containerSet) {
			container.removeLayoutListener(listener);
			if (figuresToBounds != null) {
				Iterator figureItr = figuresToBounds.keySet().iterator();
				while (figureItr.hasNext()) {
					//Must use iterator's remove to avoid concurrent modification
					IFigure child = (IFigure)figureItr.next();
					figureItr.remove();
					removeChild(child);
				}
				figuresToBounds = null;
			}
		}
	}
	
	/**
	 * Gets the constraint for the given {@link Connection}.  The constraint is the paths
	 * list of bend points for this connection.
	 *
	 * @param connection The connection whose constraint we are retrieving
	 * @return The constraint
	 */
	public Object getConstraint(Connection connection) {
		return constraintMap.get(connection);
	}
	
	/**
	 * Returns the default spacing maintained on either side of a connection. The default
	 * value is 4.
	 * @return the connection spacing
	 * @since 3.2
	 */
	public int getSpacing() {
		return spacing;
	}
	
	/**
	 * @see ConnectionRouter#invalidate(Connection)
	 */
	public void invalidate(Connection connection) {
		if (ignoreInvalidate)
			return;
		staleConnections.add(connection);
		isDirty = true;
	}
	
	private void processLayout() {
		if (staleConnections.isEmpty())
			return;
		((Connection)staleConnections.iterator().next()).revalidate();
	}
	
	private void processStaleConnections() {
		Iterator iter = staleConnections.iterator();
		if (iter.hasNext() && connectionToPaths == null) {
			connectionToPaths = new HashMap();
			hookAll();
		}

//		Set<ShortestPathRouter> algorithmSet = obstacleSet.keySet();
//		for(ShortestPathRouter algorithm : algorithmSet) {
//			ArrayList<Rectangle> obstacleList = obstacleSet.get(algorithm);
//			for(Rectangle obstacle : obstacleList) {
//				algorithm.removeObstacle(obstacle);
//			}
//			
//			obstacleList.clear();
//		}
		while (iter.hasNext()) {
			Connection conn = (Connection)iter.next();

			IFigure sourceOwner;
			ConnectionAnchor sourceAnchor;
			ShortestPathRouter srcAlgorithm = null;

			sourceAnchor = conn.getSourceAnchor();
			sourceOwner = sourceAnchor.getOwner();
			if(sourceOwner == null){
				continue;
			}
			srcAlgorithm = containerList.get(sourceOwner.getParent());
			
			ArrayList<Rectangle> connObstacleList = connectionToObstacle.get(conn);
			if(connObstacleList == null) {
				continue;
			}
			ArrayList<Rectangle> algObstacleList = algorithmToObstacle.get(srcAlgorithm);
			for(Rectangle obstacle : connObstacleList) {
				if(algObstacleList.contains(obstacle)) {
					srcAlgorithm.removeObstacle(obstacle);
					algObstacleList.remove(obstacle);
				}
			}
			
			connObstacleList.clear();
		}

		HashMap<ShortestPathRouter, HashMap<Rectangle, Integer>>  obstacleMap = new HashMap<ShortestPathRouter, HashMap<Rectangle, Integer>>();

		iter = staleConnections.iterator();
		while (iter.hasNext()) {
			Connection conn = (Connection)iter.next();
			ConnectionAnchor sourceAnchor, targetAnchor;
			sourceAnchor = conn.getSourceAnchor();
			targetAnchor = conn.getTargetAnchor();

			IFigure sourceOwner, targetOwner;
			sourceOwner = sourceAnchor.getOwner();
			targetOwner = targetAnchor.getOwner();

			ShortestPathRouter srcAlgorithm = null, tgtAlgorithm = null;
			if(sourceOwner == null){
				continue;
			}
			srcAlgorithm = containerList.get(sourceOwner.getParent());
			if((targetOwner = targetAnchor.getOwner()) != null)
			if(sourceOwner.getParent() != targetOwner.getParent()) {
				tgtAlgorithm = containerList.get(targetOwner.getParent());
			}

			Path path = (Path)connectionToPaths.get(conn);
			if (path == null) {
				path = new Path(conn);
				connectionToPaths.put(conn, path);
				
				srcAlgorithm = containerList.get(sourceOwner.getParent());
				if(srcAlgorithm != null) {
					srcAlgorithm.addPath(path);
				}
				if(tgtAlgorithm != null) {
					tgtAlgorithm.addPath(path);
				}
			}
			
			List constraint = (List)getConstraint(conn);
			if (constraint == null) {
				constraint = Collections.EMPTY_LIST;
				this.setConstraint(conn, constraint);
			}
			
			Point start = sourceAnchor.getReferencePoint().getCopy();
			Point end = targetAnchor.getReferencePoint().getCopy();

			Set<IFigure> containerSet = containerList.keySet();
			for(IFigure container : containerSet) {
				if(sourceAnchor.getOwner().getParent() != container) {
					continue;
				}
				container.translateToRelative(start);
			}
			for(IFigure container : containerSet) {
				if(targetAnchor.getOwner() == null) {
					continue;
				}
				if(targetAnchor.getOwner().getParent() != container) {
					continue;
				}
				container.translateToRelative(end);
			}
		
			path.setStartPoint(start);
			path.setEndPoint(end);
// ----------------------------------------------------------------------
			if(path.getPoints().size() == 2 || path.getPoints().size() == 3) {
				logger.debug("추가된 소스로 고고");
				int x = Math.round((start.x + end.x)*0.5f) - 1;
				int y = Math.round((start.y + end.y)*0.5f) - 1;
				Rectangle obstacle = new Rectangle(x, y, 1, 1);

				ArrayList<Rectangle> connObstacleList = connectionToObstacle.get(conn);
				if(connObstacleList == null) {
					connObstacleList = new ArrayList<Rectangle>();
					connectionToObstacle.put(conn, connObstacleList);
				}
//				ArrayList<Rectangle> algObstacleList = algorithmToObstacle.get(srcAlgorithm);
//				
//				if(algObstacleList.contains(obstacle) == false) {
//					connObstacleList.add(obstacle);
//					srcAlgorithm.addObstacle(obstacle);
//					algObstacleList.add(obstacle);
//				}

				HashMap<Rectangle, Integer> countMap;
				countMap = obstacleMap.get(srcAlgorithm);
				if(countMap == null) {
					countMap = new HashMap<Rectangle, Integer>();
					obstacleMap.put(srcAlgorithm, countMap);
				}
				Integer count = countMap.get(obstacle);
				logger.debug(""+countMap.size()+obstacle+countMap.get(obstacle));
				if(count == null || count == 0) {
					countMap.put(obstacle, 1);
					logger.debug("countMap.put(obstacle,1)");
				}
				else {
					count += 1;
					countMap.put(obstacle, count);

					ArrayList<Rectangle> algObstacleList = algorithmToObstacle.get(srcAlgorithm);
					
					if(algObstacleList.contains(obstacle) == false) {
						connObstacleList.add(obstacle);
						srcAlgorithm.addObstacle(obstacle);
						algObstacleList.add(obstacle);
					}
				}
// ----------------------------------------------------------------------
				if (!constraint.isEmpty()) {
					PointList bends = new PointList(constraint.size());
					for (int i = 0; i < constraint.size(); i++) {
						Bendpoint bp = (Bendpoint)constraint.get(i);
						bends.addPoint(bp.getLocation());
					}
					path.setBendPoints(bends);
				} else {
					path.setBendPoints(null);
				}
	
				isDirty |= path.isDirty;
			}
		}

		staleConnections.clear();
	}
	
	void queueSomeRouting() {
		if (connectionToPaths == null || connectionToPaths.isEmpty())
			return;
		try {
			ignoreInvalidate = true;
			((Connection)connectionToPaths.keySet().iterator().next())
				.revalidate();
		} finally {
			ignoreInvalidate = false;
		}
	}
	
	/**
	 * @see ConnectionRouter#remove(Connection)
	 */
	public void remove(Connection connection) {
		logger.debug("remove");
		staleConnections.remove(connection);
		constraintMap.remove(connection);
		if (connectionToPaths == null)
			return;
		Path path = (Path)connectionToPaths.remove(connection);
		
		ArrayList<Rectangle> connObstacleList = connectionToObstacle.remove(connection);
		if(connObstacleList != null) {
			ConnectionAnchor sourceAnchor, targetAnchor;
			sourceAnchor = connection.getSourceAnchor();
			targetAnchor = connection.getTargetAnchor();

			IFigure sourceOwner, targetOwner;
			sourceOwner = sourceAnchor.getOwner();
			targetOwner = targetAnchor.getOwner();

			ShortestPathRouter srcAlgorithm = null, tgtAlgorithm = null;
			srcAlgorithm = containerList.get(sourceOwner.getParent());

			for(Rectangle obstacle : connObstacleList) {
				srcAlgorithm.removeObstacle(obstacle);
			}
			connObstacleList.clear();
		}

		Collection<ShortestPathRouter> algorithms = containerList.values();
		for(ShortestPathRouter algorithm : algorithms) {
			algorithm.removePath(path);
		}
		isDirty = true;
		if (connectionToPaths.isEmpty()) {
			unhookAll();
			connectionToPaths = null;
		} else {
			//Make sure one of the remaining is revalidated so that we can re-route again.
			queueSomeRouting();
		}
	}
	
	void removeChild(IFigure child) {
		if (connectionToPaths == null)
			return;
		Rectangle bounds = child.getBounds().getCopy();
		
		ShortestPathRouter algorithm;
		algorithm = containerList.get(child.getParent());
		if(algorithm == null) {
			return;
		}
		boolean change = algorithm.removeObstacle(bounds);
		figuresToBounds.remove(child);
		child.removeFigureListener(figureListener);
		if (change) {
			isDirty = true;
			queueSomeRouting();
		}
	}
	
	/**
	 * @see ConnectionRouter#route(Connection)
	 */
	public void route(Connection conn) {
		logger.debug("route");
		if (isDirty) {
			ignoreInvalidate = true;
			processStaleConnections();

			isDirty = false;

			Collection<ShortestPathRouter> algorithms = containerList.values();
			for(ShortestPathRouter algorithm : algorithms) {
				List updated = algorithm.solve();

				Connection current;
				
				if(updated.size() > 0) {
					for (int i = 0; i < updated.size(); i++) {
						Path path = (Path) updated.get(i);
						current = (Connection)path.data;
						current.revalidate();
						
						PointList points = path.getPoints().getCopy();
						Point ref1, ref2, start, end;
						ref1 = new PrecisionPoint(points.getPoint(1));
						ref2 = new PrecisionPoint(points.getPoint(points.size() - 2));
						current.translateToAbsolute(ref1);
						current.translateToAbsolute(ref2);
						
						start = current.getSourceAnchor().getLocation(ref1).getCopy();
						end = current.getTargetAnchor().getLocation(ref2).getCopy();
						
						// KJH 20110329 s,
						double d = start.x - end.x;
						if (((start.x - ref1.x) / d) < 0)
							start = current.getSourceAnchor().getLocation(end).getCopy();
						d = end.x - start.x;
						if (((end.x - ref2.x) / d) < 0)
							end = current.getTargetAnchor().getLocation(start).getCopy();
						// KJH 20110329 e,
						
						current.translateToRelative(start);
						current.translateToRelative(end);

						points.setPoint(start, 0);
						points.setPoint(end, points.size() - 1);
			
						current.setPoints(points);
					}
				}
			}

			ignoreInvalidate = false;
		}
	}
	
	/**
	 * @see ConnectionRouter#setConstraint(Connection, Object)
	 */
	public void setConstraint(Connection connection, Object constraint) {
		//Connection.setConstraint() already calls revalidate, so we know that a
		// route() call will follow.
		staleConnections.add(connection);
		constraintMap.put(connection, constraint);
		isDirty = true;
	}
	
	/**
	 * Sets the default space that should be maintained on either side of a connection. This
	 * causes the connections to be separated from each other and from the obstacles. The
	 * default value is 4.
	 * 
	 * @param spacing the connection spacing
	 * @since 3.2
	 */
	public void setSpacing(int spacing) {

		this.spacing = spacing;
		Collection<ShortestPathRouter> algorithms = containerList.values();
		for(ShortestPathRouter algorithm : algorithms) {
			algorithm.setSpacing(spacing);
		}
	}

}
