package application;

import java.net.URL;
import java.util.ResourceBundle;

import game.game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class FormController implements Initializable {
	@FXML Canvas canvas;
	@FXML private Label label_turn;
	game game;

	GraphicsContext gc;

	@Override
	public void initialize(URL location,ResourceBundle resources) {

		game = new game();
		System.out.println("Hello");
		gc=canvas.getGraphicsContext2D();
		game.Koho();
        canvas.setOnMousePressed(ev ->{//クリックすると石を設置する
        	int xpos = (int)ev.getSceneX();
        	int ypos = (int)ev.getSceneY();

        	for(int y=0;y<8;y++) {
        		for(int x=0;x<8;x++) {
                	if(110+x*20<xpos&&xpos<130+x*20&&120+y*20<ypos&&ypos<140+y*20) {
                		System.out.println("cellstate=  "+game.CellState(x, y)+"  (x,y)=("+x+","+y+")");
                	if(game.CellState(x, y)==0) {
                		game.Reverse(x, y);//反転操作
                		game.Koho();//おける場所を探す
                		if(game.Pass()==true) {
                			game.Change();
                			System.out.println("Pass");
                			game.Koho();
                			if(game.Pass()==true) {
                				System.out.println("GameOver");
                			}
                		}
                	}
                	}
        		}
        	}


        	draw();
        });

		draw();
	}



	public void draw() {
		gc.setStroke(Color.BLACK);
/*		if(game.FirstPlayer()==true) {
			label_turn.setText("白石のターンです");
		}else {
			label_turn.setText("黒石のターンです");
		}*/
		//盤面作成
		for(int v=0;v<=8;v++) {//縦線
			gc.strokeLine(10+20*v, 20, 10+20*v, 20+8*20);
		}
		for(int h=0;h<=8;h++) {//横線
			gc.strokeLine(10, 20+h*20, 10+20*8, 20+h*20);
		}
		//候補場所の配置
		for(int y=0;y<8;y++) {
			for(int x =0;x<8;x++) {
				if(game.KohoState(x, y)==1&&game.CellState(x, y)==0) {
					gc.setFill(Color.RED);
					gc.fillRect(10.8+x*20, 20.8+y*20, 18.5, 18.5);
				}else if(game.KohoState(x, y)==0) {
					gc.setFill(Color.GREEN);
					gc.fillRect(10.8+x*20, 20.8+y*20, 18.5, 18.5);
				}

				if(game.CellState(x, y)!=0) {
					gc.setFill(Color.GREEN);
					gc.fillRect(10.8+x*20, 20.8+y*20, 18.5, 18.5);
				}
			}
		}

		//石の配置
		for(int y=0;y<8;y++) {
			for(int x =0;x<8;x++) {
				if(game.CellState(x, y)==1) {
					gc.setFill(Color.WHITE);
					gc.fillOval(12+x*20, 22+y*20, 16, 16);
				}else if(game.CellState(x, y)==2) {
					gc.setFill(Color.BLACK);
					gc.fillOval(12+x*20, 22+y*20, 16, 16);
				}
			}
		}



	}








}
