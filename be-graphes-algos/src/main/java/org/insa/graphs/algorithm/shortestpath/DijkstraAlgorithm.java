package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import java.util.ArrayList;
import java.util.List;
import org.insa.graphs.algorithm.ArcInspector;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        
        List<Node> nodes = graph.getNodes();
        
        Label[] labels = new Label[graph.size()];
        Node origine = data.getOrigin();
        
        for(Node node: nodes) {
        	 if(node == origine) {
        		 labels[node.getId()] = new Label(node,false,0,null);
        	 }
        	 labels[node.getId()] = new Label(node,false,Float.POSITIVE_INFINITY,null);
        }
        
        
        BinaryHeap<Label> tas_label = new BinaryHeap<Label>();
        
        tas_label.insert(labels[origine.getId()]);
        
        while(tas_label.isEmpty()==false)
        {
        	Label label_courrant = tas_label.deleteMin();
            label_courrant.marquer();
            Node noeud_courant = label_courrant.getNode();
            
            for (Arc arc : noeud_courant.getSuccessors()) {
                Node noeud_destination = arc.getDestination();
                Label label_destination = labels[noeud_destination.getId()];
                if (label_destination.isMarque()==false) {
                	label_destination.setCost(label_courrant.getCost()+data.getCost(arc));
                	label_destination.setPere(arc);
                	tas_label.insert(label_destination);
                }
                
                
            }
            
        	
        }
       
       
          
        
        return solution;
    }

}
