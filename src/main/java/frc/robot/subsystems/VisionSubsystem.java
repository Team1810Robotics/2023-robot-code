package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {

    private final NetworkTable table;

    public VisionSubsystem() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    /** Checks if the limelight has a valid target */
    public boolean targetValid() {
        return table.getEntry("tv").getDouble(0) == 1;
    }

    /** gets the horizontal offset of the target */
    public double targetXOffset() {
        return table.getEntry("tx").getDouble(0);
    }

    /** gets the vertical offset of the target */
    public double targetYOffset() {
        return table.getEntry("ty").getDouble(0);
    }

    public long apriltagID() {
        return table.getEntry("tid").getInteger(0);
    }

    public double getTimestamp() {
        return table.getEntry("ts").getDouble(0);
    }

    public static Pose3d botpose() {
        double p[] = NetworkTableInstance.getDefault().getTable("limelight")
                        .getEntry("botpose").getDoubleArray(new double[] {0,0,0, 0,0,0});
        var t3d = new Translation3d(p[0], p[1], p[2]);
        var r3d = new Rotation3d(p[3], p[4], p[5]);

        return new Pose3d(t3d, r3d);
    }
}