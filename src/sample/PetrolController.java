package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PetrolController {

    @FXML private Label simTimeLabel;
    @FXML private Label licznikKlientow2;
    @FXML private Label prawdopodobienstwo2;
    @FXML private Label kolBenzyna;
    @FXML private Label kolOn;
    @FXML private Label kolLpg;
    @FXML private Label kolKasy;
    @FXML private Label kolMyjnie;
    @FXML private Label dysBenzyna;
    @FXML private Label dysOn;
    @FXML private Label dysLpg;
    @FXML private Label dysKasy;
    @FXML private Label myjnia;
    @FXML private Label myjniaTakNie;
    @FXML private Label bezynaGraniczna;
    @FXML private Label onGraniczna;
    @FXML private Label lpgGraniczna;
    @FXML private Label myjniaGraniczna;
    @FXML private Label tankowanieGraniczne;
    @FXML private Label mycieGraniczne;


    private int[] data1;
    private double[] data2;
    private StartStacjaPaliw startStacjaPaliw;

    @FXML
    public void initialize(){
        Runnable task = () -> {
            try{
                Thread.sleep(0);
                Platform.runLater(() -> this.startStacjaPaliw = new StartStacjaPaliw(data1, data2));
            } catch (InterruptedException event){
                //niewazne
            }
        };
        new Thread(task).start();
    }

    public void setData(int[] data1, double[] data2){
        this.data1 = data1;
        this.data2 = data2;

        System.out.println("Odswiezenie = " + data1[12]);
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(data1[12]),
                event -> {
                    simTimeLabel.setText("" + startStacjaPaliw.getSimTime());
                    simTimeLabel.setText("" + startStacjaPaliw.getSimTime());
                    licznikKlientow2.setText("" + startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikKlientow() + "/" + startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikStrat() + "/" + (startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikKlientow() + startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikStrat()));
                    int suma = startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikKlientow() + startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikStrat();
                    double prawdopodobienstwo = ((double)startStacjaPaliw.getStacjaPaliw().getKolBuffor().getLicznikStrat()/suma);
                    prawdopodobienstwo2.setText("" + prawdopodobienstwo);

                    kolBenzyna.setText("" + startStacjaPaliw.getStacjaPaliw().getKolBenzyna().size() + "/" + startStacjaPaliw.getStacjaPaliw().getKolBenzyna().getPojemnosc());
                    kolOn.setText("" + startStacjaPaliw.getStacjaPaliw().getKolOn().size() + "/" + startStacjaPaliw.getStacjaPaliw().getKolOn().getPojemnosc());
                    kolLpg.setText("" + startStacjaPaliw.getStacjaPaliw().getKolLpg().size() + "/" + startStacjaPaliw.getStacjaPaliw().getKolLpg().getPojemnosc());
                    kolKasy.setText("" + startStacjaPaliw.getStacjaPaliw().getKolKasa().size() + "/" + startStacjaPaliw.getStacjaPaliw().getKolKasa().getPojemnosc());
                    kolMyjnie.setText("" + startStacjaPaliw.getStacjaPaliw().getKolMyjnia().size() + "/" + startStacjaPaliw.getStacjaPaliw().getKolMyjnia().getPojemnosc());

                    myjniaTakNie.setText("" + startStacjaPaliw.getStacjaPaliw().getMyjniaTak() + "/" + startStacjaPaliw.getStacjaPaliw().getMyjniaNie());

                    dysBenzyna.setText("" + startStacjaPaliw.getStacjaPaliw().getDysBenzyna().getZajeteDystrybutory() + "/" + startStacjaPaliw.getStacjaPaliw().getDysBenzyna().getLiczbaDystrybutorow());
                    dysOn.setText("" + startStacjaPaliw.getStacjaPaliw().getDysOn().getZajeteDystrybutory() + "/" + startStacjaPaliw.getStacjaPaliw().getDysOn().getLiczbaDystrybutorow());
                    dysLpg.setText("" + startStacjaPaliw.getStacjaPaliw().getDysLpg().getZajeteDystrybutory() + "/" + startStacjaPaliw.getStacjaPaliw().getDysLpg().getLiczbaDystrybutorow());
                    dysKasy.setText("" + startStacjaPaliw.getStacjaPaliw().getDysKasa().getZajeteDystrybutory() + "/" + startStacjaPaliw.getStacjaPaliw().getDysKasa().getLiczbaDystrybutorow());

                    myjnia.setText("" + startStacjaPaliw.getStacjaPaliw().getMyjnia().getZajete());

                    bezynaGraniczna.setText("" + startStacjaPaliw.benzynaG);
                    onGraniczna.setText("" + startStacjaPaliw.onG);
                    lpgGraniczna.setText("" + startStacjaPaliw.lpgG);
                    myjniaGraniczna.setText("" + startStacjaPaliw.myjniaG);

                    tankowanieGraniczne.setText("" + startStacjaPaliw.tankowanieG);
                    mycieGraniczne.setText("" + startStacjaPaliw.mycieG);
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}