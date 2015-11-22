
package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Kicker {

	private DoubleSolenoid m_release;
	private Encoder m_kickEncode;
	private CANTalon m_kicker;
	private boolean m_kickWind = false;
	private boolean m_wind = false;
	private boolean m_kick = false;
	private double m_kP = 0;
	private double m_pwr;
	private double m_error;
	private double m_actual;
	private double m_hardStop = 65;
	private final double m_desired = 38;
	
	public Kicker(DoubleSolenoid piston, Encoder encode, CANTalon motor){
		m_release = piston;
		m_kickEncode = encode;
		m_kicker = motor;
	}
	// winds surgical tubing then releases kicker
	public void kickWind(){
		m_kickWind = true;
		m_kick = false;
		m_wind = false;
	}
	// winds surgical tubing
	public void wind(){
		m_wind = true;
		m_kick = false;
		m_kickWind = false;
	}
	// releases kicker
	public void kick(){
		m_kick = true;
		m_wind = false;
		m_kickWind = false;
	}
	//disables all functions
	public void allStop(){
		m_kick = false;
		m_wind = false;
		m_kickWind = false;
	}
	
	public void run()	{
		m_actual = m_kickEncode.getRaw();
		// PID for kicker
		m_error = m_actual - m_desired;
        m_pwr= m_error * m_kP;
        // threshold for winding power
		if(m_pwr >.2){
			m_pwr = .2;
		}else if(m_pwr < -.2){
			m_pwr = -.2;
		}
		// logic for kicking then winding surgical tubing
		if(m_kickWind){
		m_actual = m_kickEncode.getRaw();
		if(Math.abs(m_actual-m_desired) < 3){
			m_release.set(DoubleSolenoid.Value.kReverse);
			m_kicker.set(0);
		} else if(Math.abs(m_actual-m_hardStop) < 3){
			m_release.set(DoubleSolenoid.Value.kForward);
			m_kicker.set(m_pwr);	
		  }
		}
		// logic for winding the surgical tubing
		if(m_wind && !m_kick){
			m_release.set(DoubleSolenoid.Value.kForward);
			m_kicker.set(m_pwr);	
		}
		// logic for releasing the kicker
		if(m_kick && !m_wind){
			m_release.set(DoubleSolenoid.Value.kReverse);
			m_kicker.set(0);
		}
	}
  
}
