import java.util.ArrayList;

public class Modell {

    class Rute {
        int rad, kol;
        int mat;
        boolean slange; 

        Rute(int rad, int kol) {
            this.rad = rad; this.kol = kol;} }



    class Slange {
        ArrayList<int[]> slangeListe = new ArrayList<>();
        char retning = 'h';

        Slange() {
            int[] hode = {6,6}; int[] kropp = {6,5}; 
            slangeListe.add(hode); slangeListe.add(kropp); } }


    //******************************************************




    GUI gui;
    Rute[][] ruter = new Rute[12][12];
    Slange slange;



    // Konstruktør
    Modell() {
        gui = new GUI(this);
        slange = new Slange(); }




    int [] flytt() {
        int[] hale;
        hale = slange.slangeListe.remove(slange.slangeListe.size()-1);
        int[] hode = {slange.slangeListe.get(0)[0], slange.slangeListe.get(0)[1]};
        if (slange.retning == 'o') {hode[0]--; slange.slangeListe.add(0,hode);}
        if (slange.retning == 'n') {hode[0]++; slange.slangeListe.add(0,hode);}
        if (slange.retning == 'v') {hode[1]--; slange.slangeListe.add(0,hode);}
        if (slange.retning == 'h') {hode[1]++; slange.slangeListe.add(0,hode);}
        //  HVIS SLANGEN KRASJER I SEG SELV SKAL SPILLET AVSLUTTES
        try {
            if (ruter[hode[0]][hode[1]].slange) {System.exit(0);}
            //  HVIS SLANGEN TREFFER VEGGEN SKAL SPILLET AVSLUTTES
        } catch (IndexOutOfBoundsException e) { System.exit(0); }
        //  HVIS SLANGEN FINNER MAT SKAL DEN BLI LENGER OG NY MAT SKAL LAGES
        if (ruter[hode[0]][hode[1]].mat > 0) {
            slange.slangeListe.add(hale);
            gui.mat(hale, hode); 
            ruter[hode[0]][hode[1]].mat=0; }
        ruter[hale[0]][hale[1]].slange = false;
        return hale; }
    
    


    ArrayList<Rute> finnLedigeRuter() {

        ArrayList<Rute> ledigeRuter = new ArrayList<>();
        for (Rute[] rad : ruter) {
            for (Rute rute : rad) {
                if (!rute.slange) { 
                    if (rute.mat < 1) {
                        ledigeRuter.add(rute);}} 

        } } return ledigeRuter; }




    public static void main (String[] arg) {
        Modell modell = new Modell();
        Thread t = new Thread(new Sovetraad(modell));
        t.start(); }

}