package server.model.players.content.skills;

import server.Config;
import server.core.event.CycleEvent;
import server.core.event.CycleEventContainer;
import server.core.event.CycleEventHandler;
import server.model.items.ItemAssistant;
import server.model.players.Client;
import server.model.players.Player;
import server.util.Misc;

public class Cooking extends SkillHandler {

	private static void cookFish(Client c, int itemID, int xpRecieved,
			int levelRequired, int burntFish, int cookedFish, int object) {
		if (c == null) {
			return;
		}
		if (!hasRequiredLevel(c, 7, levelRequired, "cooking", "cook this")) {
			return;
		}
		int chance = c.playerLevel[7];
		if (c.playerEquipment[Player.playerHands] == 775) {
			chance = c.playerLevel[7] + 8;
		}
		if (chance <= 0) {
			chance = Misc.random(5);
		}
		c.playerSkillProp[7][0] = itemID;
		c.playerSkillProp[7][1] = xpRecieved * COOKING_XP;
		c.playerSkillProp[7][2] = levelRequired;
		c.playerSkillProp[7][3] = burntFish;
		c.playerSkillProp[7][4] = cookedFish;
		c.playerSkillProp[7][5] = object;
		c.playerSkillProp[7][6] = chance;
		c.stopPlayerSkill = false;
		int item = c.getItems().getItemAmount(c.playerSkillProp[7][0]);
		if (item == 1) {
			c.doAmount = 1;
			cookTheFish(c);
			return;
		}
		viewCookInterface(c, itemID);
	}

	private static void cookTheFish(final Client c) {
		if (c == null) {
			return;
		}
		if (c.playerSkilling[7]) {
			return;
		}
		c.playerSkilling[7] = true;
		c.stopPlayerSkill = true;
		c.getPA().removeAllWindows();
		if (c.playerSkillProp[7][5] > 0) {
			c.startAnimation(c.playerSkillProp[7][5] == 2732 ? 897 : 896);
		}
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					container.stop();
					return;
				}
				c.getItems().deleteItem(c.playerSkillProp[7][0],
						c.getItems().getItemSlot(c.playerSkillProp[7][0]), 1);
				if (c.playerLevel[7] >= fishStopsBurning(c.playerSkillProp[7][0])
						|| Misc.random(c.playerSkillProp[7][6]) > Misc
								.random(c.playerSkillProp[7][2])) {
					c.sendMessage("You successfully cook the "
							+ ItemAssistant.getItemName(c.playerSkillProp[7][0])
									.toLowerCase() + ".");
					if (Config.SOUND) {
						c.sendSound(c.getSound().COOKITEM);
					}
					c.getPA().addSkillXP(c.playerSkillProp[7][1] * 2, 7);
					c.getItems().addItem(c.playerSkillProp[7][4], 1);
				} else {
					c.sendMessage("Oops! You accidentally burnt the "
							+ ItemAssistant.getItemName(c.playerSkillProp[7][0])
									.toLowerCase() + "!");
					c.getItems().addItem(c.playerSkillProp[7][3], 1);
				}
				deleteTime(c);
				if (!c.getItems().playerHasItem(c.playerSkillProp[7][0], 1)
						|| c.doAmount <= 0) {
					resetCooking(c);
					container.stop();
				}
				if (!c.stopPlayerSkill) {
					resetCooking(c);
					container.stop();
				}
			}

			@Override
			public void stop() {

			}
		}, 2);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					container.stop();
					return;
				}
				if (c.playerSkillProp[7][5] > 0) {
					c.startAnimation(c.playerSkillProp[7][5] == 2732 ? 897
							: 896);
				}
				if (!c.stopPlayerSkill) {
					container.stop();
				}
			}

			@Override
			public void stop() {

			}
		}, 4);
	}

	public static void cookThisFood(Client p, int i, int object) {
		if (p == null) {
			return;
		}
		p.random = 4; // Makes sure that the next time the random event timer
						// hits 15 minutes a random event will be started.
		switch (i) {
		case 17811:
			cookFish(p, i, 100, 85, 18193, 18173, object);
			break;
		case 17807:
			cookFish(p, i, 100, 65, 18189, 18169, object);
			break;
		case 317:
			cookFish(p, i, 30, 1, 323, 315, object);
			break;
		case 321:
			cookFish(p, i, 30, 1, 323, 319, object);
			break;
		case 327:
			cookFish(p, i, 40, 1, 367, 325, object);
			break;
		case 345:
			cookFish(p, i, 50, 5, 357, 347, object);
			break;
		case 353:
			cookFish(p, i, 60, 10, 357, 355, object);
			break;
		case 335:
			cookFish(p, i, 70, 15, 343, 333, object);
			break;
		case 341:
			cookFish(p, i, 75, 18, 343, 339, object);
			break;
		case 349:
			cookFish(p, i, 80, 20, 343, 351, object);
			break;
		case 331:
			cookFish(p, i, 90, 25, 343, 329, object);
			break;
		case 359:
			cookFish(p, i, 100, 30, 367, 361, object);
			break;
		case 361:
			cookFish(p, i, 100, 30, 367, 365, object);
			break;
		case 377:
			cookFish(p, i, 120, 40, i + 4, i + 2, object);
			break;
		case 371:
			cookFish(p, i, 140, 45, i + 4, i + 2, object);
			break;
		case 383:
			cookFish(p, i, 210, 80, i + 4, i + 2, object);
			break;
		case 395:
			cookFish(p, i, 212, 82, i + 4, i + 2, object);
			break;
		case 389:
			cookFish(p, i, 216, 91, i + 4, i + 2, object);
			break;
		case 7944:
			cookFish(p, i, 150, 62, i + 4, i + 2, object);
			break;
		default:
			if (Firemaking.getLogData(i) != null)
				Firemaking.handleBonfire(p, i);
			else
				p.sendMessage("You can't cook this!");
			break;
		}
	}

	private static int fishStopsBurning(int i) {
		switch (i) {
		case 317:
			return 34;
		case 321:
			return 34;
		case 327:
			return 38;
		case 345:
			return 37;
		case 353:
			return 45;
		case 335:
			return 50;
		case 341:
			return 39;
		case 349:
			return 52;
		case 331:
			return 58;
		case 359:
			return 63;
		case 377:
			return 74;
		case 363:
			return 80;
		case 371:
			return 86;
		case 7944:
			return 90;
		case 383:
			return 94;
		default:
			return 99;
		}
	}

	public static void getAmount(Client c, int amount) {
		int item = c.getItems().getItemAmount(c.playerSkillProp[7][0]);
		if (amount > item) {
			amount = item;
		}
		c.doAmount = amount;
		cookTheFish(c);
	}

	public static void resetCooking(Client c) {
		c.playerSkilling[7] = false;
		c.stopPlayerSkill = false;
		for (int i = 0; i < 6; i++) {
			c.playerSkillProp[7][i] = -1;
		}
	}

	private static void viewCookInterface(Client c, int item) {
		if (c == null) {
			return;
		}
		c.getPA().sendFrame164(1743);
		c.getPA().sendFrame246(13716, 190, item);
		c.getPA().sendFrame126(
				"\\n\\n\\n\\n\\n" + ItemAssistant.getItemName(item) + "", 13717);
	}
}