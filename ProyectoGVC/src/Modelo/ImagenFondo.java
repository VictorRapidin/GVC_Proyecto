/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author ghust
 */
public class ImagenFondo extends JPanel{
    private Image imagen;
    private ImageIcon imagenIcono;
    
    public ImagenFondo(String ruta){
        imagenIcono = new ImageIcon(ruta);
    }//ImagenFondo
    
    public void paint(Graphics g){
        //Se establece la imagen a colocar de fonodo
        imagen = imagenIcono.getImage();
        //Obtiene el tamaño de ventana para dibujar la imagen
        g.drawImage(imagen, 0, 0, getWidth(),getHeight(), this);
        //Coloca transparencia de ventana
        setOpaque(false);
        //Redibuja la ventana
        super.paint(g);
    }//paint
}//class
