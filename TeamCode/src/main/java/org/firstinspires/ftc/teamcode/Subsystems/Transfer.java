package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.library.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.constants.TransferConstants;

public class Transfer extends SubsystemBase {

    private Servo blockerServo;
    private Servo kickerServo;

    private RevColorSensorV3 firstColorSensor;
    private RevColorSensorV3 secondColorSensor;

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    public Transfer(HardwareMap hMap, TelemetryManager telemetryM) {
        blockerServo = hMap.get(Servo.class, TransferConstants.kBlockerServoID);
        kickerServo = hMap.get(Servo.class, TransferConstants.kKickerServoID);

        firstColorSensor = hMap.get(RevColorSensorV3.class, TransferConstants.kFirstColorSensorID);
        secondColorSensor = hMap.get(RevColorSensorV3.class, TransferConstants.kSecondColorSensorID);
        firstColorSensor.enableLed(true);
        secondColorSensor.enableLed(true);

        this.telemetryM = telemetryM;

    }

    @Override
    public void periodic() {
        telemetryM.addData(TransferConstants.kSubsystemName + "fBB Distance", firstCSDistance());
        telemetryM.addData(TransferConstants.kSubsystemName + "sBB Distance", secondCSDistance());
        telemetryM.addData(TransferConstants.kSubsystemName + "Blocker Position", blockerServo.getPosition());
        telemetryM.addData(TransferConstants.kSubsystemName + "Kicker Position", kickerServo.getPosition());
    }

    public void onInitialization(boolean initKicker, boolean initBlocker) {
        if (initKicker) kickerServo.setPosition(TransferConstants.kKickerIdlePosition);
        if (initBlocker) blockerServo.setPosition(TransferConstants.kBlockerClosed);
    }

    public void setKickerPosition(double position) {
        telemetryM.addData(TransferConstants.kSubsystemName + "Kicker Target Position", position);
        telemetryM.addData(TransferConstants.kSubsystemName + "Kicker Current Position", TransferConstants.kKickerActivePosition);
        kickerServo.setPosition(position);
    }
    public void setBlockerPosition(double position) {
        telemetryM.addData(TransferConstants.kSubsystemName + "Blocker Open Position", position);
        telemetryM.addData(TransferConstants.kSubsystemName + "Blocker Allow Position", TransferConstants.kBlockerAllowPosition);
        blockerServo.setPosition(position);
    }

    public double firstCSDistance(){return firstColorSensor.getDistance(DistanceUnit.INCH);}
    public double secondCSDistance(){return secondColorSensor.getDistance(DistanceUnit.INCH);}

}
