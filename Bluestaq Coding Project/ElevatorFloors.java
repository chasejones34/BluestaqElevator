import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.*;


public class ElevatorFloors extends JPanel{

    private Image buttonPanel;

    public ElevatorFloors(){
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        //Grabbing the button panel image
        try {
            buttonPanel = new ImageIcon(getClass().getResource("Resources/ButtonPanel.jpg")).getImage();
            } catch (Exception e) {
            System.out.println("Could not load carriage image.");
            }
    }
    
    @Override   //
    public void paintComponent(Graphics g) {    //
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //grabbing the background image for the button pannel
        //if it cant grab the inmage, a gray rectangle will be drawn at that point instead
        if (buttonPanel != null) {
            g2.drawImage(buttonPanel, 700, 250, 150, 300, this);
        } else {
            g2.setColor(Color.GRAY);  // fallback if image not loaded
            g2.fillRect(700, 250, 150,300);
        }
        
        //Set color of the frame
        g2.setColor(Color.GRAY);
        
        //Draw rectangle (x, y, width, height)
        //Drawing the frame of the "building"
        g2.drawRect(400, 50, 200, 800);     // outline
        g2.drawLine(400, 150, 600, 150);        //Top of floor 7
        g2.drawLine(400, 250, 600, 250);        //Top of floor 6
        g2.drawLine(400, 350, 600, 350);        //Top of floor 5
        g2.drawLine(400, 450, 600, 450);        //Top of floor 4
        g2.drawLine(400, 550, 600, 550);        //Top of floor 3
        g2.drawLine(400, 650, 600, 650);        //Top of floor 2
        g2.drawLine(400, 750, 600, 750);        //Top of floor 1
        
        
        g2.setFont(g.getFont().deriveFont(24f)); //Font for numbers

        int floorHeight = 100;
        int floorWidth = 200;
        int startX = 400;
        int startY = 50;

        for (int i = 0; i < 8; i++) {   //Using a for loop to draw the rectangles that correspond with the floors
            int y = startY + i * floorHeight;

            //Draw floor box
            g2.drawRect(startX, y, floorWidth, floorHeight - 10);
            g2.fillRect(startX + 1, y + 1, floorWidth - 2, floorHeight - 12);

            //Draw floor number to the left of each floor
            g2.setColor(Color.BLACK);
            g2.drawString("[Floor " + (8 - i) +"]", startX - 100, y + 50);  //Drawing the numbers on the canvas from the top down

            g2.setColor(Color.GRAY); //Reset for next floor
        }
    }

}
