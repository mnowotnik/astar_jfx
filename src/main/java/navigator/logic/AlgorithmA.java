package navigator.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmA<V extends Vertex<V, E>, E extends Edge<V, E>>
		implements Algorithm<V, E> {

	@Override
	public List<V> compute(V vertexStart, V vertexEnd,Estimator<V,E>estimator) {
		
		
		Map<V,Double> predictedDistMap = new HashMap<>();
	
		List<V> closedSet = new ArrayList<>();
		List<V> openSet = new ArrayList<>();
		
		Map<V,V> toFromMap=new HashMap<>();
	    
		Map<V,Double>optimalDistanceMap=new HashMap<>();

		predictedDistMap.put(vertexStart,estimator.heuristicFunction(vertexStart, vertexEnd));
		optimalDistanceMap.put(vertexStart,0.0d);
		
		openSet.add(vertexStart);
		while(!openSet.isEmpty())
		{
			V current = getLowestKey(predictedDistMap,openSet); //pobieramy pierwszy element posortowanej listy (najmniejszy)
			if (current == vertexEnd)
				{return this.reconstructPath(vertexEnd,toFromMap);}
			
			//dodajemy wierzcholek do przejrzanych i usuwamy go z listy wierzhcolkow nieodwiedzonych
			openSet.remove(current);
			closedSet.add(current);
			
			//deklaracja sasiadow danego wezla
			List<E> c = current.getEdges();					// tworzymy liste krawedzi
			List<V> neighborVertexList = new ArrayList<V>();	// tworzymy list� sasiadow za pomoca listy krawedzi
			for(int i = 0; i < c.size(); i++)
				neighborVertexList.add(c.get(i).getOther(current));
			
			if(c.isEmpty()){
				throw new RuntimeException("Lista krawedzi sasiadujacych jest pusta!");
			}
			for(int i = 0; i < c.size(); i++)
			{
				E edge=current.getEdges().get(i);
				V neighborVertex=neighborVertexList.get(i);
				
				//jesli twoj sasiad to wierzcholek koncowy, to nie szukaj dalej
				if(neighborVertex== vertexEnd)
				{
					toFromMap.put(vertexEnd,current);
					return this.reconstructPath(vertexEnd,toFromMap);
				}

				//jesli sasiad zostal juz przejrzany, przechodzimy do kolejnego sasiada
				if (closedSet.contains(neighborVertex))
				{	continue;}
				
				double tentativeOptDist=optimalDistanceMap.get(current)+edge.getWeight();
				boolean tentativeBetter=false;
				if(!openSet.contains(neighborVertex))
				{
					openSet.add(neighborVertex);
					tentativeBetter=true;
				}
				//jesli dystans do sasiada + heurystyka mniejsze niz najmniejsza 
				//jaka znalezlismy, wtedy przypisz jako najmniejsza i zapamietaj wierzcholek
				else{
					double neighborScore=optimalDistanceMap.get(neighborVertex);
					if (tentativeOptDist < neighborScore)
					{
						tentativeBetter=true;
					}
				}
				if(tentativeBetter){
					double heuristicDist=estimator.heuristicFunction(neighborVertex,vertexEnd);
					optimalDistanceMap.put(neighborVertex,tentativeOptDist);
					predictedDistMap.put(neighborVertex, tentativeOptDist+heuristicDist);
					toFromMap.put(neighborVertex,current);
					
				}

			}

			
			
		}
		return this.reconstructPath(vertexEnd,toFromMap);
	}	private List<V> reconstructPath(V to, Map<V, V> toFromMap) {
		List<V> reconstructedPath = new ArrayList<>();
		reconstructedPath.add(to);

		V currentNode = null;
		while (true) {
			currentNode = toFromMap.get(to);
			if (currentNode == null) {
				break;
			}
			reconstructedPath.add(currentNode);
			to = currentNode;
		}

		Collections.reverse(reconstructedPath);
		return reconstructedPath;
	}
	private V getLowestKey(Map<V,Double>predictedDistMap,List<V>openSet){
		V lowestV=openSet.get(0);
		if(openSet.size()==1){return lowestV;}
		for(V v  : openSet){
			double currentDist=predictedDistMap.get(lowestV);
			double nextDist=predictedDistMap.get(v);
			if(nextDist<currentDist){
				lowestV=v;
			}
		}
		return lowestV;

	}
}


/*
 * //#todo public List<V> reconstructPath(V cameFrom, V currentNode) { if
 * (cameFrom[current_node] is set) { p =
 * reconstruct_path(came_from,came_from[current_node]); return (p +
 * current_node); } return null; } }
 */
