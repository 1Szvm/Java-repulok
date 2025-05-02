package com.example.repulokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsBrands;
    @FXML private ListView<String> lsModle;

    private FileChooser fc = new FileChooser();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Fájlok","*.csv"));
    }


    @FXML private void onMegnyitasClick(){
        File fbe=fc.showOpenDialog(lsBrands.getScene().getWindow());
        if(fbe!=null){
            repulok.clear();
            lsBrands.getItems().clear();
            betolt(fbe);
            TreeSet<String> brands = new TreeSet<>();
            for(Repulo r:repulok){
                brands.add(r.tipus.split(" ")[0]);
            }
            for (String brand:brands){
                lsBrands.getItems().add(brand);
            }
        }
    }

    public void renderUtazasok(){
        lsModle.getItems().clear();
        for(Repulo r:repulok){
            if (lsBrands.getSelectionModel().getSelectedItem().equals(r.tipus.split(" ")[0])){
                lsModle.getItems().add(r.tipus);
            }
        }
    }

    private void betolt(File Fajl){
        Scanner be = null;
        try{
            be= new Scanner(Fajl,"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                repulok.add(new Repulo(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public class Repulo{
        String tipus;
        double hossz;
        int suly;
        int ferohely;
        int tank;

        public Repulo(String sor) {
            String[] s = sor.split(";");
            this.tipus = s[0];
            this.hossz = Double.parseDouble(s[1]);
            this.suly = Integer.parseInt(s[2]);
            this.ferohely = Integer.parseInt(s[3]);
            this.tank = Integer.parseInt(s[4]);
        }
    }

    public ArrayList<Repulo> repulok = new ArrayList<>();

    @FXML private void onKilépesClick(){
        Platform.exit();
    }

    @FXML private void onNevjegyzekClikc(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Repülők v1.0.0\n(C) Kandó");
        info.showAndWait();
    }
}