package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// really useful comment

public class PickerSubsystem extends SubsystemBase {
    private CANSparkMax oneMotor;
    private DigitalInput oneSensor;

    private FlywheelSim oneMotorSim;

    public enum PickerStates {
        PICKING,
        IDLING,
        EJECTING
    }
    private PickerStates newState = PickerStates.IDLING;

    public PickerSubsystem() {
        oneMotor = new CANSparkMax(15, MotorType.kBrushless);
        oneSensor = new DigitalInput(7);
        oneMotorSim = new FlywheelSim(
            DCMotor.getNeo550(1), 
            1, 
            .0001
        );
    }

    @Override
    public void periodic() {
        switch (newState) {
            case PICKING -> {
                oneMotor.setVoltage(12);
                oneMotorSim.setInput(12);
                if (!oneSensor.get()) {
                    newState = PickerStates.IDLING;
                }
            }
            case IDLING -> {
                oneMotorSim.setInput(0);
                oneMotor.setVoltage(0);
            }
            case EJECTING -> {
                oneMotorSim.setInput(-12);
                oneMotor.setVoltage(-12);
            }
        }

        SmartDashboard.putNumber("motor speed", oneMotor.get());
        SmartDashboard.putString("picker state", newState.toString());
    }

    @Override
    public void simulationPeriodic() {
        oneMotorSim.update(.02);
        SmartDashboard.putNumber("Picker/Speed", oneMotorSim.getAngularVelocityRPM());
    }

    public void setState(PickerStates state) {
        newState = state;
    }
}
