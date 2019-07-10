package game;

import java.util.ArrayList;
import java.util.List;

public class game {
	private int[][] cells= new int[8][8];							//盤面の状態のデータを格納する
	private int[][] koho_cells= new int[8][8];						//次に石をおける場所かどうかを格納する
	private boolean first_player =true;							//先攻か後攻かを判定
	public List<int[][]> history = new ArrayList<int[][]>();		//過去の盤面を格納する
	public List<Boolean> history_turn = new ArrayList<Boolean>();	//過去の手番を格納する


	public game() {
		NewGame();
	}

	//cells[][]のすべての値を0にして、その後に初期位置に黒石と白石を配置する
	public void NewGame() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				cells[i][j]=0;
			}
		}
		cells[3][3]=1;
		cells[4][3]=2;
		cells[3][4]=2;
		cells[4][4]=1;
	}

	//白か黒か空かを判定する
	public int CellState(int x,int y) {
		if(cells[y][x]==1) {
			return 1;//白
		}else if(cells[y][x]==2){
			return 2;//黒
		}else {
			return 0;//空
		}
	}

	//koho_cells[y][x]の値を返す
	public int KohoState(int x,int y) {
		if(koho_cells[y][x]==1) {
			return 1;//石を置くことができる
		}else if(cells[y][x]==2){
			return 2;//石がすでに置いてある
		}else {
			return 0;//石は置かれていなく、置くこともできない
		}
	}

	//手番を交代する
	public void ChangeTurn() {
		first_player = !first_player;
	}

	//first_playerの値を返す
	public boolean FirstPlayer() {
		if(first_player==true) {
			return true;
		}else {
			return false;
		}
	}

	//おける場所をkoho_cellsに入れる
	public void Koho() {
		for(int i=0;i<8;i++) {//初期化
			for(int j=0;j<8;j++) {
				koho_cells[i][j]=0;
			}
		}
		//おける場所を格納
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(cells[i][j]==0) {
					koho_cells[i][j]=JudgeSet(j,i);
				}else {
					koho_cells[i][j]=2;
				}
			}
		}
	}

	//おける場所があるかないかを判定
	//koho_cellsの中に1がなければtrueを返す
	public boolean Pass() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(koho_cells[i][j]!=1) {
					continue;
				}else {
					return false;
				}
			}
		}
		return true;
	}

	//過去の盤面及び手番のデータを削除し、現在の盤面、手番を初期状態にする
	public void Reset() {
		history.clear();
		history_turn.clear();
		NewGame();
	}

	//盤面と手番を1つ前に戻す、
	public void Undo() {
		if(isUndoable()==true) {
			cells = history.get(history.size()-1);
			history.remove(history.size()-1);
			first_player=history_turn.get(history_turn.size()-1);
			history_turn.remove(history_turn.size()-1);
		}
	}

	//undoできるか判定する
	//historyの中にデータがあるかを判定する
	public boolean isUndoable() {
		if(history.size()!=0) {
			return true;
		}else {
			return false;
		}
	}

	//(x,y)のマスに石を置けるなら1を返し、置けないなら0を返す
	public int JudgeSet(int x, int y) {
		boolean judge_up = JudgeUp(x,y);
		boolean judge_down = JudgeDown(x,y);
		boolean judge_right = JudgeRight(x,y);
		boolean judge_left = JudgeLeft(x,y);
		boolean judge_left_up = JudgeLeftUp(x,y);
		boolean judge_left_down = JudgeLeftDown(x,y);
		boolean judge_right_up = JudgeRightUp(x,y);
		boolean judge_right_down = JudgeRightDown(x,y);

		if(judge_up||judge_down||judge_right||judge_left||judge_left_up
				|| judge_left_down || judge_right_up || judge_right_down) {
			return 1;
		}else {
			return 0;
		}
	}

	//石を反転させる
	public void Reverse(int x,int y) {
		int stone;
		int[][] save = new int[8][8];

		//現在の盤面を取得
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(cells[i][j]==0) {
					save[i][j]=0;
				}else if(cells[i][j]==1) {
					save[i][j]=1;
				}else {
					save[i][j]=2;
				}
			}
		}

		if(first_player==true) {
			stone = 1;
		}else {
			stone = 2;
		}

		boolean up = ReverseUp(x,y,stone);
		boolean down = ReverseDown(x,y,stone);
		boolean right = ReverseRight(x,y,stone);
		boolean left = ReverseLeft(x,y,stone);
		boolean right_up =ReverseRightUp(x,y,stone);
		boolean right_down = ReverseRightDown(x,y,stone);
		boolean left_up = ReverseLeftUp(x,y,stone);
		boolean left_down = ReverseLeftDown(x,y,stone);

		if(up || down || right || left || right_up || right_down || left_up || left_down) {
				history.add(save);//盤面を保存
				if(first_player==true) {
					history_turn.add(true);
				}else {
					history_turn.add(false);
				}
				ChangeTurn();
		}
	}

	//指定方向のマスの相手の石を反転させる
	//上方向
	private boolean ReverseUp(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		int up=-1;

		for(int y_i=y-1;y_i>=0;y_i--) {
			int check=cells[y_i][x];
			switch(check) {
			case 0:
				y_i=-1;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						up=y_i;
					}
					y_i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==true) {
					tonari=false;
					continue;
				}else {
					if(tonari==false) {
						up=y_i;
					}
					y_i=-1;
				break;
			}
			default:
				break;
			}
		}

		if(up !=-1) {
			for(int i=up+1;i<=y;i++) {
				cells[i][x]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//下方向
	private boolean ReverseDown(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		int down=-1;

		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						down=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						down=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}

		if(down !=-1) {
			for(int i=y;i<down;i++) {
				cells[i][x]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//右方向
	private boolean ReverseRight(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		tonari=true;
		int right=-1;

		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						right=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						right=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}
		if(right!=-1) {
			for(int i=x;i<right;i++) {
				cells[y][i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//左方向
	private boolean ReverseLeft(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		int left=-1;

		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						left=i;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						left=i;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}
		if(left!=-1) {
			for(int i=left+1;i<=x;i++) {
				cells[y][i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//左上方向
	private boolean ReverseLeftUp(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		int lup_pos=-1;
		int lu=1;

		while(x+lu<8&&y+lu<8) {
			int check=cells[y+lu][x+lu];
			switch(check) {
			case 0:
				lu=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						lup_pos=lu;
					}
					lu=10;
					break;
				}else {
					lu++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						lup_pos=lu;
					}
					lu=10;
					break;
				}else {
					lu++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}
		if(lup_pos!=-1) {
			for(int i=0;i<lup_pos;i++) {
				cells[y+i][x+i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//左下方向
	private boolean ReverseLeftDown(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		tonari=true;
		int ld_pos=-1;
		int ld=1;

		while(x+ld<8&&y-ld>0) {
			int check=cells[y-ld][x+ld];
			switch(check) {
			case 0:
				ld=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						ld_pos=ld;
					}
					ld=10;
					break;
				}else {
					ld++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						ld_pos=ld;
					}
					ld=10;
					break;
				}else {
					ld++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}
		if(ld_pos!=-1) {
			for(int i=0;i<ld_pos;i++) {
				cells[y-i][x+i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//右下方向
	private boolean ReverseRightDown(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		tonari=true;
		int rd_pos=-1;
		int rd=1;

		while(x-rd>0&&y-rd>0) {
			int check=cells[y-rd][x-rd];
			switch(check) {
			case 0:
				rd=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						rd_pos=rd;
					}
					rd=10;
					break;
				}else {
					rd++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						rd_pos=rd;
					}
					rd=10;
					break;
				}else {
					rd++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}

		if(rd_pos!=-1) {
			for(int i=0;i<rd_pos;i++) {
				cells[y-i][x-i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//右上方向
	private boolean ReverseRightUp(int x,int y,int insert) {
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		tonari=true;
		int ru_pos=-1;
		int ru=1;

		while(x-ru>0&&y+ru<8) {
			int check=cells[y+ru][x-ru];
			switch(check) {
			case 0:
				ru=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						ru_pos=ru;
					}
					ru=10;
					break;
				}else {
					ru++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						ru_pos=ru;
					}
					ru=10;
					break;
				}else {
					ru++;
					tonari=false;
					continue;
				}
			}
		}

		if(ru_pos!=-1) {
			for(int i=0;i<ru_pos;i++) {
				cells[y+i][x-i]=insert;
			}
			return true;
		}else {
			return false;
		}
	}

	//次に置くことができるマスがあればtrueを返す
	//上方向
	private boolean JudgeUp(int x,int y) {
		boolean tonari=true;//反転できない場合、同色のとなりに石は置けない

		for(int i=y-1;i>=0;i--) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
			if(first_player==true) {
				tonari=false;
				continue;
			}else {
				if(tonari==false) {
					return true;
				}
				i=-1;
				break;
			}
			default:
				break;
			}
		}
		return false;
	}

	//下方向
	private boolean JudgeDown(int x,int y) {
		boolean tonari=true;

		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}
		return false;
	}

	//右方向
	private boolean JudgeRight(int x,int y) {
		boolean tonari=true;;
		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}
		return false;
	}

	//左方向
	private boolean JudgeLeft(int x,int y) {
		boolean tonari=true;

		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];

			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			default:
				break;
			}
		}
		return false;
	}

	//左上方向
	private boolean JudgeLeftUp(int x,int y) {
		boolean tonari=true;
		int lu=1;

		while(x+lu<8&&y+lu<8) {
			int check=cells[y+lu][x+lu];

			switch(check) {
			case 0:
				lu=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					lu=10;
					break;
				}else {
					lu++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					lu=10;
					break;
				}else {
					lu++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}
		return false;
	}

	//左下方向
	private boolean JudgeLeftDown(int x,int y) {
		boolean tonari=true;
		int ld=1;
		while(x+ld<8&&y-ld>0) {
			int check=cells[y-ld][x+ld];
			switch(check) {
			case 0:
				ld=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					ld=10;
					break;
				}else {
					ld++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					ld=10;
					break;
				}else {
					ld++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}
		return false;
	}

	//右下方向
	private boolean JudgeRightDown(int x,int y) {
		boolean tonari=true;
		int rd=1;
		while(x-rd>0&&y-rd>0) {
			int check=cells[y-rd][x-rd];
			switch(check) {
			case 0:
				rd=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					rd=10;
					break;
				}else {
					rd++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					rd=10;
					break;
				}else {
					rd++;
					tonari=false;
					continue;
				}
				default:
					break;
			}
		}
		return false;
	}

	//右上方向
	private boolean JudgeRightUp(int x,int y) {
		boolean tonari=true;
		int ru=1;

		while(x-ru>0&&y+ru<8) {
			int check=cells[y+ru][x-ru];
			switch(check) {
			case 0:
				ru=10;
				break;
			case 1:
				if(first_player==true) {
					if(tonari==false) {
						return true;
					}
					ru=10;
					break;
				}else {
					ru++;
					tonari=false;
					continue;
				}
			case 2:
				if(first_player==false) {
					if(tonari==false) {
						return true;
					}
					ru=10;
					break;
				}else {
					ru++;
					tonari=false;
					continue;
				}
			}
		}
		return false;
	}
}
