import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
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

    public Main(){
        betolt("repulok.csv");

        //0.feladat
        System.out.printf("0) Összesen %d repülő adata beolvasva.\n",repulok.size());
        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n",repulok.get((int)(Math.random()*repulok.size())).tipus);

        //1.feladat
        Repulo max1 = null;
        Repulo max2 = null;

        for (Repulo r : repulok) {
            if (max1 == null || r.ferohely > max1.ferohely) {
                max2 = max1;
                max1 = r;
            } else if (max2 == null || r.ferohely > max2.ferohely) {
                max2 = r;
            }
        }

        System.out.printf("1) Legtöbb férőhellyel rendelkezik: %s (%d hely)\n", max1.tipus, max1.ferohely);
        System.out.printf("   A második legtöbb férőhely: %s (%d hely)\n", max2.tipus, max2.ferohely);

        //2.feladat
        double avgWeight=0;
        int count=0;
        for (Repulo r : repulok) {
            if(r.suly<100000){
                avgWeight+=r.suly;
                count++;
            }
        }
        System.out.printf("2) A 100000kg súlynál kisebb gépek (%d darab) átlagsúlya: %.2fkg\n",count,avgWeight/count);

        //3.faladat
        ArrayList<String> numberless= new ArrayList<>();
        for (Repulo r : repulok) {
            if(!r.tipus.matches(".*\\d.*")){
                numberless.add(r.tipus);;
            }
        }

        System.out.print("3) Típusok, amelyikben nincs szám: ");
        for(String tipus:numberless){
            System.out.print(tipus+" ");
        }
        System.out.println();

        //4.faladat
        TreeSet<String> brands= new TreeSet<>();
        for (Repulo r : repulok) {
            brands.add(r.tipus.split(" ")[0]);
        }
        System.out.print("4) Gyártók: ");
        for(String brnad:brands){
            if(!brands.last().equals(brnad)){
                System.out.print(brnad+", ");
            }else {
                System.out.print(brnad+" ");
            }
        }
        System.out.println();

        //a feladat nem mondta hogy az előzö lisából kell
        String randBrand=repulok.get((int)(Math.random()*repulok.size())).tipus.split(" ")[0];
        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n",randBrand);
        System.out.println("   Termékeik:");
        for (Repulo r : repulok) {
            if(r.tipus.split(" ")[0].equals(randBrand)){
                System.out.println("   - "+r.tipus);
            }
        }

        //5.feladat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("sokutas.txt"),"utf-8");
            for (Repulo r : repulok) {
                if (r.ferohely>300) ki.printf("%s / %d hely\r\n",r.tipus,r.ferohely);
            }
        }catch (UnsupportedEncodingException | FileNotFoundException e){
            System.out.println(e);
        }finally {
            if(ki !=null) ki.close();
        }
        System.out.printf("6) Az első év adatai kiírva a kezdes.txt-be");
    }

    private void betolt(String FajNev){
        Scanner be = null;
        try{
            be= new Scanner(new File(FajNev),"utf-8");
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

    public static void main(String[] args) {
        new Main();
    }
}