/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectogvc;


import Vista.MenuPrincipal;
import Vista.Splash;
import java.util.Timer;
import java.util.TimerTask;

public class ProyectoGVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Splash s = new Splash();
        s.setVisible(true);
        MenuPrincipal p = new MenuPrincipal();
        
        Timer t = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                s.setVisible(false);
                p.setVisible(true);
            }
        };
        t.schedule(task1, 6000);
    } 
    
}
