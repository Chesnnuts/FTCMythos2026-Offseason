package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.library.command.SubsystemBase;
import org.firstinspires.ftc.library.hardware.motors.Motor;
import org.firstinspires.ftc.library.hardware.motors.MotorEx;
import org.firstinspires.ftc.library.math.geometry.Pose2d;
import org.firstinspires.ftc.library.math.geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {

    private MotorEx rightFront;

    private MotorEx leftFront;

    private MotorEx rightRear;

    private MotorEx leftRear;

    private IMU imu;

    private final Follower follower;

    @IgnoreConfigurable

    static TelemetryManager telemetryM;

    public Drivetrain(HardwareMap hMap, TelemetryManager telemetryM) {

        rightFront = hMap.get(MotorEx.class, DrivetrainConstants.fRMotorID);
        leftFront = hMap.get(MotorEx.class, DrivetrainConstants.fLMotorID);
        rightRear = hMap.get(MotorEx.class, DrivetrainConstants.bRMotorID);
        leftRear = hMap.get(MotorEx.class, DrivetrainConstants.bLMotorID);

        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        rightFront.setRunMode(Motor.RunMode.RawPower);
        leftFront.setRunMode(Motor.RunMode.RawPower);
        rightRear.setRunMode(Motor.RunMode.RawPower);
        leftRear.setRunMode(Motor.RunMode.RawPower);

        rightFront.setInverted(true);
        leftFront.setInverted(false);
        rightRear.setInverted(true);
        leftRear.setInverted(true);

        follower = new Follower(hMap);

        initializeImu(hMap);
        this.telemetryM = telemetryM;


    }

        public void initializeImu(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        ));

        imu.initialize(parameters);
        }

        public Pose2d getPose() {
            return new Pose2d(follower.getPose().getX(), follower.getPose().getY(), Rotation2d.fromRadians(follower.getPose().getHeading()));
        }

        @Override
    public void periodic() {
            telemetryM.addData(DrivetrainConstants.kSubsystemName + "Pose X", getPose().getX());
            telemetryM.addData(DrivetrainConstants.kSubsystemName + "Pose Y", getPose().getY());
            telemetryM.addData(DrivetrainConstants.kSubsystemName + "Pose 0", getPose().getRotation());
        }

        public void drive(double xSpeedInchesPerSecond, double ySpeedInchesPerSecond, double omegaSpeedRadiansPerSecond){
            double scaledForward = xSpeedInchesPerSecond / DrivetrainConstants.kMaximumLinearVelocityInchesPerSecond;
            double scaledStrafe = ySpeedInchesPerSecond / DrivetrainConstants.kMaximumLinearVelocityInchesPerSecond;
            double scaledRotation = omegaSpeedRadiansPerSecond / DrivetrainConstants.kMaximumRotationRadiansPerSecond;

            double[] chassisSpeeds = new double[] {
                    (scaledForward - scaledRotation - scaledStrafe),
                    (scaledForward + scaledRotation + scaledStrafe),
                    (scaledForward - scaledRotation + scaledStrafe),
                    (scaledForward + scaledRotation - scaledStrafe)
            };

            rightFront.set(chassisSpeeds[0]);
            leftFront.set(chassisSpeeds[1]);
            rightRear.set(chassisSpeeds[2]);
            leftRear.set(chassisSpeeds[3]);
        }

        public void startTeleopDriving(){follower.startTeleopDrive(true);}

        public void setMaxPower(final double maxPower){follower.setMaxPower(maxPower);}

        public void setMovementVectors(double strafe, double forward, double rotation, boolean isRobotCentric){
            follower.setTeleOpDrive(strafe, forward, rotation, isRobotCentric);
        }

        public void followTrajectory(final PathChain pathChain, final boolean holdEnd){
            follower.followPath(pathChain, holdEnd);
        }

        public void resetDriveSpeed() {
        follower.setTeleOpDrive(0, 0, 0, false);
        drive(0, 0, 0);
        }

        public void resetPose(Pose2d pose){follower.setPose(pose.getAsPedroPose());}

        public void setPose(Pose2d pose){follower.setPose(pose.getAsPedroPose());}

        public void setStartingPose(Pose pose){
        follower.setStartingPose(pose);
        setPose(new Pose2d(pose.getX(), pose.getY(), Rotation2d.fromRadians(pose.getHeading())));
        }

        public void resetHeading(){imu.resetYaw();}

        public void update(){follower.update();}

        public boolean isFollowingTrajectory(){return follower.isBusy();}

}
