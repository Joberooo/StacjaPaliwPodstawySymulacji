package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML private TextField maxLiczbaKlientow;
    @FXML private TextField mi;
    @FXML private TextField normalOczekiwana;
    @FXML private TextField normalOdchylenie;
    @FXML private TextField generatorMin;
    @FXML private TextField generatorMax;
    @FXML private TextField erlang;
    @FXML private TextField wielkosciKolejek0;
    @FXML private TextField wielkosciKolejek1;
    @FXML private TextField wielkosciKolejek2;
    @FXML private TextField wielkosciKolejek3;
    @FXML private TextField wielkosciKolejek4;
    @FXML private TextField wielkosciKolejek5;
    @FXML private TextField wielkosciDystrybutorow0;
    @FXML private TextField wielkosciDystrybutorow1;
    @FXML private TextField wielkosciDystrybutorow2;
    @FXML private TextField wielkosciDystrybutorow3;
    @FXML private TextField skokCzasu;
    @FXML private Button sendButton;
    @FXML private Label ourLabel;
    @FXML private Label blad;
    @FXML private TextField odswiezenieText;

    private int[] data1;
    private double[] data2;

    @FXML
    public void initialize(){
        sendButton.setDisable(true);
        mi.setText("20");
        normalOczekiwana.setText("3");
        normalOdchylenie.setText("1");
        generatorMin.setText("1");
        generatorMax.setText("10");
        erlang.setText("1");
        wielkosciKolejek0.setText("10");
        wielkosciKolejek1.setText("5");
        wielkosciKolejek2.setText("5");
        wielkosciKolejek3.setText("5");
        wielkosciKolejek4.setText("2");
        wielkosciKolejek5.setText("3");
        wielkosciDystrybutorow0.setText("2");
        wielkosciDystrybutorow1.setText("2");
        wielkosciDystrybutorow2.setText("4");
        wielkosciDystrybutorow3.setText("5");
        skokCzasu.setText("50");
        odswiezenieText.setText("100");
    }

    @FXML
    public void onButtonClick(ActionEvent e){
        if(e.getSource().equals(sendButton)){
            System.out.println("maxLiczbaKlientow: "+ maxLiczbaKlientow.getText());
            System.out.println("mi: "+ mi.getText());
            System.out.println("normalOczekiwana: "+ normalOczekiwana.getText());
            System.out.println("normalOdchylenie: "+ normalOdchylenie.getText());
            System.out.println("generatorMin: "+ generatorMin.getText());
            System.out.println("generatorMax: "+ generatorMax.getText());
            System.out.println("erlang: "+ erlang.getText());
        }
        //--------------------------------------------------------------------
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("petrolStation.fxml"));
            Parent root = loader.load();
            PetrolController controller = loader.getController();
            controller.setData(data1, data2);

            Stage stage = new Stage();
            stage.setTitle("Stacja Paliw is running...");

            stage.setScene(new Scene(root, 900, 400));

            stage.show();

            ourLabel.setText("Variables sended!");
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private boolean check(int[] tab){
        for (int value : tab) {
            if (value <= 0) return false;
        }
        return true;
    }

    private Boolean[] checkEmpty(String[] tab){
        Boolean[] chceck = new Boolean[tab.length];
        for(int value = 0; value < tab.length; value++){
            chceck[value] = tab[value].isEmpty() || tab[value].trim().isEmpty();
        }
        return chceck;
    }

    @FXML
    public void handeKeyReleased(){
        String[] wielkosci1 = {maxLiczbaKlientow.getText(), mi.getText(), normalOczekiwana.getText(), normalOdchylenie.getText(), generatorMin.getText(), generatorMax.getText(), erlang.getText()};
        Boolean[] check1 = checkEmpty(wielkosci1);
        String[] wielkosci2 = {wielkosciKolejek0.getText(), wielkosciKolejek1.getText(), wielkosciKolejek2.getText(), wielkosciKolejek3.getText(), wielkosciKolejek4.getText(), wielkosciKolejek5.getText()};
        Boolean[] check2 = checkEmpty(wielkosci2);
        String[] wielkosci3 = {wielkosciDystrybutorow0.getText(), wielkosciDystrybutorow1.getText(), wielkosciDystrybutorow2.getText(), wielkosciDystrybutorow3.getText(), skokCzasu.getText(), odswiezenieText.getText()};
        Boolean[] check3 = checkEmpty(wielkosci3);

        boolean x = check1[0] || check1[1] || check1[2] || check1[3] || check1[4] || check1[5] || check1[6] || check2[0] || check2[1] || check2[2] || check2[3] || check2[4] || check2[5] || check3[0] || check3[1] || check3[2] || check3[3] || check3[4] || check3[5];

        try {
            blad.setText("");
            sendButton.setDisable(true);

            if (!x) {
                data1 = new int[]{Integer.parseInt(wielkosci1[0]), Integer.parseInt(wielkosci2[0]), Integer.parseInt(wielkosci2[1]), Integer.parseInt(wielkosci2[2]), Integer.parseInt(wielkosci2[3]), Integer.parseInt(wielkosci2[4]), Integer.parseInt(wielkosci2[5]), Integer.parseInt(wielkosci3[0]), Integer.parseInt(wielkosci3[1]), Integer.parseInt(wielkosci3[2]), Integer.parseInt(wielkosci3[3]), Integer.parseInt(wielkosci3[4]), Integer.parseInt(wielkosci3[5])};
                data2 = new double[]{Double.parseDouble(wielkosci1[1]), Double.parseDouble(wielkosci1[2]), Double.parseDouble(wielkosci1[3]), Double.parseDouble(wielkosci1[4]), Double.parseDouble(wielkosci1[5]), Double.parseDouble(wielkosci1[6])};
            }
            if(check(data1)) sendButton.setDisable(x);
            else{
                blad.setText("Wpisz wszystkie wartosci!");
                sendButton.setDisable(true);
            }
        }catch(Exception exception){
            System.out.println("Wpisz wszystkie dane poprawnie!");
            blad.setText("Wpisz wszystkie wartosci!");
        }
    }
}