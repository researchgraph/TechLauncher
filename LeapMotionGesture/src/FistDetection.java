import java.io.IOException;

import com.leapmotion.leap.*;

public class FistDetection {
	public static void main(String[] args){
		//Add controller, serves as connection to the leap motion service/daemon
		SampleListener listener = new SampleListener();
		Controller controller = new Controller();
		controller.addListener(listener);
		
		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try{
			System.in.read();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//Remove the sample listener when done
		controller.removeListener(listener);
	}
}
class SampleListener extends Listener{
	public void onInit(Controller controller){
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller){
		System.out.println("Connected");
	}
	
	public void onDisconnect(Controller controller){
		System.out.println("Disconnected");
	}
	
	public void onExit(Controller controller){
		System.out.println("Exited");
	}
	public void onFrame(Controller controller){
		//Get the most recent frame and report some basic information
		Frame frame = controller.frame();
		
//		System.out.println("Frame id: " + frame.id()
//							+", timestamp: "+ frame.timestamp()
//							+", hands: "+ frame.hands().count()
//							+", fingers: "+ frame.fingers().count());
//		System.out.println("Frame available");
		
		//Get hands
		for(Hand hand: frame.hands()){
			String handType = hand.isLeft()?"Left hand": "Right hand";
//			System.out.println(" " + handType + ", id: " + hand.id()
//								+ ", palm position: "+ hand.palmPosition());
//			System.out.println(hand.sphereRadius());
			if(hand.sphereRadius()<50){
				System.out.println("Stop");
			}else{
				System.out.println("Running");
			}
			//Get the hand's normal vector and direction
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
//			//Calculate the hand's pitch, roll, and yaw angles
//			System.out.println("Pitch: " + Math.toDegrees(direction.pitch())+ " degrees, "
//								+"roll: " + Math.toDegrees(normal.roll()) + " degrees, "
//								+"yaw: " + Math.toDegrees(direction.yaw()) + " degrees");
			
//			//Get arm bone
//			Arm arm = hand.arm();
//			System.out.println("Arm direction: "+arm.direction()
//								+ ", wrist position: "+arm.wristPosition()
//								+", elbow position: "+ arm.elbowPosition());
//			
			//Get fingers
			for (Finger finger : hand.fingers()) {
//                System.out.println("    " + finger.type() + ", id: " + finger.id()
//                                 + ", length: " + finger.length()
//                                 + "mm, width: " + finger.width() + "mm");
               
//                for(Bone.Type boneType : Bone.Type.values()) {
//                    Bone bone = finger.bone(boneType);
//                    System.out.println("      " + bone.type()
//                                     + " bone, start: " + bone.prevJoint()
//                                     + ", end: " + bone.nextJoint()
//                                     + ", direction: " + bone.direction());
//                }
            }
        }

        if (!frame.hands().isEmpty()) {
            System.out.println();
        }
	}
}
