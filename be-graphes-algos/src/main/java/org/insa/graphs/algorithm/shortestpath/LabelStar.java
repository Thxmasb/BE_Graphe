package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
	private double cout_estime;
	private Node destination;
	
	
	public LabelStar(Node sommet_courant,boolean marque,double cout,Arc pere,Node destination) {
		super(sommet_courant,marque,cout,pere);
		this.cout_estime = sommet_courant.getPoint().distanceTo(destination.getPoint());
		
	}
	
    public double getTotalCost() {
        return this.cout_estime+this.getCost();
    }
    
}
