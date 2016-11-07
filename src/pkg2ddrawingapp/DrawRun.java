/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ddrawingapp;

import javax.swing.JFrame;

/**
 *
 * @author Austin
 */
public class DrawRun {
            
        public static void main(String[] args) 
        {
            DrawPane drawPane = new DrawPane();
            drawPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            drawPane.setSize(750,500);
            drawPane.setVisible(true);
        }
    
}
