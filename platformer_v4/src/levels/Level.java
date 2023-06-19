package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Ghost;
import main.Game;
import objects.Coin;
import objects.Trap;
import utilz.AddMethod;

import static utilz.AddMethod.GetLevelData;
import static utilz.AddMethod.GetGhosts;
import static utilz.AddMethod.GetPlayerSpawn;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Ghost> ghosts;
	private ArrayList<Coin> coins;
	private ArrayList<Trap> traps;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createCoins();
		createTraps();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void createTraps() {
		traps = AddMethod.GetTraps(img);
	}

	private void createCoins() {
		coins = AddMethod.GetCoins(img);
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		ghosts = GetGhosts(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	public ArrayList<Trap> getTraps() {
		return traps;
	}

	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}

}
