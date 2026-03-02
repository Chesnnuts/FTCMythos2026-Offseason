package org.firstinspires.ftc.teamcode.constants;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class IntakeConstants {

    public static final String kSubsystemName = "Intake";

    // Motor Names

    public static final String kFirstIntakeMotorID = "firstIntakeMotor";
    public static final String kSecondIntakeMotorID = "secondIntakeMotor";

    // Motor Specifications
    public static final double firstIntakeMotorRPM = 312;
    public static final double secondIntakeMotorRPM = 435;


    // Motor Power
    public static final double intakingPower = 1;
    public static final double outtakingPower = -1;
    public static final double haltedPower = 0;
}
