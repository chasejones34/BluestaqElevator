import java.awt.Color;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
       int startX = 450;
       int startY = 750;


        //creating the background
        ElevatorFloors elevator = new ElevatorFloors();
        elevator.setOpaque(true);
        elevator.setBounds(0, 0, 1000, 1000);

        //creating the movable elevator car
        Carriage car = new Carriage(startX, startY);    //startX and startY to tell the car where to start
        car.setOpaque(false);
        car.setForeground(Color.DARK_GRAY);
        car.setBounds(startX, startY, 1000, 1000);  
        
        //creating the buttons and their functions
        ButtonPanel buttons = new ButtonPanel(car);
        buttons.setOpaque(false);
        buttons.setBounds(700, 250, 150, 300);

        car.setControlPanel(buttons);   //Lets the carrage know about the button pannel

        Firetruck truck = new Firetruck();
        car.setFiretruck(truck);  //connects the firetruck to the carriage

        //using a JLayeredPane in order to have multiple paint objects on screen at the same time
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000,1000);
        buttons.setOpaque(false);
        layeredPane.setLayout(null);
        
        //adding all teh materials to the JLayeredPane
        layeredPane.add(buttons, JLayeredPane.MODAL_LAYER);
        layeredPane.add(elevator, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(car, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(truck, JLayeredPane.DRAG_LAYER);
        
        //Initializing JFrame and all of its attributes
        JFrame frame = new JFrame("Elevator Project");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        
        frame.add(layeredPane);     //Add the JLayeredPane to the JFrame.


    }


    
}
