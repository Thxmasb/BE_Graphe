package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

import java.util.ArrayList;

public class Label implements Comparable<Label>{
	
	private Node sommet_courant;
	private boolean marque;
	private double cout;
	private Arc pere;
	
    public double getCost() {
        return this.cout;
    }
    

	public boolean isMarque() {
		return this.marque;
	}


	public Node getNode() {
		return this.sommet_courant;
	}


	public Label(Node sommet_courant,boolean marque,double cout,Arc pere) {
		this.sommet_courant=sommet_courant;
		this.marque=marque;
		this.cout=cout;
		this.pere=pere;
	}
	
	public void setCost(double cout) {
		this.cout = cout;
	}


	public void setPere(Arc pere) {
		this.pere = pere;
	}


	public Arc getPere() {
		return pere;
	}


	public void marquer() {
        this.marque = true;
    }
	
    public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
    }
	
}