
import pro.beam.interactive.event.EventListener;
import pro.beam.interactive.net.packet.Protocol;

import java.awt.*;

// MouseMoveListener is responsible for listening to Report messages and handling them.
public class MouseMoveListener implements EventListener<Protocol.Report> {
    // This Robot is an AWT Robot, not a Beam one. It knows how to move the mouse across the screen.
    protected Robot mouse;

    // Basic no-args constructor, nothing special here...
    public MouseMoveListener() {
        try {
            this.mouse = new Robot();
        } catch (AWTException ignored) { }
    }

    // The handler method. This method is invoked when a new Report message is received over the wire.
    // It takes some information given in the Report message and does something with it (in this case,
    // it moves the mouse across the screen.
    @Override public void handle(Protocol.Report report) {
        // Grab the joystick for each both axes
        Protocol.Report.JoystickInfo joystick = report.getJoystick(0);

        // Move the mouse to the calculated mean of each of the individual axis respectively.
        Protocol.Coordinate coordMean = report.getJoystick(0).getCoordMean();
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        if (!Double.isNaN(coordMean.getX()) && !Double.isNaN(coordMean.getY())) {
            mouse.mouseMove(
                    ((int) (mousePosition.getX() + (300 * coordMean.getX()))),
                    ((int) (mousePosition.getY() + (300 * coordMean.getY())))
            );

            System.out.println("Mean JoyCoord Coordinates X: " + coordMean.getX());
            System.out.println("Mean JoyCoord Coordinates Y: " + coordMean.getY());
        }
    }
}