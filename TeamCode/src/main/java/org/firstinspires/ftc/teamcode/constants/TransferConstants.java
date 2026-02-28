package org.firstinspires.ftc.teamcode.constants;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class TransferConstants {

    public static final String kSubsystemName = "Transfer";

    public static final String kBlockerServoID = "blockServo";
    public static final String kKickerServoID = "KickServo";

    public static final String kFirstColorSensorID = "fCs";
    public static final String kSecondColorSensorID = "sCs";

    public static double kBlockerClosed = 0;
    public static double kBlockerOpenPosition = 1;
    public static double kKickerIdlePosition = 0.84;

    public static double kBlockerAllowPosition = 0;
    public static double kKickerActivePosition = 0;
}
