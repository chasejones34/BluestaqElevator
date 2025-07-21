import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Firetruck extends JPanel {
    private int x = 1000;  //start offscreen to the right
    private final int y = 800;  //fixed vertical position

    private Image firetruck;

    public Firetruck() {
        setOpaque(false);
        setBounds(x, y, 200, 200);  //Size of firetruck image

        //Checks if image can be loaded
        try {
            firetruck = new ImageIcon(getClass().getResource("Resources/FireTruck.jpg")).getImage();
            } catch (Exception e) {
            System.out.println("Could not load carriage image.");
            }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Use firetruck image, else create a firetruck using the paintComponent
        if (firetruck != null) {
            g2d.drawImage(firetruck, 0, 0, 200, 100, this);
        } else {
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 20, 150, 50);  //firetruck body

        //Wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval(20, 60, 20, 20);
        g2d.fillOval(110, 60, 20, 20);

        //Lights
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(10, 10, 30, 20);

        //Label: "FIRE DEPT"
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("FIRE DEPT", 40, 45);  //(x, y) = label position
        }
    }

    //Animates the firetruck coming on screen
    public void animateIn() {
        Timer enterTimer = new Timer(10, null);
        enterTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > 700) {       //700 = the stopping place of the firetruck   
                    x -= 5;             
                    setLocation(x, y);
                } else {
                    ((Timer) e.getSource()).stop();
    
                    // Wait 3 seconds, then exit
                    Timer waitTimer = new Timer(5000, null);
                    waitTimer.setRepeats(false);
                    waitTimer.addActionListener(ev -> animateOut());
                    waitTimer.start();
                }
            }
        });
        enterTimer.start();
    }

    //Animates teh firetruck coming off screen
    public void animateOut() {
        Timer exitTimer = new Timer(10, null);
        exitTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x < 1100) {     //insure that is fully off screen
                    x += 5;
                    setLocation(x, y);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        exitTimer.start();
    }
}