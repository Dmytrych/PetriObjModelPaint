package LibTest;

import LibNet.NetLibrary;
import PetriObj.*;

import java.util.ArrayList;

public class Task1 {
    private static int time = 1000;
    private static String modelName = "Name";
    
    public static void main(String[] args)
            throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {

        ArrayList<PetriSim> petriObjects = new ArrayList<PetriSim>();
        PetriNet net = NetLibrary.CreateNetSMOgroup(7, 1, 200, modelName);
        petriObjects.add(new PetriSim(net));
        PetriObjModel model = new PetriObjModel(petriObjects);
        
        model.setIsProtokol(false);
        net.getListP()[0].setMark(1000);
        model.go(time);

        PetriP[] listP = net.getListP();
        PetriT[] listT = net.getListT();

        System.out.println("P List: ");
        for (PetriObj.PetriP petriP : listP) {
            System.out.println(petriP.getName() + ": " + petriP.getMark());
        }
        System.out.println("\n T List: ");
        for (PetriObj.PetriT petriT : listT) {
            System.out.println(petriT.getName() + ": " + petriT.getBuffer());
        }
    }
}
