package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;

	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() throws IOException {
		FileWriter fw = new FileWriter("config.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		try {
		// Full screen
		if(gp.fullScreenOn) {
			bw.write("On");
		}
		if(!gp.fullScreenOn) {
			bw.write("Off");
		}
		bw.newLine();

		// Music volume
		bw.write(String.valueOf(gp.music.volumeScale));
		bw.newLine();

		// SE volume
		bw.write(String.valueOf(gp.se.volumeScale));
		bw.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadConfig() throws FileNotFoundException {
		FileReader fr = new FileReader("config.txt");
		BufferedReader br = new BufferedReader(fr);

		try{
			String s = br.readLine();
			// Full screen
			if(s.equals("On")) {
				gp.fullScreenOn = true;
			}
			if(s.equals("Off")) {
				gp.fullScreenOn = false;
			}
			// Music volume
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			// SE volume
			s = br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
