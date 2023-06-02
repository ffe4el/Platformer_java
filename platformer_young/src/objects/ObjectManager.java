package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[][] coinImgs ;
//	private BufferedImage[][] containerImgs;
	private BufferedImage trapImag;
	private ArrayList<Coin> coins;
//	private ArrayList<GameContainer> containers;
	private ArrayList<Trap> traps;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();

	}

	public void checkSpikesTouched(Player p) {
		for (Trap s : traps)
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
	}

	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Coin p : coins)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
	}

	public void applyEffectToPlayer(Coin p) {
		if (p.getObjType() == COIN_1)
			playing.getPlayer().changeHealth(COIN1_VALUE);
		else
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
	}

//	public void checkObjectHit(Rectangle2D.Float attackbox) {
//		for (GameContainer gc : containers)
//			if (gc.isActive() && !gc.doAnimation) {
//				if (gc.getHitbox().intersects(attackbox)) {
//					gc.setAnimation(true);
//					int type = 0;
//					if (gc.getObjType() == BARREL)
//						type = 1;
//					coins.add(new Coin((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
//							(int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
//					return;
//				}
//			}
//	}

	public void loadObjects(Level newLevel) {
		coins = new ArrayList<>(newLevel.getPotions());
//		containers = new ArrayList<>(newLevel.getContainers());
		traps = newLevel.getSpikes();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
		coinImgs = new BufferedImage[2][7];

		for (int j = 0; j < coinImgs.length; j++)
			for (int i = 0; i < coinImgs[j].length; i++)
				coinImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

//		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
//		containerImgs = new BufferedImage[2][8];

//		for (int j = 0; j < containerImgs.length; j++)
//			for (int i = 0; i < containerImgs[j].length; i++)
//				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

		trapImag = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
	}

	public void update(int[][] lvlData, Player player) {
		for (Coin p : coins)
			if (p.isActive())
				p.update();

//		for (GameContainer gc : containers)
//			if (gc.isActive())
//				gc.update();

	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
//		drawContainers(g, xLvlOffset);
		drawTraps(g, xLvlOffset);
	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (Trap s : traps)
			g.drawImage(trapImag, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()),
					SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

//	private void drawContainers(Graphics g, int xLvlOffset) {
//		for (GameContainer gc : containers)
//			if (gc.isActive()) {
//				int type = 0;
//				if (gc.getObjType() == BARREL)
//					type = 1;
//				g.drawImage(containerImgs[type][gc.getAniIndex()],
//						(int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
//						(int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
//			}
//	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Coin p : coins)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == COIN_1)
					type = 1;
				g.drawImage(coinImgs[type][p.getAniIndex()],
						(int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
						(int) (p.getHitbox().y - p.getyDrawOffset()), COIN_WIDTH, COIN_HEIGHT, null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Coin p : coins)
			p.reset();
//		for (GameContainer gc : containers)
//			gc.reset();
	}
}