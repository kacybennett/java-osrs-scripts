import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import java.awt.*;
import java.util.Arrays;

@ScriptManifest(author = "kacy", info = "Creates base melee accounts with no mule!", name = "kacyBaseMeleeAIO", version = 1.0, logo = "")

public class KacyBaseMeleeAIO extends Script {
    int picked = 0;
    final int MAX_POTATOES = 34;

    @Override
    public void onStart() {
        log("Let's get started");
    }

    @Override
    public int onLoop() throws InterruptedException {

        return 0;
    }

    private void checkBank(Player p, Area closestBank) throws InterruptedException {

    }

    private void pickPotatoes(Player p, Position position, Area field) throws InterruptedException {

    }

    private void bankPotatoes(Player p) throws InterruptedException {

    }

    private void sellPotatoes(Player p, Area exchange) throws InterruptedException {

    }

    private void buyMeleeItems() {

    }

    private Area getClosestBank() {
        Area[] bankAreas = {
                Banks.AL_KHARID, Banks.ARCEUUS_HOUSE, Banks.ARDOUGNE_NORTH, Banks.ARDOUGNE_SOUTH, Banks.CAMELOT, Banks.CANIFIS,
                Banks.CASTLE_WARS, Banks.CATHERBY, Banks.DRAYNOR, Banks.DUEL_ARENA, Banks.EDGEVILLE, Banks.FALADOR_EAST, Banks.FALADOR_WEST,
                Banks.GNOME_STRONGHOLD, Banks.GRAND_EXCHANGE, Banks.HOSIDIUS_HOUSE, Banks.LOVAKENGJ_HOUSE, Banks.LOVAKITE_MINE,
                Banks.LUMBRIDGE_LOWER, Banks.LUMBRIDGE_UPPER, Banks.PEST_CONTROL, Banks.PISCARILIUS_HOUSE, Banks.SHAYZIEN_HOUSE,
                Banks.TZHAAR, Banks.VARROCK_EAST, Banks.VARROCK_WEST, Banks.YANILLE
        };

        Area closestBank = null;

        for (int i = 0; i < bankAreas.length; i++) {
            if (closestBank == null) {
                closestBank = bankAreas[i];
            } else if (myPosition().distance(bankAreas[i].getRandomPosition()) < myPosition().distance(closestBank.getRandomPosition())) {
                closestBank = bankAreas[i];
            }
        }
        return closestBank;
    }

    private void walkTo(Area destination) throws InterruptedException {
        WebWalkEvent webWalkEvent = new WebWalkEvent(destination);
        getSettings().setRunning(true);
        execute(webWalkEvent);
    }

    @Override
    public void onMessage(Message message) throws InterruptedException{
        if(message.getMessage().toString().contains("You pick a potato.")){
            picked = picked + 1;
        }
    }

    public boolean doorIsClosed(RS2Object door) {
        return Arrays.asList(door.getDefinition().getActions()).contains("Open");
    }

    @Override
    public void onExit() {
        log("Thanks for running my PotatoPicker!");
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawString("Potatos Picked: " + picked + "/ " + MAX_POTATOES, 50, 50);

    }
}
