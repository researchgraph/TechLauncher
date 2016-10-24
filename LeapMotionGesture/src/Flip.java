import com.leapmotion.leap.*;

import java.io.IOException;

/**
 * Created by wangkun on 24/10/2016.
 */


class FlipListener extends Listener{

    private int mark = 1;
    private int count = 0;


    public void onInit(Controller contorller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Ready to detect Flip");
        //Initialize the Circle gesture
//        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
//        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
//        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
//        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Motion Sensor Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        //GestureList gestures = frame.gestures();


        for(Hand hand: frame.hands()){
            String handType = hand.isLeft()?"Left hand": "Right hand";

            Vector normal = hand.palmNormal();

            float status = normal.getY();

            if (status * mark > 0) {
                mark *= -1;
                count++;
            }

            if (count == 2) {
                System.out.println("Catching your flip!");
                count = 0;
            }

            //mark = "111";

            //System.out.println(normal.getY()>0);

        }




    }
}





public class Flip {
    public static void main(String[] args) {
        float mark = 1;
        FlipListener listener = new FlipListener();
        Controller controller = new Controller();
        controller.addListener(listener);

        System.out.println("Press enter to quit");

        try {
            System.in.read();
        } catch(IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);
    }
}
