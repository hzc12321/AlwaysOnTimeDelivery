package Algorithm;

import AlwaysOnTime.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 *
 * @author @author Hong Zhao Cheng Chiew Zhe Wei Yau De Min Wong Yu Xuan
 */
public class Greedy extends Graph {

    public Greedy(String name) throws FileNotFoundException {
        super(name);
    }

    public void GreedySimulation() {
        long start = System.currentTimeMillis();
        LinkedList<Vehicle> Vehicles_List = new LinkedList<>();
        LinkedList<Node> Remaining_Nodes = new LinkedList<>();
        Node temp = head.nextVertex; //dont need to add warehouse (head)

        while (temp != null) {
            //list of all customer to be visited which will gradually be removed 
            Remaining_Nodes.add(temp);
            temp = temp.nextVertex;
        }

        while (!Remaining_Nodes.isEmpty()) {
            int current = 0; //store the current node/customer / initially is warehouse
            Vehicle v = new Vehicle();
            v.addNode(head); //start from warehouse
            Node[] choice;
            //all the available customer to evaluate on that particular node with exception of warehouse and visited customer

            while (true) {
                if (Remaining_Nodes.isEmpty()) {
                    break;  //no more customers 
                }
                choice = Sorted(getNode(current), Remaining_Nodes.toArray());
                //sort all the avaliable destination in ascending order of cost path 
                //Evaluate all potential customer sorted by distance and add
                boolean added = false;
                for (int i = 0; i < choice.length; i++) {
                    if (!v.TestNode(choice[i])) {
                        continue;
                    }
                    v.addNode(choice[i]);              //add customer to vehicle path 
                    Remaining_Nodes.remove(choice[i]); //this customer is serviced
                    current = choice[i].getId();
                    added = true; //added a customer
                    break;
                } //break for loop go next to newly added customer
                if (!added) {
                    break;
                }
            } //break while loop because nothing is added to current path anymore

            v.addNode(head); // back to warehouse
            Vehicles_List.add(v);
        } //next vehicle end while

        total_cost_path = CalculateTourCost(Vehicles_List);
        System.out.println("No limited time is set since the simulation time is too fast");
        System.out.println("");
        System.out.println("Greedy simulation");
        BasicPrint(Vehicles_List);
        long end = System.currentTimeMillis();
        long timeElapsed = (end - start) / 1000;
        System.out.println("");
        System.out.println("Time Elapsed : " + timeElapsed + " s");
        System.out.println("");
    }

}
