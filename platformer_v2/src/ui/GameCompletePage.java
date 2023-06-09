package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameCompletePage {
	private Playing playing;
	private BufferedImage img;
	private MenuButton quit;
	private int imgX, imgY, imgW, imgH;
	protected double grade;

	public GameCompletePage(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
		setGrade();
	}

	private void createButtons() {
		quit = new MenuButton(Game.GAME_WIDTH / 2, (int) (235 * Game.SCALE), 2, Gamestate.MENU);
	}

	private void createImg() {
		img = LoadSave.GetSpritePng(LoadSave.GAME_COMPLETED);
		imgW = (int) (img.getWidth() * Game.SCALE);
		imgH = (int) (img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int) (100 * Game.SCALE);

	}
	
	// 학점 산출
	public void setGrade() {
		if(Playing.numCoins > 15)
			grade = 0.25 * Playing.numCoins;
		else if(Playing.numCoins > 13)
			grade = 0.25 * Playing.numCoins;
		else if(Playing.numCoins > 1)
			grade = 0.25 * Playing.numCoins;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		// 코인 출력
		 g.setColor(Color.white);
	     g.setFont(new Font("Arial", Font.BOLD, 16));
	     g.drawString("Coins: " + Playing.numCoins, imgX, imgY - 10);
	     g.drawString("Grade: " + grade, imgX + 100, imgY - 10);

		g.drawImage(img, imgX, imgY, imgW, imgH, null);

		quit.draw(g);
	}

	public void update() {
		quit.update();
	}

	private boolean isIn(MenuButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		quit.setMouseOver(false);

		if (isIn(quit, e))
			quit.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(quit, e)) {
			if (quit.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;

			}
		}

		quit.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(quit, e))
			quit.setMousePressed(true);
	}
}
