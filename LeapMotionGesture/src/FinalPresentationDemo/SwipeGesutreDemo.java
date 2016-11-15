import java.io.IOException;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class MyListener extends Listener {
	public void onInit(Controller contorller) {
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller) {
		System.out.println("Connect to Motion Sensor");
		//Initialize the Circle gesture
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	public void onDisconnect(Controller controller) {
		System.out.println("Motion Sensor Disconnected");
	}
	
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	public void onFrame(Controller controller) {
		Frame frame = controller.frame(0);
		
		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);
			
			switch (gesture.type()) {
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);
					
					String clockwiseness;
					if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
						clockwiseness = "clockwise";
					} else {
						clockwiseness = "counter-clockwise";
					}
					
					double sweptAngle = 0;
					if (circle.state() != State.STATE_START) {
						CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circle.id()));
						sweptAngle = (circle.progress() - previous.progress()) * 2 * Math.PI;
					}
				
					System.out.println( "You draw a " + clockwiseness + " cicrle");
					break;
					
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
//					System.out.println("Swipe ID: " + swipe.id()
//									+ " State: " + swipe.state()
//									+ " Swipe Position "  + swipe.position()
//									+ " Direction: " + swipe.direction()
//									+ " Speed " + swipe.speed());
					if(swipe.direction().getX()>0){System.out.println("swipe to Right");}
					else{
						System.out.println("swipe to left");
					}
					break;

			}
		}
	}
}



public class SwipeGesutreDemo {
	public static void main(String[] args) {
		MyListener listener = new MyListener();
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
