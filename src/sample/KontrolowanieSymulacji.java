package sample;

import dissimlab.simcore.SimManager;

public class KontrolowanieSymulacji {
    private SimManager simMgr;
    private int skokCzasu;

    public void kontroluj(int odswiezanie) throws InterruptedException {
        double i = simMgr.simTime();
        simMgr.pauseSimulation();
        boolean pause = true;

        do{
            System.out.print("");
            if(pause) {
                simMgr.resumeSimulation();
                pause = false;
            }
            if(simMgr.simTime() > i + skokCzasu){
                i = simMgr.simTime();
                simMgr.pauseSimulation();
                pause = true;
                Thread.sleep(odswiezanie);
            }
        }while(true);
    }

    public KontrolowanieSymulacji(SimManager simMgr, int skokCzasu, int odswiezanie) throws InterruptedException {
        this.simMgr = simMgr;
        this.skokCzasu = skokCzasu;
        kontroluj(odswiezanie);
    }
}
