package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.library.command.SubsystemBase;
import org.firstinspires.ftc.library.hardware.motors.MotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private MotorEx firstIntakeMotor;
    private MotorEx secondIntakeMotor;
    private TelemetryManager telemetryManager;


    @IgnoreConfigurable
    public Intake(HardwareMap hMap, TelemetryManager telemetryManager)     {

    firstIntakeMotor = hMap.get(MotorEx.class, IntakeConstants.firstIntakeMotorName);

    secondIntakeMotor = hMap.get(MotorEx.class, IntakeConstants.secondIntakeMotorName);

    firstIntakeMotor = new MotorEx (hMap, IntakeConstants.firstIntakeMotorName);

    secondIntakeMotor = new MotorEx (hMap, IntakeConstants.secondIntakeMotorName);

// Ask @ultimate_hecker this Question ^^ both instantations works properly but which one would you recommened me using

    this.telemetryManager = telemetryManager;
}

@Override
public void periodic() {
    telemetryManager.addData(" First Intake Running", firstIntakeMotor.getRawPower() != 0 ? "Yes" : "No");
    telemetryManager.update();

    telemetryManager.addData(" Second Intake Running", secondIntakeMotor.getRawPower() != 0 ? "Yes" : "No");
    telemetryManager.update();

    // @utlimate_hecker should I keep the telemetryManager.update(); at the very end of the periodic or should I keep it in between
    // Im assuming I should just do it one
}


    // @ultimate_hecker When trying to do .setPower it wont work till you add .motor
    // Do you possibly know why this is and how to fix it. Im thinking that its because of the MotorEx instead of DcMotor
public void intake ()   {
    firstIntakeMotor.motor.setPower(IntakeConstants.intakingPower);
    secondIntakeMotor.motor.setPower(IntakeConstants.intakingPower);

}
public void outake ()   {
    firstIntakeMotor.motor.setPower(IntakeConstants.outtakingPower);
    secondIntakeMotor.motor.setPower(IntakeConstants.outtakingPower);
}
public void halt ()   {
    firstIntakeMotor.motor.setPower(IntakeConstants.haltedPower);
    secondIntakeMotor.motor.setPower(IntakeConstants.haltedPower);
}

}

