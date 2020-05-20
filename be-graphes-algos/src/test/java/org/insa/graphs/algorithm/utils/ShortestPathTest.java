package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.utils.PriorityQueueTest.MutableInteger;
import org.junit.Assume;
import org.junit.Test;


import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.io.*;
import org.insa.graphs.algorithm.*;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.*;

import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.*;
import org.junit.Test;


public class ShortestPathTest {
	
	@Test
    public void Test() throws FileNotFoundException {
		final String graphHauteGaronne = "/Users/thomasballotin/Desktop/INSA/BE_Graphe/europe/france/haute-garonne.mapgr";
	    final String graphBretagne= "/Users/thomasballotin/Desktop/INSA/BE_Graphe/europe/france/bretagne.mapgr";
		
	    final GraphReader readerHG = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphHauteGaronne))));
	    final GraphReader readerB = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphBretagne))));
	    
	    final Graph graphHG = readerHG.read();
	    final Graph graphB = readerB.read();

	    final ArcInspector allRoads=ArcInspectorFactory.getAllFilters().get(0);
	    final ArcInspector carsAndLength=ArcInspectorFactory.getAllFilters().get(1);
	    final ArcInspector carsAndTime=ArcInspectorFactory.getAllFilters().get(2);
	    final ArcInspector pedestrian=ArcInspectorFactory.getAllFilters().get(4);
	    
	    ShortestPathData data[] = new ShortestPathData[5];

	    
	    data[0] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(10991), allRoads); //dans 1 noeud
	    data[1] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(63104), carsAndTime); //bikini canal en voiture et en temps
	    data[2] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(63104), carsAndLength); //bikini canal en voiture et en longueur
	    data[3] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(63104), pedestrian); //... à pied
	    data[4] = new ShortestPathData(graphB, graphB.get(417195), graphB.get(116100), allRoads); //trajet entre 2 iles
	    
	    
	    BellmanFordAlgorithm bellman[]= new BellmanFordAlgorithm[5];
	    DijkstraAlgorithm dijkstra[]= new DijkstraAlgorithm[5];
	    AStarAlgorithm astar[]= new AStarAlgorithm[5];
	    
	    for(int i=0; i<=4;i++) {
	    	bellman[i] = new BellmanFordAlgorithm(data[i]);
	    	dijkstra[i] = new DijkstraAlgorithm(data[i]);
	    	astar[i] = new AStarAlgorithm(data[i]);
	    }
	    
	    assertEquals 
	}

}
