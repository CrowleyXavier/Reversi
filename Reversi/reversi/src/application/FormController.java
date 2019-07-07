package application;

import java.net.URL;
import java.util.ResourceBundle;

import game.game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class FormController implements Initializable {
	@FXML Canvas canvas;
	@FXML private Label label_sum;
	@FXML private Label label_text;
	@FXML private Button button_Undo;
	@FXML private Button button_Reset;
	game game;
	Label label;
	GraphicsContext gc;

	@Override
	public void initialize(URL location,ResourceBundle resources) {
		game = new game();
		gc=canvas.getGraphicsContext2D();

		game.Koho();

		//クリックすると石を設置する
        canvas.setOnMousePressed(ev ->{
        	int Mouse_x_position = (int)ev.getSceneX();
        	int Mouse_y_position = (int)ev.getSceneY();
        	System.out.println("(x,y)=("+Mouse_x_position+","+Mouse_y_position+")");
        	for(int y=0;y<8;y++) {
        		for(int x=0;x<8;x++) {
                	if(110+x*20<Mouse_x_position&&Mouse_x_position<130+x*20&&125+y*20<Mouse_y_position&&Mouse_y_position<145+y*20) {
                		System.out.println("cellstate=  "+game.CellState(x, y)+"  (x,y)=("+x+","+y+")");
                		if(game.CellState(x, y)==0) {

                			game.TestReverse(x, y);//反転操作
                			game.Koho();//おける場所を探す

                			if(game.Pass()==true) {
                				game.ChangeTurn();
                				System.out.println("Pass");//おける場所がなければパス
                				game.Koho();
                				if(game.Pass()==true) {
                					System.out.println("GameOver");//どちらとも置く場所がなければ終了
                					label_text.setText("ゲーム終了");
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


	//Undoボタンを押すと、盤面を1つ前に戻す
    @FXML
    public void onUndoClicked() {//ひとつ前に戻る
    	game.Undo();
    	game.Koho();
        draw();
    }


    //Resetのボタンを押すと、盤面が初期状態になる
    @FXML
    public void onResetClicked() {
    	game.Reset();
    	game.Koho();
        draw();
    }


	public void draw() {
		//undo出来るときはラベルが黒色で出来ないときは赤色となる
		if(game.isUndoable()==true) {
			button_Undo.setTextFill(Color.BLACK);
		}else {
			button_Undo.setTextFill(Color.RED);
		}

		int white =0;
		int black=0;
		//盤面上の白石と黒石の個数を数える
		for(int y=0;y<8;y++) {
			for(int x =0;x<8;x++) {
				if(game.CellState(x, y)==1) {
					white++;
				}else if(game.CellState(x, y)==2) {
					black++;
				}
			}
		}

		gc.setStroke(Color.BLACK);

		label_sum.setText("白石："+white+"    黒石："+black);

		if(game.FirstPlayer()==true&&game.Pass()==false) {
			label_text.setText("白石のターンです");
		}else if(game.FirstPlayer()==false&&game.Pass()==false){
			label_text.setText("黒石のターンです");
		}
		//盤面作成
		gc.setFill(Color.GREEN);
		gc.fillRect(10, 20, 160, 160);//下生地

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
					gc.fillRect(10.2+x*20, 20.2+y*20, 19, 19);
				}else if(game.KohoState(x, y)==0) {
					gc.setFill(Color.GREEN);
					gc.fillRect(10.2+x*20, 20.2+y*20, 19, 19);
				}
				if(game.CellState(x, y)!=0) {
					gc.setFill(Color.GREEN);
					gc.fillRect(10.2+x*20, 20.2+y*20, 19, 19);
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
