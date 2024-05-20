import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.Arrays;

@ScriptManifest(name = "KacyBaseMeleeAIO", author = "kacy", version = 1.0, info = "Creates base melee accounts from scratch!", logo = "")

public class KacyBaseMeleeAIO extends Script {

    Area potatoField = new Area(3140, 3293, 3155, 3270);
    int potatoMaxQty = 0;
    int currentPotatoAmt = 0;

    @Override
    public void onStart() {
        log("Let's make some accounts!");
    }

    @Override
    public void onExit() {
        log("Script stopped. Goodbye!");

    }

    @Override
    public int onLoop() throws InterruptedException {

        return 1000; //The amount of time in milliseconds before the loop starts over
    }

    private boolean doorIsClosed(RS2Object door) throws InterruptedException {
        return Arrays.asList(door.getDefinition().getActions()).contains("Open");
    }

    private boolean atPotatoField() throws InterruptedException {
        return myPosition().equals(potatoField);
    }

    private void openGate() throws  InterruptedException {
        RS2Object gate = objects.closest("Gate");
        if(gate != null && doorIsClosed(gate)) {
            gate.interact("Open");
        }
    }

    private void pickPotatoes() throws InterruptedException {
        log("Picking potato...");
        RS2Object potato = getObjects().closest("Potato");
        if(potato != null) {
            potato.interact("Pick");
            currentPotatoAmt ++;
        }
    }

    private boolean atBank() {
        return myPosition().equals(Banks.DRAYNOR);
    }

    private void walkToBank() {
        getWalking().webWalk(Banks.DRAYNOR);
    }

    private void bankPotatoes() throws InterruptedException {
        getBank().open();
        getBank().depositAll();
        getBank().close();
    }

    private void PotatoAmtInBank() {
        getBank().getItem("Potato").getAmount();
    }

    private boolean atGrandExchange() {
        return myPosition().equals(Banks.GRAND_EXCHANGE);
    }

    private void walkToGrandExchange() throws InterruptedException {
        getWalking().webWalk(Banks.GRAND_EXCHANGE);
    }

    private void sellPotatoes() throws InterruptedException {
        NPC clerk = npcs.closest("Grand Exchange Clerk");
        if(clerk != null) {
            grandExchange.sellItem(1943, 1, 5);
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawString("Potatoes Picked: " + currentPotatoAmt  + "/ " + potatoMaxQty, 10, 290);
    }

}