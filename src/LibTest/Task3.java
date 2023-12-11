package LibTest;

import LibNet.NetLibrary;
import PetriObj.*;

import java.util.ArrayList;

public class Task3 {
    private static int time = 1000;
    
    public static void main(String[] args) throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        double timeInQueue = 600;
        ArrayList<PetriP> queue1, queue2, busA, busB;

        for(int i = 1; i<=25;i++) {
            ArrayList<PetriSim> list = new ArrayList<>();
        
            list.add(new PetriSim(PetriNet.CreateBusStop()));
            list.add(new PetriSim(PetriNet.CreateBusStop()));
            list.add(new PetriSim(PetriNet.CreateBusA(i)));
            list.add(new PetriSim(PetriNet.CreateBusB(i)));

            list.get(2).getNet().getListP()[0] = list.get(0).getNet().getListP()[2];
            list.get(3).getNet().getListP()[0] = list.get(0).getNet().getListP()[2];
            list.get(2).getNet().getListP()[8] = list.get(1).getNet().getListP()[2];
            list.get(3).getNet().getListP()[8] = list.get(1).getNet().getListP()[2];

            PetriObjModel model = new PetriObjModel(list);
            model.setIsProtokol(false);
            model.go(time);
            
            queue1 = model.getListObj().get(0).getListPositionsForStatistica();
            queue2 = model.getListObj().get(1).getListPositionsForStatistica();
            busA = model.getListObj().get(2).getListPositionsForStatistica();
            busB = model.getListObj().get(3).getListPositionsForStatistica();
            
            int lost = queue1.get(3).getMark() + queue2.get(3).getMark();
            int totalProduced = queue1.get(5).getMark() + queue2.get(5).getMark();
            double avgQueue = (queue1.get(2).getMean() + queue2.get(2).getMean()) / 2;
            double totalQueueProcessed = (queue1.get(4).getMark() + queue2.get(4).getMark()) / 2;

            double queueRate = totalQueueProcessed / time;
            
            timeInQueue = avgQueue / queueRate;
            
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Seats: " + i);
            System.out.println("Mean queue time " + timeInQueue);
            System.out.println("Lost " + lost);
            
            int totalMoney = busA.get(9).getMark() + busB.get(9).getMark();
            System.out.println("Total " + totalMoney);
        }
    }
}
