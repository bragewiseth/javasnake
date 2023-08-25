import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI {
    Modell modell;
    JFrame vindu;
    JPanel panel, konsoll, lengde, styring, rutenett, avslutt;
    JPanel[][] ruter = new JPanel[12][12];
    JButton stoppknapp, opp, ned, hoyre, venstre;
    JLabel len;

    GUI (Modell m) {
        modell = m;

        try {
            // UIManager.setLookAndFeel("javax.swing.plaf.gtk.GTKLookAndFeel");
            // UIManager.getCrossPlatformLookAndFeelClassName();
        } catch (Exception e) { System.exit(0); }
        //  ALLE JPANELS SOM TILSAMMEN BESTEMMER HVORDAN PROGRAMMET SER UT
        vindu = new JFrame("SNAKE");
        panel = new JPanel(); panel.setLayout(new BorderLayout());
        konsoll = new JPanel();
        lengde = new JPanel(); lengde.setPreferredSize(new Dimension(150, 150));
        styring = new JPanel();
        avslutt = new JPanel(); avslutt.setPreferredSize(new Dimension(150, 150));
        rutenett = new JPanel();
        
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        konsoll.setLayout(new BorderLayout());
        styring.setLayout(new BorderLayout());
        styring.setPreferredSize(new Dimension(200, 150));
        
        len = new JLabel("2"); len.setFont(new Font("Serif", Font.PLAIN, 40));
        
        opp = new JButton("^"); opp.setPreferredSize(new Dimension(50, 50));
        ned = new JButton("v"); ned.setPreferredSize(new Dimension(50, 50));
        venstre = new JButton("<"); venstre.setPreferredSize(new Dimension(75, 50));
        hoyre = new JButton(">"); hoyre.setPreferredSize(new Dimension(75, 50));
        stoppknapp = new JButton("Exit"); 
        
        rutenett.setLayout(new GridLayout(12,12, 2, 2));
        rutenett.setPreferredSize(new Dimension(500, 500));
        
        for (int rad = 0; rad < 12; ++rad) {
            for (int kol = 0; kol < 12; ++kol) {
                JPanel b = new JPanel(); b.setBorder(BorderFactory.createLoweredBevelBorder());
                ruter[rad][kol] = b;
                modell.ruter[rad][kol] = modell.new Rute(rad, kol);
                rutenett.add(b); } }
                
        //  LAGER MAT
        for (int i = 0; i < 10; i++) {
            int rad = (int)(Math.random()*(-2-11+1))+11;
            int kol = (int)(Math.random()*(-2-11+1))+11;
            ruter[rad][kol].add(new JLabel("$"));
            modell.ruter[rad][kol].mat++; }
            
        //  IMPLENENTERER FUNKSJON FOR ALLE KNAPPENE
        class Tast extends KeyAdapter {
            @Override
        public void keyPressed(KeyEvent e) { modell.keyPressed(e); } }

        class Stoppbehandler implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) { System.exit(0); } }
        
        class Opp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) { modell.slange.retning = 'o'; } }
            
        class Ned implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) { modell.slange.retning = 'n'; } }
            
        class Hoyre implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) { modell.slange.retning = 'h'; } }
                
        class Venstre implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) { modell.slange.retning = 'v'; } }
                    
                    
        vindu.setFocusable(true); vindu.addKeyListener(new Tast());
        opp.addActionListener(new Opp());
        ned.addActionListener(new Ned());
        hoyre.addActionListener(new Hoyre()); 
        venstre.addActionListener(new Venstre()); 
        stoppknapp.addActionListener(new Stoppbehandler());
        //  LEGGER SAMMEN ALLE JPANELS
        vindu.add(panel);
        lengde.add(new JLabel("Lengde:")); lengde.add(len);
        avslutt.add(stoppknapp);
        panel.add(konsoll, BorderLayout.NORTH);;
        konsoll.add(lengde, BorderLayout.WEST);
        styring.add(opp, BorderLayout.NORTH);
        styring.add(ned, BorderLayout.SOUTH);
        styring.add(hoyre,BorderLayout.EAST);
        styring.add(venstre, BorderLayout.WEST);
        konsoll.add(styring, BorderLayout.CENTER);
        konsoll.add(avslutt,BorderLayout.EAST);
                    
        panel.add(rutenett, BorderLayout.CENTER);
        vindu.pack();
        vindu.setVisible(true); }






        
    void updateSlange(int[] hale) {

        //  TEGNER SLANGEN
        for(int[] i : modell.slange.slangeListe) {
            ruter[i[0]][i[1]].setBackground(Color.GREEN); }
        ruter[modell.slange.slangeListe.get(0)[0]][modell.slange.slangeListe.get(0)[1]].setBackground(Color.ORANGE);
        // FJERNER HALEN
        ruter[hale[0]][hale[1]].setBackground(null);
        // OPPDATERER VINDU
        vindu.setVisible(true); }




    void mat(int[] hale, int[] hode) {

        len.setText(Integer.toString(modell.slange.slangeListe.size()));
        // LAGER NY MAT ET ANNET STED
        ArrayList<Modell.Rute> ledigeRuter = modell.finnLedigeRuter();
        int i = (int)(Math.random()*(0-ledigeRuter.size()))+ledigeRuter.size()-1;
        try {
            Modell.Rute r = ledigeRuter.get(i);
            ruter[r.rad][r.kol].add(new JLabel("$"));
            r.mat ++; 
        } catch (IndexOutOfBoundsException e) {}
        //  FJERNER MATEN I RUTEN
        ruter[hode[0]][hode[1]].removeAll(); }

}



//  GENERERER MAT PÅ EN TILFELDIG RUTE PÅ ET TILFELIG TIDSPUNKT
        // ArrayList<Modell.Rute> ledigeRuter = modell.finnLedigeRuter();
        // int mat = (int)(Math.random()*(0-24))+25;
        // if (mat == 5) {
        //     int i = (int)(Math.random()*(0-ledigeRuter.size()))+ledigeRuter.size()-1;
        //     Modell.Rute r = ledigeRuter.get(i);
        //     ruter[r.rad][r.kol].add(new JLabel("$"));
        //     r.mat ++; }
