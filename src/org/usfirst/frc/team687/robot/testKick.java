
package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class testKick extends IterativeRobot {
	Joystick joy;
	Kicker kick;
	DoubleSolenoid release;
	Encoder encode;
	CANTalon kicker;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	joy = new Joystick(0);
    	release = new DoubleSolenoid(1,2);
    	encode = new Encoder(1,2);
    	kicker = new CANTalon(1);
    	kick = new Kicker(release,encode,kicker);
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if(joy.getRawButton(1)){
        	kick.kickWind();
        }
        if(joy.getRawButton(2)){
        	kick.wind();
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
