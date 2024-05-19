import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

@ScriptManifest(name = "KacyBaseMeleeAIO", author = "kacy", version = 1.0, info = "Creates base melee accounts from scratch!", logo = "")

public class KacyBaseMeleeAIO extends Script {

    @Override
    public void onStart() {

    }

    @Override
    public void onExit() {
        log("");

    }

    @Override
    public int onLoop() throws InterruptedException {

        return 1000; //The amount of time in milliseconds before the loop starts over
    }

    private void bank() throws InterruptedException {

    }

    private void pickPotato() throws InterruptedException {

    }

    private boolean potatoStocked() {
        return getInventory().isFull();
    }

    @Override
    public void onPaint(Graphics2D g) {

    }

}