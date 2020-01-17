public class Elevator {
    //tworzymy trzy watki
    static ElevatorCar car = new ElevatorCar();
    static ExternalPanelsAgent externalPanelsAgent = new ExternalPanelsAgent(car);
    static InternalPanelAgent internalPanelAgent = new InternalPanelAgent(car);

    //symulacja przywolania windy z panelu zewnętrznego
    static void makeExternalCall(int atFloor, boolean directionUp){
        try {
            externalPanelsAgent.input.put(new ExternalPanelsAgent.ExternalCall(atFloor,directionUp));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // symulacja wyboru pietra w panelu wewnętrznym
    static void makeInternalCall(int toFloor){
        try {
            internalPanelAgent.input.put(new InternalPanelAgent.InternalCall(toFloor));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //uruchomienie watków
    static void init(){
        car.start();
        externalPanelsAgent.start();
        internalPanelAgent.start();
    }

    //miejsce na kod testowy
    public static void main(String [] args) throws InterruptedException {
        init();

       /* System.out.println("--1--");
        makeExternalCall(4,false);
        Thread.currentThread().sleep(100);
        makeInternalCall(2);
        c
        makeExternalCall(6,true);*/
/*
        System.out.println("--2--");
        makeExternalCall(1, true);
        Thread.currentThread().sleep(100);
        makeInternalCall(7);
        Thread.currentThread().sleep(100);
        makeExternalCall(5,true);
        makeExternalCall(6,false);
        makeInternalCall(2);*/

        /*System.out.println("--3--");
        makeInternalCall(3);
        makeInternalCall(7);
        makeInternalCall(4);*/

        /*System.out.println("--4--");
        makeExternalCall(1,true);
        makeExternalCall(3,false);
        makeExternalCall(7,true);*/

        System.out.println("--5--");
        makeInternalCall(5);
        makeInternalCall(8);
        makeExternalCall(6,false);
        Thread.currentThread().sleep(100);
        makeInternalCall(2);
        makeExternalCall(3,true);
    }
}
