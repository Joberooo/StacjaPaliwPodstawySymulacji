package sample;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Stacja Paliw - Patryk BARCZAK - I7B1S1");

        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        SimManager simMgr = new SimManager();

        int maxLiczbaKlientow = 1000;
        double mi = 10;
        double normalOczekiwana = 3;
        double normalOdchylenie = 1;
        double generatorMin = 1.0;
        double generatorMax = 10.0;
        double erlang = 1;
        //w tablic wielkosciKolejek znajdują się kolejno: {Buffor, Benzyna, ON, LPG, Kasa, Myjnia}
        int[] wielkosciKolejek = {10, 5, 5, 5, 2, 3};
        //w tablic wielkosciDystrybutorow znajdują się kolejno: {Benzyna, ON, LPG, Kasa}
        int[] wielkosciDystrybutorow = {2, 2, 4, 5};
        //Monitory wielkosci kolejek Benzyny, ON, LPG, Myjni
        MonitoredVar[] mvSize = {new MonitoredVar(simMgr), new MonitoredVar(simMgr), new MonitoredVar(simMgr), new MonitoredVar(simMgr)};
        //Monitory czasu tankowania i mycia
        MonitoredVar[] mvTime = {new MonitoredVar(simMgr), new MonitoredVar(simMgr)};

        StacjaPaliw stacjaPaliw = new StacjaPaliw(maxLiczbaKlientow, mi, normalOczekiwana, normalOdchylenie, generatorMin, generatorMax, erlang, wielkosciKolejek, wielkosciDystrybutorow, mvSize, mvTime);

        try {
            new StworzenieKlienta(stacjaPaliw, 0);

            simMgr.startSimulation();

        } catch (SimControlException e) {
            // TODO Auto-generated catch block
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

        System.out.println("\nKolejka: Obsłużono/Utracono/Suma(Obsłużono + Utracono)");

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

        System.out.println("\nLiczba klientów przy kasie: obsłużonych/utraconych/suma");

        int suma4 = stacjaPaliw.getKolKasa().getLicznikKlientow() + stacjaPaliw.getKolKasa().getLicznikStrat();
        System.out.println("Kasa: " + stacjaPaliw.getKolKasa().getLicznikKlientow() + "/" + stacjaPaliw.getKolKasa().getLicznikStrat() + "/" + suma4);

        System.out.println("\nTylko do myjni chciało: " + stacjaPaliw.getTylkoMyjnia() + " klient(ów).");

        System.out.println("\nSpośród klientów (nieutraconych) dodanych do wszystkich kolejek:");
        System.out.println("Myjnia (Tak/Nie): " + stacjaPaliw.getMyjniaTak() + "/" + stacjaPaliw.getMyjniaNie());
    }
}
