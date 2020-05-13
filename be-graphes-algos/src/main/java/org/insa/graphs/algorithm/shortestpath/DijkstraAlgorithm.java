package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Arc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.AbstractSolution.Status;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes();
        
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        Label[] labels = new Label[graph.size()];
        
        Node origine = data.getOrigin();
        
        for (Node node : nodes ) {
        		labels[node.getId()] = new Label(node,false,Float.POSITIVE_INFINITY,null);
        }
        
        notifyOriginProcessed(origine);
        labels[origine.getId()].setCost(0);
        heap.insert(labels[origine.getId()]);
        
        while(!heap.isEmpty() && labels[data.getDestination().getId()].isMarque()==false) {
        	Label label_courrant = heap.deleteMin();
        	label_courrant.marquer();
        	Node noeud_courant = label_courrant.getNode();
        	notifyNodeMarked(noeud_courant);
        	
        	//int nbsuccesseur=0;
        	
        	for (Arc arc : noeud_courant.getSuccessors()) {
        		Node noeud_destination = arc.getDestination();
        		Label label_destination = labels[noeud_destination.getId()];
        		
        		double cout = data.getCost(arc) + label_courrant.getCost();
        		//nbsuccesseur++;
                
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		
        		if (!label_destination.isMarque() && label_destination.getCost()>cout )
        		{
        			
        			if (label_destination.getPere()==null) { 
	        			label_destination.setCost(cout);
	            		label_destination.setPere(arc);
	            		heap.insert(label_destination);
	            		notifyNodeReached(noeud_destination);
	            		//System.out.println("Cout: "+label_destination.getCost());
	  
	        		}	
        			else {
        				heap.remove(label_destination);
	        			label_destination.setCost(cout);
	            		label_destination.setPere(arc);
	            		heap.insert(label_destination);
	            		notifyNodeReached(noeud_destination);
	            		//System.out.println("Cout: "+label_destination.getCost());
        			}
        		}
        		
  
        	}// end for
        	//System.out.println("Successeur: "+nbsuccesseur);
        			
        }// end while
        
        Node noeud_desti = data.getDestination();
        Label label_desti = labels[noeud_desti.getId()];
        
        // Destination has no predecessor, the solution is infeasible...
        if (label_desti.getPere()== null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
       
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(noeud_desti);
            
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = label_desti.getPere();
            
            while (arc != null) {
                arcs.add(arc);
                label_desti = labels[arc.getOrigin().getId()];
                arc = label_desti.getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);
            
            Path path= new Path(graph, arcs);
            // Create the final solution.
            if(path.isValid()==true) {
            	solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
            	System.out.println("Chemin valide");
            }
            else {
            	System.out.println("Chemin non valide");
            	solution=null;
            }
            

            System.out.println("Path : "+path.getLength()+" SPS : "+labels[data.getDestination().getId()].getCost());

   
            
        }
        
        
        return solution;
    }
}
