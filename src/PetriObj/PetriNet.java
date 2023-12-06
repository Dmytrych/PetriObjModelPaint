package PetriObj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class provides constructing Petri net
 *
 * @author Inna V. Stetsenko
 */
public class PetriNet implements Cloneable, Serializable {

    /**
     * @return the ListIn
     */
    public ArcIn[] getListIn() {
        return ListIn;
    }

    /**
     * @return the ListOut
     */
    public ArcOut[] getListOut() {
        return ListOut;
    }

    private String name;
    private int numP;
    private int numT;
    private int numIn;
    private int numOut;
    private PetriP[] ListP = new PetriP[numP];
    private PetriT[] ListT = new PetriT[numT];
    private ArcIn[] ListIn = new ArcIn[numIn];
    private ArcOut[] ListOut = new ArcOut[numOut];

    /**
     * Construct Petri net for given set of places, set of transitions, set of
     * arcs and the name of Petri net
     *
     * @param s name of Petri net
     * @param pp set of places
     * @param TT set of transitions
     * @param In set of arcs directed from place to transition
     * @param Out set of arcs directed from transition to place
     */
    public PetriNet(String s, PetriP[] pp, PetriT TT[], ArcIn[] In, ArcOut[] Out) {
        name = s;
        numP = pp.length;
        numT = TT.length;
        numIn = In.length;
        numOut = Out.length;
        ListP = pp;
        ListT = TT;
        ListIn = In;
        ListOut = Out;

        for (PetriT transition : ListT) {
            try {
                transition.createInP(ListIn);
                transition.createOutP(ListOut);
                if (transition.getInP().isEmpty()) {
                    throw new ExceptionInvalidTimeDelay("Error: Transition " + transition.getName() + " has empty list of input places "); //генерувати виключення???
                }
                if (transition.getOutP().isEmpty()) {
                    throw new ExceptionInvalidTimeDelay("Error: Transition " + transition.getName() + " has empty list of input places"); //генерувати виключення???
                }
            } catch (ExceptionInvalidTimeDelay ex) {
                Logger.getLogger(PetriNet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * @param s name of Petri net
     * @param pp set of places
     * @param TT set of transitions
     * @param In set of arcs directed from place to transition
     * @param Out set of arcs directed from transition to place
     * @throws PetriObj.ExceptionInvalidTimeDelay if Petri net has invalid structure
     */
    public PetriNet(String s, ArrayList<PetriP> pp, ArrayList<PetriT> TT, ArrayList<ArcIn> In, ArrayList<ArcOut> Out) throws ExceptionInvalidTimeDelay //додано 16 серпня 2011
    {//Працює прекрасно, якщо номера у списку співпадають із номерами, що присвоюються, і з номерами, які використовувались при створенні зв"язків!!!
        name = s;
        numP = pp.size();
        numT = TT.size();
        numIn = In.size();
        numOut = Out.size();
        ListP = new PetriP[numP];
        ListT = new PetriT[numT];
        ListIn = new ArcIn[numIn];
        ListOut = new ArcOut[numOut];

        for (int j = 0; j < numP; j++) {
            ListP[j] = pp.get(j);
        }

        for (int j = 0; j < numT; j++) {
            ListT[j] = TT.get(j);
        }

        for (int j = 0; j < numIn; j++) {
            ListIn[j] = In.get(j);
        }
        for (int j = 0; j < numOut; j++) {
            ListOut[j] = Out.get(j);
        }

        for (PetriT transition : ListT) {
            transition.createInP( ListIn);
            transition.createOutP( ListOut);
        }

    }

    /**
     *
     * @return name of Petri net
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of Petri net
     *
     * @param s the name of Petri net
     */
    public void setName(String s) {
        name = s;
    }

    /**
     *
     * @return array of Petri net places
     */
    public PetriP[] getListP() {
        return ListP;
    }

    /**
     *
     * @return array of Petri net transitions
     */
    public PetriT[] getListT() {
        return ListT;
    }

    /**
     *
     * @return array of Petri net input arcs
     */
    public ArcIn[] getArcIn() {
        return getListIn();
    }

    /**
     *
     * @return array of Petri net output arcs
     */
    public ArcOut[] getArcOut() {
        return getListOut();
    }

    /**
     * Finds the place of Petri net with given name
     *
     * @param s name of place
     * @return number of place with given name
     */
    public int strToNumP(String s) {
        
        int a = -1;
        for (PetriP e : ListP) {
            if (s.equalsIgnoreCase(e.getName())) {
                a = e.getNumber();
                
            }
        }
        return a;
    }

    /**
     * Determines the quantity of markers in place with given name
     *
     * @param s name of place
     * @return quantity of markers in place with given name
     */
    public int getCurrentMark(String s) {
        int a = ListP[PetriNet.this.strToNumP(s)].getMark();
        return a;
    }

    /**
     * Determines the mean value of markers in place with given name
     *
     * @param s name of place
     * @return the mean value of quantity of markers in place with given name
     */
    public double getMeanMark(String s) {
        double a = ListP[PetriNet.this.strToNumP(s)].getMean();
        return a;
    }

    /**
     * Determines quantity of active channels of transition with given name
     *
     * @param s name of transition
     * @return quantity of active channels of transition
     */
    public int getCurrentBuffer(String s) {
        int a = ListT[strToNumT(s)].getBuffer();
        return a;
    }

    /**
     * Determines mean value of active channels of transition with given name
     *
     * @param s name of transition
     * @return the mean value of quantity of active channels of transition
     */
    public double getMeanBuffer(String s) {
        double a = ListT[strToNumT(s)].getMean();
        return a;
    }

    /**
     * Finds the place of Petri net with given name and given set of places
     *
     * @param s name of place
     * @param pp array of places
     * @return the number of place
     */
    public int strToNumP(String s, PetriP[] pp) {
   
        int a = -1;
        for (PetriP e : pp) {
            if (s.equalsIgnoreCase(e.getName())) {
                a = e.getNumber();
                
            }
        }
        return a;
    }

    /**
     * Finds the transition of Petri net with given name
     *
     * @param s name of transition
     * @return the number of transition of Petri net with given name
     */
    public int strToNumT(String s) {
        
        int a = -1;
        for (PetriT e : ListT) {
            if (s.equalsIgnoreCase(e.getName())) {
                a = e.getNumber();
                
            }
        }
        return a;
    }

    /**
     *
     */
    public void printArcs() //додано 1.10.2012
    {
        System.out.println("Petri net " + name + " arcs: " + getListIn().length + " input arcs snd " + getListOut().length + " output arcs");

        for (ArcIn arcs : getListIn()) {
            arcs.print();
        }
        for (ArcOut arcs : getListOut()) {
            arcs.print();
        }
    }

    /**
     *
     */
    public void printMark() {
        System.out.print("Mark in Net  " + this.getName() + "   ");
        for (PetriP position: ListP) {
            System.out.print(position.getMark() + "  ");
        }
        System.out.println();
    }
    public void printBuffer() {
        System.out.print("Buffer in Net  " + this.getName() + "   ");
        for (PetriT transition: ListT) {
            System.out.print(transition.getBuffer() + "  ");
        }
        System.out.println();
    }
    
    @Override
    public PetriNet clone() throws CloneNotSupportedException //14.11.2012
    {
        super.clone();
        PetriP[] copyListP = new PetriP[numP];
        PetriT[] copyListT = new PetriT[numT];
        ArcIn[] copyListIn = new ArcIn[numIn];
        ArcOut[] copyListOut = new ArcOut[numOut];
        for (int j = 0; j < numP; j++) {
            copyListP[j] = ListP[j].clone();
        }
        for (int j = 0; j < numT; j++) {
            copyListT[j] = ListT[j].clone();
        }
        for (int j = 0; j < numIn; j++) {
            copyListIn[j] = getListIn()[j].clone();
            copyListIn[j].setNameP(getListIn()[j].getNameP());
            copyListIn[j].setNameT(getListIn()[j].getNameT());
        }

        for (int j = 0; j < numOut; j++) {
            copyListOut[j] = getListOut()[j].clone();
            copyListOut[j].setNameP(getListOut()[j].getNameP());
            copyListOut[j].setNameT(getListOut()[j].getNameT());
        }

        PetriNet net = new PetriNet(name, copyListP, copyListT, copyListIn, copyListOut);

        return net;
    }
    
    public boolean hasParameters() { // added by Katya 08.12.2016
        for (PetriP petriPlace : ListP) {
            if (petriPlace.markIsParam()) {
                return true;
            }
        }
        for (PetriT petriTran : ListT) {
            if (petriTran.distributionIsParam() || petriTran.parametrIsParam() || petriTran.priorityIsParam() || petriTran.probabilityIsParam()) {
                return true;
            }
        }
        for (ArcIn arcIn : getListIn()) {
            if (arcIn.infIsParam() || arcIn.kIsParam()) {
                return true;
            }
        }
        for (ArcOut arcOut : getListOut()) {
            if (arcOut.kIsParam()) {
                return true;
            }
        }
        return false;
    }

    public static PetriNet CreateTaskGenerator2() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",1));
        d_P.add(new PetriP("P2",0));
        
        d_T.add(new PetriT("T1",40.0));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(0),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        
        PetriNet d_Net = new PetriNet("Task2",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
     public static PetriNet CreateProcessor1() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",3));
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("P5",1));
        d_P.add(new PetriP("P6",1));
        d_P.add(new PetriP("P7",1));
        
        d_T.add(new PetriT("T1",14.0));
        d_T.add(new PetriT("T2",14.0));
        d_T.add(new PetriT("T3",14.0));
        d_T.add(new PetriT("T4",60.0));
        d_T.get(3).setDistribution("norm", d_T.get(3).getTimeServ());
        d_T.get(3).setParamDeviation(10.0);
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(2),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
        
        PetriNet d_Net = new PetriNet("Processor1",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
     public static PetriNet CreateProcessor2() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",3));
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("P5",1));
        d_P.add(new PetriP("P6",1));
        d_P.add(new PetriP("P7",1));
        
        d_T.add(new PetriT("T1",15.0));
        d_T.add(new PetriT("T2",15.0));
        d_T.add(new PetriT("T3",15.0));
        d_T.add(new PetriT("T4",100.0));
        d_T.get(3).setDistribution("exp", d_T.get(3).getTimeServ());
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(2),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
        
        PetriNet d_Net = new PetriNet("Processor2",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }

     public static PetriNet CreateStorage() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",1));
        d_P.add(new PetriP("P4",1));
        d_P.add(new PetriP("P5",1));
        
        d_T.add(new PetriT("T1",13.0));
        d_T.add(new PetriT("T2",13.0));
        d_T.add(new PetriT("T3",13.0));
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(3),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(2),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        
        PetriNet d_Net = new PetriNet("Storage",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }

     public static PetriNet CreateProcessor21() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",3));
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("P5",1));
        d_P.add(new PetriP("P6",1));
        d_P.add(new PetriP("P7",1));
        
        d_T.add(new PetriT("T1",14.0));
        d_T.add(new PetriT("T2",14.0));
        d_T.add(new PetriT("T3",14.0));
        d_T.add(new PetriT("T4",60.0));
        d_T.get(3).setDistribution("norm", d_T.get(3).getTimeServ());
        d_T.get(3).setParamDeviation(10.0);
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(0),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
        
        PetriNet d_Net = new PetriNet("Processor21",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
     public static PetriNet CreateProcessor22() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",3));
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("P5",1));
        d_P.add(new PetriP("P6",1));
        d_P.add(new PetriP("P7",1));
        
        d_T.add(new PetriT("T1",15.0));
        d_T.add(new PetriT("T2",15.0));
        d_T.add(new PetriT("T3",15.0));
        d_T.add(new PetriT("T4",100.0));
        d_T.get(3).setDistribution("exp", d_T.get(3).getTimeServ());
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(0),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
        
        PetriNet d_Net = new PetriNet("Processor22",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }

     public static PetriNet CreateStorage2() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",1));
        d_P.add(new PetriP("P4",1));
        d_P.add(new PetriP("P5",1));
        
        d_T.add(new PetriT("T1",13.0));
        d_T.add(new PetriT("T2",13.0));
        d_T.add(new PetriT("T3",13.0));
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(0),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(3),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(2),1));
        
        PetriNet d_Net = new PetriNet("Storage2",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
     
     public static PetriNet CreateBusStop() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",1));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",0));
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("Counter",0));
        
        d_T.add(new PetriT("T1",0.5));
        d_T.get(0).setDistribution("unif", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.2);
        d_T.add(new PetriT("T2",0.0));
        d_T.add(new PetriT("T3",0.0));
        d_T.get(2).setPriority(1);
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(2),30));
        d_In.get(3).setInf(true);
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(0),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(4),1));
        
        PetriNet d_Net = new PetriNet("BusStop",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
     public static PetriNet CreateBusA(int seatQuantity) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        
        d_P.add(new PetriP("P1",0));
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("P3",0));
        d_P.add(new PetriP("P4",1));
        d_P.add(new PetriP("P5",0));
        d_P.add(new PetriP("P6",0));
        d_P.add(new PetriP("P7",0));
        d_P.add(new PetriP("P8",0));
        d_P.add(new PetriP("P9",0));
        d_P.add(new PetriP("P10",0));
        
        d_T.add(new PetriT("T1",0.0));
        d_T.get(0).setPriority(1);
        d_T.add(new PetriT("T2",20.0));
        d_T.get(1).setDistribution("unif", d_T.get(1).getTimeServ());
        d_T.get(1).setParamDeviation(5.0);
        d_T.add(new PetriT("T3",5.0));
        d_T.get(2).setDistribution("unif", d_T.get(2).getTimeServ());
        d_T.get(2).setParamDeviation(1.0);
        d_T.add(new PetriT("T4",5.0));
        d_T.get(3).setDistribution("unif", d_T.get(3).getTimeServ());
        d_T.get(3).setParamDeviation(1.0);
        d_T.add(new PetriT("T5",20.0));
        d_T.get(4).setDistribution("unif", d_T.get(4).getTimeServ());
        d_T.get(4).setParamDeviation(5.0);
        d_T.add(new PetriT("T6",0.0));
        d_T.get(5).setPriority(1);
        d_T.add(new PetriT("T7",0.0));
        
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(1),seatQuantity));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(8),d_T.get(5),1));
        d_In.add(new ArcIn(d_P.get(7),d_T.get(6),1));
        d_In.add(new ArcIn(d_P.get(3),d_T.get(0),1));
        d_In.get(5).setInf(true);
        d_In.add(new ArcIn(d_P.get(3),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(5),1));
        d_In.get(7).setInf(true);
        d_In.add(new ArcIn(d_P.get(6),d_T.get(4),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(4),seatQuantity));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(3),1));
        
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(6),d_P.get(9),seatQuantity*20));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(5),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(4),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(7),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(7),1));
        
        PetriNet d_Net = new PetriNet("BusA",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
     }
    public static PetriNet CreateBusB(int seatQuantity) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
       ArrayList<PetriP> d_P = new ArrayList<>();
       ArrayList<PetriT> d_T = new ArrayList<>();
       ArrayList<ArcIn> d_In = new ArrayList<>();
       ArrayList<ArcOut> d_Out = new ArrayList<>();
       
       d_P.add(new PetriP("P1",0));
       d_P.add(new PetriP("P2",0));
       d_P.add(new PetriP("P3",0));
       d_P.add(new PetriP("P4",1));
       d_P.add(new PetriP("P5",0));
       d_P.add(new PetriP("P6",0));
       d_P.add(new PetriP("P7",0));
       d_P.add(new PetriP("P8",0));
       d_P.add(new PetriP("P9",0));
       d_P.add(new PetriP("P10",0));
       
       d_T.add(new PetriT("T1",0.0));
       d_T.add(new PetriT("T2",30.0));
       d_T.get(1).setDistribution("unif", d_T.get(1).getTimeServ());
       d_T.get(1).setParamDeviation(5.0);
       d_T.add(new PetriT("T3",5.0));
       d_T.get(2).setDistribution("unif", d_T.get(2).getTimeServ());
       d_T.get(2).setParamDeviation(1.0);
       d_T.add(new PetriT("T4",5.0));
       d_T.get(3).setDistribution("unif", d_T.get(3).getTimeServ());
       d_T.get(3).setParamDeviation(1.0);
       d_T.add(new PetriT("T5",30.0));
       d_T.get(4).setDistribution("unif", d_T.get(4).getTimeServ());
       d_T.get(4).setParamDeviation(5.0);
       d_T.add(new PetriT("T6",0.0));
       d_T.add(new PetriT("T7",0.0));
       
       d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
       d_In.add(new ArcIn(d_P.get(1),d_T.get(1),seatQuantity));
       d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
       d_In.add(new ArcIn(d_P.get(8),d_T.get(5),1));
       d_In.add(new ArcIn(d_P.get(7),d_T.get(6),1));
       d_In.add(new ArcIn(d_P.get(3),d_T.get(0),1));
       d_In.get(5).setInf(true);
       d_In.add(new ArcIn(d_P.get(3),d_T.get(1),1));
       d_In.add(new ArcIn(d_P.get(6),d_T.get(5),1));
       d_In.get(7).setInf(true);
       d_In.add(new ArcIn(d_P.get(6),d_T.get(4),1));
       d_In.add(new ArcIn(d_P.get(5),d_T.get(4),seatQuantity));
       d_In.add(new ArcIn(d_P.get(4),d_T.get(3),1));
       
       d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
       d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
       d_Out.add(new ArcOut(d_T.get(6),d_P.get(9),seatQuantity*20));
       d_Out.add(new ArcOut(d_T.get(3),d_P.get(3),1));
       d_Out.add(new ArcOut(d_T.get(2),d_P.get(6),1));
       d_Out.add(new ArcOut(d_T.get(5),d_P.get(5),1));
       d_Out.add(new ArcOut(d_T.get(4),d_P.get(4),1));
       d_Out.add(new ArcOut(d_T.get(3),d_P.get(7),1));
       d_Out.add(new ArcOut(d_T.get(2),d_P.get(7),1));
       
       PetriNet d_Net = new PetriNet("BusA",d_P,d_T,d_In,d_Out);
       PetriP.initNext();
       PetriT.initNext();
       ArcIn.initNext();
       ArcOut.initNext();

       return d_Net;
    }

}
