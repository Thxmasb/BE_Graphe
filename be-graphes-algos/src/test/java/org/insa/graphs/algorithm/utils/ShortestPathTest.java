package org.insa.graphs.algorithm.utils;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.*;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class ShortestPathTest {

    protected ShortestPathData dataShortest[] = new ShortestPathData[3];
	protected ShortestPathData dataFastest[] = new ShortestPathData[3];
 
	@Before
    public void Construction() throws Exception{
    	final Graph graphHG, graphB;
    
		final String graphHauteGaronne = "/Users/thomasballotin/Desktop/INSA/BE_Graphe/europe/france/haute-garonne.mapgr";
	    final String graphBretagne= "/Users/thomasballotin/Desktop/INSA/BE_Graphe/europe/france/bretagne.mapgr";
		
	    try(final GraphReader readerHG = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphHauteGaronne))))){
	    	graphHG = readerHG.read();
	    }
	    try(final GraphReader readerB = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphBretagne))))){
	    	graphB = readerB.read();
	    }
	    
	    final ArcInspector allRoadsLength=ArcInspectorFactory.getAllFilters().get(0);
	    final ArcInspector carsAndLength=ArcInspectorFactory.getAllFilters().get(1);
	    final ArcInspector carsAndTime=ArcInspectorFactory.getAllFilters().get(2);

	    
	    dataShortest[0] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(10991), allRoadsLength); //dans 1 noeud
	    dataShortest[1] = new ShortestPathData(graphB, graphB.get(417195), graphB.get(116100), allRoadsLength); //trajet entre 2 iles
	    dataShortest[2] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(63104), carsAndLength); //bikini canal en voiture et en longueur
	    
	    dataFastest[0] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(10991), carsAndTime); //dans 1 noeud
	    dataFastest[1] = new ShortestPathData(graphHG, graphHG.get(10991), graphHG.get(63104), carsAndTime); //bikini canal en voiture et en temps
	    dataFastest[2] = new ShortestPathData(graphB, graphB.get(417195), graphB.get(116100), carsAndTime); //trajet entre 2 iles

    }
    
    @Test
    public void testcompareDjBF() {
    	//******************************
        //Chemin Bikini INSA en longueur
    	//******************************
        DijkstraAlgorithm DijkstraBI = new DijkstraAlgorithm(dataShortest[2]);
        ShortestPathSolution SolutionD = DijkstraBI.run();
        
        BellmanFordAlgorithm BFBI = new BellmanFordAlgorithm(dataShortest[2]);
        ShortestPathSolution SolutionBF = BFBI.run();
        
        //Comparaison des coûts des solutions
        assertTrue(SolutionD.getPath().isValid());
        assertTrue(SolutionBF.getPath().isValid());
        assertEquals(SolutionD.getPath().getLength(),SolutionBF.getPath().getLength(),0.00001);
        
        //***************************
        //Chemin Bikini INSA en temps
        //***************************
        DijkstraBI = new DijkstraAlgorithm(dataFastest[1]);
        SolutionD = DijkstraBI.run();
        
        BFBI = new BellmanFordAlgorithm(dataFastest[1]);
        SolutionBF = BFBI.run();
        
        //Comparaison des coûts des solutions
        assertTrue(SolutionD.getPath().isValid());
        assertTrue(SolutionBF.getPath().isValid());
        assertEquals(SolutionD.getPath().getMinimumTravelTime(),SolutionBF.getPath().getMinimumTravelTime(),0.00001);
        
        //**************
        //Chemin 1 noeud
        //**************
        DijkstraBI = new DijkstraAlgorithm(dataShortest[0]);
        SolutionD = DijkstraBI.run();
        
        BFBI = new BellmanFordAlgorithm(dataShortest[0]);
        SolutionBF = BFBI.run();
        
        //Comparaison des coûts des solutions
        assertEquals(SolutionD.getPath(),null);
		assertEquals(SolutionD.getStatus(),Status.INFEASIBLE);
		assertEquals(SolutionBF.getPath(),null);
		assertEquals(SolutionBF.getStatus(),Status.INFEASIBLE);
		
        //*****************
        //Chemin inexistant
        //*****************
        DijkstraBI = new DijkstraAlgorithm(dataShortest[1]);
        SolutionD = DijkstraBI.run();
        
        BFBI = new BellmanFordAlgorithm(dataShortest[1]);
        SolutionBF = BFBI.run();
        
        //Comparaison des coûts des solutions
        assertEquals(SolutionD.getPath(),null);
		assertEquals(SolutionD.getStatus(),Status.INFEASIBLE);
		assertEquals(SolutionBF.getPath(),null);
		assertEquals(SolutionBF.getStatus(),Status.INFEASIBLE);
    }
    
    @Test
    public void testcompareAsBF() {
    	//******************************
        //Chemin Bikini INSA en longueur
    	//******************************
        AStarAlgorithm AsBI = new AStarAlgorithm(dataShortest[2]);
        ShortestPathSolution SolutionAs = AsBI.run();
        
        BellmanFordAlgorithm BFBI = new BellmanFordAlgorithm(dataShortest[2]);
        ShortestPathSolution SolutionBF = BFBI.run();
        
        //Comparaison des coûts des solutions
        assertTrue(SolutionAs.getPath().isValid());
        assertTrue(SolutionBF.getPath().isValid());
        assertEquals(SolutionAs.getPath().getLength(),SolutionBF.getPath().getLength(),0.00001);
        

    }

}
