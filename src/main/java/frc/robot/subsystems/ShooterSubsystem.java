package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// bruh
// why is this not working
public class ShooterSubsystem extends SubsystemBase {
    public enum ShooterStates {
        IDLING,
        SHOOTING,
        EJECTING
    }
    private ShooterStates state = ShooterStates.IDLING; 

    private CANSparkMax motor;
    private FlywheelSim motorSim;

    private final int MOTOR_ID = 20;

    public ShooterSubsystem() {
        motor = new CANSparkMax(MOTOR_ID, MotorType.kBrushless);
        motorSim = new FlywheelSim(
            DCMotor.getNeo550(1),
            1,
            .001
        );
    }

    @Override
    public void periodic() {
        switch (state) {
            case IDLING -> {
                motor.setVoltage(0);
            }
            case SHOOTING -> {
                motor.setVoltage(12);
            }
            case EJECTING -> {
                motor.setVoltage(-12);
            }
        }

        SmartDashboard.putNumber("shooter motor speed", motor.get());
        SmartDashboard.putString("shooter state", state.toString());
    }

    public void setState(ShooterStates state) {
        this.state = state;
    }
}
