package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Subsystems.Vision;

@Config
@Disabled
@Autonomous(group="!CompOpModes")
public class BlueScoringSide extends AutoOpMode {
    private Vision vision;
    private Vision.IDENTIFIED_SPIKE_MARK_LOCATION spikeLocation;
    public static Position START = new Position(12, 63.375, 270);
    public static Position START_CENTER = new Position(12,60,270);
    public static Position SPIKE_RIGHT = new Position(12, 36, 270);

    public static Position SPIKE_RIGHT_TURN = new Position(12, 36, 195);
    public static Position SPIKE_RIGHT_BACKUP = new Position(12, 44, 195);
    public static Position SPIKE_RIGHT_ADJUST_HEADING = new Position(12, 44, 270);
    public static Position SPIKE_CENTER = new Position(12, 36, 270);
    public static Position SPIKE_CENTER_BACKUP = new Position(12, 44, 270);


    public static Position SPIKE_LEFT = new Position(23, 44.625, 270);
    public static Position SPIKE_LEFT_BACKUP = new Position(23, 52, 270);

    public static Position AFTER_SPIKE_POSITION = new Position(24, 52, 270);

    public static Position AFTER_SPIKE_TURN = new Position(24, 52, 180);

    public static Position DROP_START_INTERMEDIATE = new Position(44, 52, 180);

    public static Position DROP_PARK_INTERMEDIATE = new Position(44, 60, 180);
    public static Position DROP_POSITION = new Position(44, 36, 180);
    public static Position DROP_POSITION_TOUCH_BOARD = new Position(52, 36, 180);
    public static Position DROP_POSITION_BACKUP_BOARD = new Position(44, 36, 180);


    public static Position DROP_POSITION_R = new Position(44, 28, 180);
    public static Position DROP_POSITION_TOUCH_BOARD_R = new Position(52, 28, 180);
    public static Position DROP_POSITION_BACKUP_BOARD_R = new Position(44, 28, 180);



    public static Position DROP_POSITION_L = new Position(44, 44, 180);
    public static Position DROP_POSITION_TOUCH_BOARD_L = new Position(52, 44, 180);
    public static Position DROP_POSITION_BACKUP_BOARD_L = new Position(44, 44, 180);

    public static Position PARK_POSITION = new Position(60, 60, 180);

    @Override
    public void runOpMode() {
        vision = new Vision(this, Vision.START_POSITION.BLUE_SCOREING);
        vision.initTfod();



        while (!isStopRequested() && !opModeIsActive()) {
            //Run Vuforia Tensor Flow and keep watching for the identifier in the Signal Cone.
            vision.runTfodTensorFlow();
            telemetry.addData("Vision identified Parking Location", vision.getPixelLocation());
            telemetry.update();
        }
        spikeLocation = vision.getPixelLocation();
        //vision.Stop();
        setup();
        setStartPosition(START);
        switch (spikeLocation) {   // camer detection value goes herre
            case RIGHT:
                goTo(SPIKE_RIGHT);
                goTo(SPIKE_RIGHT_TURN);
                intake.returnPixel();
                sleep(2000);
                goTo(SPIKE_RIGHT_BACKUP);
                goTo(SPIKE_RIGHT_ADJUST_HEADING);
                coneDrop(3);

                break;
            case MIDDLE:
                goTo(SPIKE_CENTER);
                intake.returnPixel();
                sleep(2000);
                goTo(SPIKE_CENTER_BACKUP);
                coneDrop(2);
                break;
            case LEFT:
                goTo(SPIKE_LEFT);
                intake.returnPixel();
                sleep(2000);
                goTo(SPIKE_LEFT_BACKUP);
                coneDrop(1);
        }
    }
    public void coneDrop(int casee) {
        intake.stop();
        goTo(AFTER_SPIKE_POSITION);
        goTo(AFTER_SPIKE_TURN);
        goTo(DROP_START_INTERMEDIATE);



        if(casee==1){
            goTo(DROP_POSITION_L);
            drop.goToPosition(3);
            setSpeed(Speed.SLOW);
            goTo(DROP_POSITION_TOUCH_BOARD_L);
            sleep(1000);
            topGate.setGateOpen();
            telemetry.update();
            sleep(1000);
            topGate.setGateStopped();
            goTo(DROP_POSITION_BACKUP_BOARD_L);
        }
        if(casee==2){
            goTo(DROP_POSITION);
            drop.goToPosition(3);
            setSpeed(Speed.SLOW);
            goTo(DROP_POSITION_TOUCH_BOARD);
            sleep(1000);
            topGate.setGateOpen();
            telemetry.update();
            sleep(1000);
            topGate.setGateStopped();
            goTo(DROP_POSITION_BACKUP_BOARD);
        }
        if(casee==3){
            goTo(DROP_POSITION_R);
            drop.goToPosition(3);
            setSpeed(Speed.SLOW);
            goTo(DROP_POSITION_TOUCH_BOARD_R);
            sleep(1000);
            topGate.setGateOpen();
            telemetry.update();
            sleep(1000);
            topGate.setGateStopped();
            goTo(DROP_POSITION_BACKUP_BOARD_R);
        }




        setSpeed(Speed.MEDIUM);
        drop.goToBottom();
        sleep(1000);
        goTo(DROP_PARK_INTERMEDIATE);
        goTo(PARK_POSITION);
        sleep(10000);

    }
}

