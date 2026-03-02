package org.firstinspires.ftc.teamcode.Subsystems;

import android.health.connect.datatypes.units.Power;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.library.command.Command;
import org.firstinspires.ftc.library.command.CommandBase;
import org.firstinspires.ftc.library.command.SubsystemBase;
import org.firstinspires.ftc.library.hardware.motors.MotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private MotorEx firstIntakeMotor;
    private MotorEx secondIntakeMotor;

    @IgnoreConfigurable

    private TelemetryManager telemetryManager;

    public Intake(HardwareMap hMap, TelemetryManager telemetryManager)     {

    firstIntakeMotor = new MotorEx (hMap, IntakeConstants.kFirstIntakeMotorID);

    secondIntakeMotor = new MotorEx (hMap, IntakeConstants.kSecondIntakeMotorID);

    this.telemetryManager = telemetryManager;
}

@Override
public void periodic() {
    telemetryManager.addData(" First Intake Running", firstIntakeMotor.getRawPower() != 0 ? "Yes" : "No");

    telemetryManager.addData(" Second Intake Running", secondIntakeMotor.getRawPower() != 0 ? "Yes" : "No");

    telemetryManager.update();

}
private void setPower(double power) {
    firstIntakeMotor.set(power);
    secondIntakeMotor.set(power);
}

public Command intake (){
    return runOnce(() -> setPower(IntakeConstants.intakingPower));
}

public Command outtake (){
    return runOnce(() -> setPower(IntakeConstants.outtakingPower));
}

public Command halt(){
    return runOnce(() -> setPower(IntakeConstants.haltedPower));
}
}


