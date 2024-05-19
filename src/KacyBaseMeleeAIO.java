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

    //Idle Tracker
    int idleCounter = 0, MAX_IDLE_TIMER = 200;

    @Override
    public void onStart() {
        log("Let's get started!");

    }

    @Override
    public int onLoop() throws InterruptedException {
        log("Idle Counter: " + idleCounter + "of " + MAX_IDLE_TIMER);
        idleCounter++;

        // Close any dialogues (gaining levels, etc.)
        closeDialog();

        return 0;
    }

    private void pickPotatoes(Player p, Area closestBank, Area exchange) throws InterruptedException {
        log("Logged in. Checking bank...");

        // Walks to a bank from wherever the character logs in at
        walkTo(closestBank);
        NPC banker = npcs.closest("Banker");
        if(banker != null) {
            banker.interact("Bank");
            sleep(random(1800, 3300));
            // If the amount of potatoes in the bank is greater or equal to the max amount of potatoes wanted,
            // the character will walk to the GE and sell the potatoes.
            if(getBank().getItem("Potato").getAmount() >= MAX_POTATOES) {
                getBank().close();
                sleep(random(900, 3300));
                walkTo(Banks.GRAND_EXCHANGE);
                if (banker != null) {
                    banker.interact("Bank");
                    sleep(random(1300, 3400));
                    bank.withdrawAll(1943);
                    bank.close();
                    NPC clerk = npcs.closest("Grand Exchange Clerk");
                    if (clerk != null && clerk.isVisible()) {
                        if (!getGrandExchange().isOpen()) {
                            clerk.interact("Exchange");
                        } else {
                            getGrandExchange().sellItem(1943, 10, 1);
                            sleep(random(4500, 7000));
                            getGrandExchange().collect();
                            sleep(random(2000, 3200));
                            getGrandExchange().close();
                            log("Potatoes sold!");
                        }
                    } else {
                        log("Clerk not found.");
                    }
                }
            }
        }
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

    public void closeDialog() {
        log("Closing Dialog...");

        if(getWidgets().getWidgetContainingText("Click here to continue") != null) {
            getKeyboard().typeKey(' ');
        }
        idleCounter++;
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
