import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Carriage extends JPanel{
    private int x = 450;
    private int y = 750;
    private int currentY;
    private final int fixedX = 450; //current X stays the same

    private boolean isMoving = false;
    private Queue<Integer> floorQueue = new LinkedList<>();     //Stores the floors that are waiting to be visited 
                                                                //Using a Queue to preserve the order of requested floors

    private Set<Integer> queuedFloors = new HashSet<>();        //stores the floor numbers that are requested to ensure no duplicate requests
                                                                //Using a HashSet to ensure that there are no duplicates since sets cant have duplicates
    private int currentFloor = 1;

    volatile boolean callFireDept = false;
    private Timer activeTimer = null;

    private ButtonPanel controlPanel;

    private Firetruck truck;

    private Image closedDoors;
    private Image openDoors;


//================== SET UP FOR THE CAR ITSELF ===============================
    public Carriage(int x, int y){
        this.setLayout(null);
        this.x = x;
        this.y = y;
        this.currentY = 750;
        setBounds(fixedX, currentY, 100, 100);

        //loading in images for open and closed doors
        try {
            closedDoors = new ImageIcon(getClass().getResource("Resources/ClosedDoors.jpg")).getImage();
            openDoors = new ImageIcon(getClass().getResource("Resources/OpenDoors.jpg")).getImage();
            } catch (Exception e) {
            System.out.println("Could not load carriage image.");
            }
    }

    @Override   
    public void paintComponent(Graphics g) {    
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //using the isMoving boolean to figure out if the car is moving
        //if the car is moving, use the closed doors image
        //if the car is stationary, use open doors image
        if (!isMoving && openDoors != null) {
            g2d.drawImage(openDoors, 0, 0, 100, 100, this);
        } else if (closedDoors != null) {
            g2d.drawImage(closedDoors, 0, 0, 100, 100, this);
        } else {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(0, 0, 100, 100);
        }
        
    }
//=================== BEGINNING OF MOVEMENT LOGIC ==============================

    public void moveToFloor(int floor) {
        if (callFireDept) return;   //Check if emergency button has been pushed
        int targetY = getYForFloor(floor);
        isMoving = true;

        if (controlPanel != null) {
        controlPanel.setButtonsEnabled(false);  //disable buttons
        }

        activeTimer = new Timer(10, null);
        Timer thisTimer = activeTimer;

        thisTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        //Not using a lambda expression because its easier to follow 
                if (callFireDept || activeTimer != thisTimer) { //since theres a lot of if statements in this section
                    thisTimer.stop();
                    return;
                }

                int dy = targetY - currentY;

                if (Math.abs(dy) <= 5) {
                    currentY = targetY;
                    setLocation(fixedX, currentY);
                    currentFloor = floor;
                    isMoving = false;

                if (controlPanel != null) {
                        controlPanel.setButtonsEnabled(true); //Re-enable buttons since movement has finished
                }

                    thisTimer.stop();
                if (activeTimer == thisTimer) {
                    activeTimer = null;
                }

                if (!callFireDept) {
                    processNextFloor();      //if the fire button is not pushed, run the processNextFloor method
                }
                } else {    //move the car 5 pixels up when the currentY does not equal the moveY variable
                    int moveY = 5 * Integer.signum(dy);     //Integer.signum(dy) will return a -1 if the requested floor is "above" (lower y value)
                    currentY += moveY;                      //Integer.signum(dy) will return 1 if the requested floor is "below" (higher y value) 
                    setLocation(fixedX, currentY);
                }
            }   
        });

        thisTimer.start();
    }   

    private void processNextFloor() {
        if (callFireDept || floorQueue.isEmpty()) return;   //will end early if fire button is pressed or if there is no floor requests

        int nextFloor = floorQueue.poll();  //puklls from the queue in FIFO order
        queuedFloors.remove(nextFloor);     //removed floor number from HashSet so that it can be requested again
        moveToFloor(nextFloor);
    }

    
    private int getYForFloor(int floor) {
        //Assuming floor 1 (bottom) is Y=750, floor 2 is 650, ..., floor 8 is 50
        return 850 - (floor * 100);  //850 - 100 * floorNum
    }


    public void requestFloor(int floor) {
        if (floor == currentFloor || queuedFloors.contains(floor)) {    //checking if the floor requested is not already in the Queue, or that it isnt the curent floor the elevator is on
            return;
        }
    
        queuedFloors.add(floor);    //adding floor to Set
        floorQueue.offer(floor);    //adding floor to Queue
    
        if (!isMoving && activeTimer == null && !callFireDept) {    //If the elevator is not moving and the fire button has not been pressed, run processNextFLoorMethod
            processNextFloor();
        }
    }

    //============= EMERGENCY BUTTON CODE ================================
    public void emergency(boolean callFireDept){
        this.callFireDept = true;
        if (activeTimer != null) {
            activeTimer.stop();           //stop animation
            activeTimer = null;          //clear reference
        }
    
        isMoving = false;                //mark as idle
    
        if (callFireDept) {
            floorQueue.clear();          //clear queue if needed
        }
    
        //Optional: visually indicate stop (like changing color)
        repaint();


        Timer resumeTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToGround(1);
            }
        });
        resumeTimer.setRepeats(false);         //only run once
        resumeTimer.start();

        if (truck != null) {
            truck.animateIn();  //will only call if it's been set
        }
    }


    public void returnToGround(int floor) {
        if (!callFireDept) {
            return;         //Only allow resume from emergency
        }
    
        System.out.println("Resuming elevator to floor " + floor);
    
        callFireDept = false;       //lift emergency lock
        floorQueue.clear();         //clear old requests
        isMoving = false;
        moveToFloor(floor);         //go to desired floor

        
    }

    public void setFiretruck(Firetruck truck) {     //Setter method to initialize the firetruck
        this.truck = truck;
    }

    //=================== HELP WITH DISABLING THE BUTTONS ===========================================
    public void setControlPanel(ButtonPanel panel) {
        this.controlPanel = panel;
    }

}

