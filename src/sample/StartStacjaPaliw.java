package sample;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.monitors.Statistics;

import java.awt.*;

public class StartStacjaPaliw {

    private SimManager simManager;
    private StacjaPaliw stacjaPaliw;
    private double prawdopodobienstwo;

    public double benzynaG;
    public double onG;
    public double lpgG;
    public double myjniaG;

    public double tankowanieG;
    public double mycieG;

    public StartStacjaPaliw(int[] data1, double[] data2) {
        int maxLiczbaKlientow = data1[0]; //1000
        SimManager simMgr = new SimManager();
        this.simManager = simMgr;
        double mi = data2[0]; //10
        double normalOczekiwana = data2[1]; //3
        double normalOdchylenie = data2[2]; //1
        double generatorMin = data2[3]; //1.0
        double generatorMax = data2[4]; //10.0
        double erlang = data2[5]; //1
        //w tablic wielkosciKolejek znajduja sie kolejno: {Buffor, Benzyna, ON, LPG, Kasa, Myjnia}
        int[] wielkosciKolejek = {data1[1], data1[2], data1[3], data1[4], data1[5], data1[6]};
        //w tablic wielkosciDystrybutorow znajduja sie kolejno: {Benzyna, ON, LPG, Kasa}
        int[] wielkosciDystrybutorow = {data1[7], data1[8], data1[9], data1[10]};
        //Monitory wielkosci kolejek Benzyny, ON, LPG, Myjni
        MonitoredVar[] mvSize = {new MonitoredVar(simMgr), new MonitoredVar(simMgr), new MonitoredVar(simMgr), new MonitoredVar(simMgr)};
        //Monitory czasu tankowania i mycia
        MonitoredVar[] mvTime = {new MonitoredVar(simMgr), new MonitoredVar(simMgr)};

        this.stacjaPaliw = new StacjaPaliw(maxLiczbaKlientow, mi, normalOczekiwana, normalOdchylenie, generatorMin, generatorMax, erlang, wielkosciKolejek, wielkosciDystrybutorow, mvSize, mvTime);

        Thread t = new Thread(() ->{
            try {
                new KontrolowanieSymulacji(simMgr, data1[11], data1[12]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() ->{
            try {
                new StworzenieKlienta(stacjaPaliw, 0);
                t.start();
                simMgr.startSimulation();
            } catch (SimControlException e) {
                e.printStackTrace();
            }

            Diagram diagSize0 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Diagram wielkosci kolejki Benzyny");
            diagSize0.add(mvSize[0], Color.black);
            diagSize0.show();
            Diagram diagSize1 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Diagram wielkosci kolejki ON");
            diagSize1.add(mvSize[1], Color.black);
            diagSize1.show();
            Diagram diagSize2 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Diagram wielkosci kolejki LPG");
            diagSize2.add(mvSize[2], Color.black);
            diagSize2.show();
            Diagram diagSize3 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Diagram wielkosci kolejki Myjni");
            diagSize3.add(mvSize[3], Color.black);
            diagSize3.show();

            Diagram diagTime0 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Czas tankowania");
            diagTime0.add(mvTime[0], Color.black);
            diagTime0.show();
            Diagram diagTime1 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Czas mycia");
            diagTime1.add(mvTime[1], Color.black);
            diagTime1.show();


            System.out.println("\nKolejka: Obsluzono/Utracono/Suma(Obsluzono + Utracono)");

            int suma = stacjaPaliw.getKolBuffor().getLicznikKlientow() + stacjaPaliw.getKolBuffor().getLicznikStrat();
            System.out.println("Buffor: " + stacjaPaliw.getKolBuffor().getLicznikKlientow() + "/" + stacjaPaliw.getKolBuffor().getLicznikStrat() + "/" + suma);

            int suma0 = stacjaPaliw.getKolBenzyna().getLicznikKlientow() + stacjaPaliw.getKolBenzyna().getLicznikStrat();
            System.out.println("Benzyna: " + stacjaPaliw.getKolBenzyna().getLicznikKlientow() + "/" + stacjaPaliw.getKolBenzyna().getLicznikStrat() + "/" + suma0);

            int suma1 = stacjaPaliw.getKolOn().getLicznikKlientow() + stacjaPaliw.getKolOn().getLicznikStrat();
            System.out.println("ON: " + stacjaPaliw.getKolOn().getLicznikKlientow() + "/" + stacjaPaliw.getKolOn().getLicznikStrat() + "/" + suma1);

            int suma2 = stacjaPaliw.getKolLpg().getLicznikKlientow() + stacjaPaliw.getKolLpg().getLicznikStrat();
            System.out.println("LPG: " + stacjaPaliw.getKolLpg().getLicznikKlientow() + "/" + stacjaPaliw.getKolLpg().getLicznikStrat() + "/" + suma2);

            int suma3 = stacjaPaliw.getKolMyjnia().getLicznikKlientow() + stacjaPaliw.getKolMyjnia().getLicznikStrat();
            System.out.println("Myjnia: " + stacjaPaliw.getKolMyjnia().getLicznikKlientow() + "/" + stacjaPaliw.getKolMyjnia().getLicznikStrat() + "/" + suma3);

            System.out.println("\nLiczba klientow przy kasie: obsluzonych/utraconych/suma");

            int suma4 = stacjaPaliw.getKolKasa().getLicznikKlientow() + stacjaPaliw.getKolKasa().getLicznikStrat();
            System.out.println("Kasa: " + stacjaPaliw.getKolKasa().getLicznikKlientow() + "/" + stacjaPaliw.getKolKasa().getLicznikStrat() + "/" + suma4);

            System.out.println("\nTylko do myjni chcialo: " + stacjaPaliw.getTylkoMyjnia() + " klient(ow).");

            System.out.println("\nSpośrod klientow (nieutraconych) dodanych do wszystkich kolejek:");
            System.out.println("Myjnia (Tak/Nie): " + stacjaPaliw.getMyjniaTak() + "/" + stacjaPaliw.getMyjniaNie());

            System.out.println("------------------------------------------------------------------\nOczekiwana graniczna liczba samochodow w kolejnce:");
            benzynaG = Statistics.arithmeticMean(mvSize[0]);
            System.out.println("Benzyna: " + benzynaG);
            onG = Statistics.arithmeticMean(mvSize[1]);
            System.out.println("ON: " + onG);
            lpgG = Statistics.arithmeticMean(mvSize[2]);
            System.out.println("LPG: " + lpgG);
            myjniaG = Statistics.arithmeticMean(mvSize[3]);
            System.out.println("Myjnia: " + myjniaG);

            tankowanieG = Statistics.arithmeticMean(mvTime[0]);
            System.out.println("\nOczekiwany graniczny czas tankowania samochodu: " + tankowanieG);

            mycieG = Statistics.arithmeticMean(mvTime[1]);
            System.out.println("Oczekiwany graniczny czas mycia samochodu: " + mycieG);

            prawdopodobienstwo = ((double)stacjaPaliw.getKolBuffor().getLicznikStrat()/suma);
            System.out.println("Prawdopodobienstwo utraty klienta: " + prawdopodobienstwo);
        });

        t2.start();
    }

    public double getSimTime() {return simManager.simTime();}

    public StacjaPaliw getStacjaPaliw() {return this.stacjaPaliw;}
}