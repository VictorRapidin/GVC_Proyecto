
package gvcproyecto;

import java.awt.Splash;
import java.util.Timer;
import java.util.TimerTask;

public class GVCProyecto {

    public static void main(String[] args) {
        Splash s = new Splash(); // Preparación de la ventana principal
        s.setvisible(true); // Configuración del temporizador para el cambio de ventana
        Principal p = new Principal();
        Timer t = new Timer();
        TimerTask task1 = new TimerTask() {
            /**Acción que se ejecuta al finalizar el tiempo del Timer.
             * Oculta el Splash y hace visible la ventana Principal.
             */
            @Override
            public void run() {
                s.setvisible(false);
                p.setvisible(true);
            }
        };
        t.schedule(task1, 6000);
    } 
    
}

