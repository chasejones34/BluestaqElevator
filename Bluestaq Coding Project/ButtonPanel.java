import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;



public class ButtonPanel extends JPanel{

    private Carriage elevator;
    public int currentPosY = 750;   //default starting Y value
    volatile boolean callFireDept = false;
    private List<JButton> floorButtons = new ArrayList<>();     //using an Array list so I can activate/deactive all buttons at once

    public ButtonPanel(Carriage elevator) {
        
        this.elevator = elevator;

        this.setLayout(null);   //setting layout to NULL so that I can manualy place buttons


        //Every button will have the same x value based on
        //the column that its in. The same goes for the y value
        //depending on the row.
        //Code for the buttons is simmilar for all, im not copying the comments again
        JButton button7 = new JButton();                                                                      //Initializing the button
            button7.setIcon(loadButtonIcon("Resources/ElevatorButtons/7.jpg", 40, 40));     //Setting the button Image
            button7.setBounds(15, 10, 50, 50);                                               //Setting teh size of the button
            button7.setBackground(Color.LIGHT_GRAY);                                                          //Setting background color in case the image doesnt load
            button7.addActionListener(e -> elevator.moveToFloor(7));                                    //Sets the action for the button (in this case: cale the moveToFloor() method from the carriage.java file)
            floorButtons.add(button7);                                                                        //Adds button to the activation/deatcivation array list
            add(button7);                                                                                     //adds physicall button to the panel

            JButton button8 = new JButton();
            button8.setIcon(loadButtonIcon("Resources/ElevatorButtons/8.jpg", 40, 40));
            button8.setBounds(85, 10, 50, 50);
            button8.setBackground(Color.LIGHT_GRAY);
            button8.addActionListener(e -> elevator.moveToFloor(8));
            floorButtons.add(button8);
            add(button8);

            JButton button5 = new JButton();
            button5.setIcon(loadButtonIcon("Resources/ElevatorButtons/5.jpg", 40, 40));
            button5.setBounds(15, 70, 50, 50);
            button5.setBackground(Color.LIGHT_GRAY);
            button5.addActionListener(e -> elevator.moveToFloor(5));
            floorButtons.add(button5);
            add(button5);
        
            JButton button6 = new JButton();
            button6.setIcon(loadButtonIcon("Resources/ElevatorButtons/6.jpg", 40, 40));
            button6.setBounds(85, 70, 50, 50);
            button6.setBackground(Color.LIGHT_GRAY);
            button6.addActionListener(e -> elevator.moveToFloor(6));
            floorButtons.add(button6);
            add(button6);

            JButton button3 = new JButton();
            button3.setIcon(loadButtonIcon("Resources/ElevatorButtons/3.jpg", 40, 40));
            button3.setBounds(15, 130, 50, 50);
            button3.setBackground(Color.LIGHT_GRAY);
            button3.addActionListener(e -> elevator.moveToFloor(3));
            floorButtons.add(button3);
            add(button3);

            JButton button4 = new JButton();
            button4.setIcon(loadButtonIcon("Resources/ElevatorButtons/4.jpg", 40, 40));
            button4.setBounds(85, 130, 50, 50);
            button4.setBackground(Color.LIGHT_GRAY);
            button4.addActionListener(e -> elevator.moveToFloor(4));
            floorButtons.add(button4);
            add(button4);
        
            JButton button1 = new JButton();
            button1.setIcon(loadButtonIcon("Resources/ElevatorButtons/1.jpg", 40, 40));
            button1.setBounds(15, 190, 50, 50);
            button1.setBackground(Color.LIGHT_GRAY);
            button1.addActionListener(e -> elevator.moveToFloor(1));
            floorButtons.add(button1);
            add(button1);
            
            JButton button2 = new JButton();
            button2.setIcon(loadButtonIcon("Resources/ElevatorButtons/2.jpg", 40, 40));
            button2.setBounds(85, 190, 50, 50);
            button2.setBackground(Color.LIGHT_GRAY);
            button2.addActionListener(e -> elevator.moveToFloor(2));
            floorButtons.add(button2);
            add(button2);

            JButton buttonFIRE = new JButton("FIRE");
            buttonFIRE.setBounds(25, 250, 100, 30);
            buttonFIRE.setBackground(Color.RED);
            buttonFIRE.setForeground(Color.WHITE);
            buttonFIRE.addActionListener(e -> elevator.emergency(true));
            add(buttonFIRE);

    }

    //Loads teh images for teh button
    private ImageIcon loadButtonIcon(String path, int width, int height) {
        ImageIcon rawIcon = new ImageIcon(path);
        Image scaled = rawIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    //Disableing buttons except the emergency button to prevent spamming
    //Nobody wants to be trapped in the elevator with Buddy The Elf
    public void setButtonsEnabled(boolean enabled) {    
        for (JButton button : floorButtons) {
            button.setEnabled(enabled);
        }
    }


    
}
