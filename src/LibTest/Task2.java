package LibTest;

import LibNet.NetLibrary;
import PetriObj.*;

import java.util.ArrayList;

public class Task2 {
        private static int time = 1000;
    
    public static void main(String[] args) throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        double time = 1000;
        double totalProcessed1 = 0;
        ArrayList<PetriP> pModel1;
        
        for(int i = 1;i<=10;i++){
            PetriObjModel model1 = getModel1();
            model1.setIsProtokol(false);
            model1.go(time);
            
            pModel1 = model1.getListObj().get(3).getListPositionsForStatistica();
            
            totalProcessed1 += pModel1.get(1).getMark();
        }
        
        System.out.println(totalProcessed1 / 10);
        
        ArrayList<PetriP> pModel2;
        double totalProcessed2 = 0;
        
        for(int i = 1;i<=10;i++){
            PetriObjModel model2 = getModel2();
            model2.setIsProtokol(false);
            model2.go(time);
            
            pModel2 = model2.getListObj().get(3).getListPositionsForStatistica();
            totalProcessed2 += pModel2.get(1).getMark();
        }
        
        System.out.println(totalProcessed2 / 10);
    }
    public static PetriObjModel getModel1() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure{
        ArrayList<PetriSim> list = new ArrayList<>();
        
        list.add(new PetriSim(PetriNet.CreateTaskGenerator2()));
        list.add(new PetriSim(PetriNet.CreateProcessor1()));
        list.add(new PetriSim(PetriNet.CreateProcessor2()));
        list.add(new PetriSim(PetriNet.CreateStorage()));
        
        list.get(0).getNet().getListP()[1] = list.get(1).getNet().getListP()[0];
        list.get(1).getNet().getListP()[3] = list.get(2).getNet().getListP()[0];
        list.get(2).getNet().getListP()[3] = list.get(3).getNet().getListP()[0];

        return new PetriObjModel(list);
    }
    public static PetriObjModel getModel2() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure{
        ArrayList<PetriSim> list = new ArrayList<>();
        
        list.add(new PetriSim(PetriNet.CreateTaskGenerator2()));
        list.add(new PetriSim(PetriNet.CreateProcessor21()));
        list.add(new PetriSim(PetriNet.CreateProcessor22()));
        list.add(new PetriSim(PetriNet.CreateStorage2()));
        
        list.get(0).getNet().getListP()[1] = list.get(1).getNet().getListP()[0];
        list.get(1).getNet().getListP()[3] = list.get(2).getNet().getListP()[0];
        list.get(2).getNet().getListP()[3] = list.get(3).getNet().getListP()[0];

        return new PetriObjModel(list);
    }
}