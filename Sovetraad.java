public class Sovetraad implements Runnable {
    Modell modell;
    Sovetraad(Modell g) {
        modell = g; }

        
    @Override
    public void run() {
        
        while(!Thread.currentThread().isInterrupted()) {
            try { Thread.sleep(700); } catch (InterruptedException e) { e.printStackTrace(); }
            
            int[] hale = modell.flytt();
            modell.gui.updateSlange(hale); } } 
}   