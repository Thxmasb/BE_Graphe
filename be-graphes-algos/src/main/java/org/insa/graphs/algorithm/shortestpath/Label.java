package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

import java.util.ArrayList;

public class Label {
	
	private Node sommet_courant;
	private boolean marque;
	private float cout;
	private Arc pere;
	
    public float getCost() {
        return this.cout;
    }
	
	public Label(Node sommet_courant,boolean marque,float cout,Arc pere) {
		this.sommet_courant=sommet_courant;
		this.marque=marque;
		this.cout=cout;
		this.pere=pere;
	}
	
	
}