package game;

import java.util.ArrayList;
import java.util.List;

public class game {
	private int[][] cells= new int[8][8];
	private int[][] koho_cells= new int[8][8];
	private boolean fp=true;//先攻か後攻かを判定
	public List<int[][]> history = new ArrayList<int[][]>();
	public List<Boolean> history_turn = new ArrayList<Boolean>();

	public game() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				cells[i][j]=0;
			}
		}
		cells[3][3]=1;
		cells[4][3]=1;
		cells[3][4]=2;
		cells[4][4]=1;
	}

	//白か黒か空かをチェック
	public int CellState(int x,int y) {
		if(cells[y][x]==1) {
			return 1;//白
		}else if(cells[y][x]==2){
			return 2;//黒
		}else {
			return 0;//空
		}
	}

	public int KohoState(int x,int y) {
		if(koho_cells[y][x]==1) {
			return 1;//候補
		}else if(cells[y][x]==2){
			return 2;//石がすでに置いてある
		}else {
			return 0;//空
		}
	}

	//手番を交代する
	public void Change() {
	fp = !fp;
	}



	public boolean FirstPlayer() {
		if(fp==true) {
			return true;
		}else {
			return false;
		}
	}

	public void Koho() {//おける場所をkoho_cellsに入れる
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
		return true;//候補がなければパス発動
	}

	public void Reset() {//盤面と順番をリセット
		history.clear();
		history_turn.clear();
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				cells[i][j]=0;
				fp=true;
			}
		}
		cells[3][3]=1;
		cells[4][3]=2;
		cells[3][4]=2;
		cells[4][4]=1;
	}

	public void Undo() {//undoメソッド

		if(isUndoable()==true) {
			cells = history.get(history.size()-1);
			history.remove(history.size()-1);
			fp=history_turn.get(history_turn.size()-1);
			history_turn.remove(history_turn.size()-1);
		}
	}
	public boolean isUndoable() {
		if(history.size()!=0) {
			return true;
		}else return false;
	}




	public int JudgeSet(int x,int y) {
		boolean tonari=true;//反転できない場合、同色のとなりに石は置けない

		//上方向
		for(int i=y-1;i>=0;i--) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						return 1;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
			if(fp==true) {
				tonari=false;
				continue;
			}else {
				if(tonari==false) {
					return 1;
				}
				i=-1;
				break;
			}
			default:
				break;
			}
		}



		//下方向
		tonari=true;
		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						return 1;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						return 1;
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


		//右方向
		tonari=true;;
		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						return 1;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						return 1;
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


		//左方向
		tonari=true;

		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];

			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						return 1;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						return 1;
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



		//左上方向
				tonari=true;
				int lu=1;
				while(x+lu<8&&y+lu<8) {
					int check=cells[y+lu][x+lu];

					switch(check) {
					case 0:
						lu=10;
						break;
					case 1:
						if(fp==true) {
							if(tonari==false) {
								return 1;
							}
							lu=10;
							break;
						}else {
							lu++;
							tonari=false;
							continue;
						}
					case 2:
						if(fp==false) {
							if(tonari==false) {
								return 1;
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

				//左下方向
				tonari=true;
				int ld=1;
				while(x+ld<8&&y-ld>0) {
					int check=cells[y-ld][x+ld];
					switch(check) {
					case 0:
						ld=10;
						break;
					case 1:
						if(fp==true) {
							if(tonari==false) {
								return 1;
							}
							ld=10;
							break;
						}else {
							ld++;
							tonari=false;
							continue;
						}
					case 2:
						if(fp==false) {
							if(tonari==false) {
								return 1;
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

				//右下方向
				tonari=true;
				int rd=1;
				while(x-rd>0&&y-rd>0) {
					int check=cells[y-rd][x-rd];
					switch(check) {
					case 0:
						rd=10;
						break;
					case 1:
						if(fp==true) {
							if(tonari==false) {
								return 1;
							}
							rd=10;
							break;
						}else {
							rd++;
							tonari=false;
							continue;
						}
					case 2:
						if(fp==false) {
							if(tonari==false) {
								return 1;
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

				//右上方向
				tonari=true;
				int ru=1;
				while(x-ru>0&&y+ru<8) {
					int check=cells[y+ru][x-ru];
					switch(check) {
					case 0:
						ru=10;
						break;
					case 1:
						if(fp==true) {
							if(tonari==false) {
								return 1;
							}
							ru=10;
							break;
						}else {
							ru++;
							tonari=false;
							continue;
						}
					case 2:
						if(fp==false) {
							if(tonari==false) {
								return 1;
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
				return 0;
	}

	public void Reverse(int x,int y) {
		boolean turn=false;//ターン
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		int insert;
		int[][] save = new int[8][8];

		for(int i=0;i<8;i++) {//現在の盤面を取得
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
		if(fp==true) {
			insert = 1;
		}else {
			insert = 2;
		}
		//上方向
		int up=-1;
		System.out.println("x,y "+x+" ,"+y);
		for(int i=y-1;i>=0;i--) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						turn=true;
						up=i;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
			if(fp==true) {
				tonari=false;
				continue;
			}else {
				if(tonari==false) {
					turn=true;
					up=i;
				}
				i=-1;
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
		}

		//下方向
		tonari=true;
		int down=-1;
		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						turn=true;
						down=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						turn=true;
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

		}

		//右方向
		tonari=true;;
		int right=-1;
		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						turn=true;
						right=i;
					}
					i=8;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						turn=true;
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

		}

		//左方向
		tonari=true;
		int left=-1;
		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];

			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(fp==true) {
					if(tonari==false) {
						turn=true;
						left=i;
					}
					i=-1;
					break;
				}else {
					tonari=false;
					continue;
				}
			case 2:
				if(fp==false) {
					if(tonari==false) {
						turn=true;
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
		}


		//左上方向
				tonari=true;
				int lup_pos=-1;
				int lu=1;
				while(x+lu<8&&y+lu<8) {
					int check=cells[y+lu][x+lu];

					switch(check) {
					case 0:
						lu=10;
						break;
					case 1:
						if(fp==true) {
							if(tonari==false) {
								turn=true;
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
						if(fp==false) {
							if(tonari==false) {
								turn=true;
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
					if(lup_pos!=-1) {
					for(int i=0;i<lup_pos;i++) {
							cells[y+i][x+i]=insert;
						}
					}
				}

				//左下方向
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
						if(fp==true) {
							if(tonari==false) {
								turn=true;
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
						if(fp==false) {
							if(tonari==false) {
								turn=true;
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
					if(ld_pos!=-1) {
					for(int i=0;i<ld_pos;i++) {
							cells[y-i][x+i]=insert;
						}
					}
				}

				//右下方向
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
						if(fp==true) {
							if(tonari==false) {
								turn=true;
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
						if(fp==false) {
							if(tonari==false) {
								turn=true;
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
					if(rd_pos!=-1) {
					for(int i=0;i<rd_pos;i++) {
							cells[y-i][x-i]=insert;
						}
					}
				}

				//右上方向
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
						if(fp==true) {
							if(tonari==false) {
								turn=true;
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
						if(fp==false) {
							if(tonari==false) {
								turn=true;
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
					if(ru_pos!=-1) {
					for(int i=0;i<ru_pos;i++) {
							cells[y+i][x-i]=insert;
						}
					}
				}
		if(turn==true) {
			history.add(save);//盤面を保存
			if(fp==true) {
				history_turn.add(true);
			}else {
				history_turn.add(false);
			}
			Change();
			turn=false;
		}
	}



	public void ChangeWhite(int x,int y){
		boolean wturn=false;//ターン
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		//上方向
		int up=-1;
		System.out.println("x,y "+x+" ,"+y);
		for(int i=y-1;i>=0;i--) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(tonari==false) {
					wturn=true;
					up=i;
				}
				i=-1;
				break;
			case 2:
				tonari=false;
				continue;
			default:
				break;
			}
		}
		if(up !=-1) {
		for(int i=up+1;i<=y;i++) {
			cells[i][x]=1;
		}
		}

		//下方向
		tonari=true;
		int down=-1;
		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(tonari==false) {
					wturn=true;
					down=i;
				}
				i=8;
				break;
			case 2:
				tonari=false;
				continue;
			default:
				break;
			}
		}
		if(down !=-1) {
		for(int i=y;i<down;i++) {
			cells[i][x]=1;
		}

		}

		//右方向
		tonari=true;;
		int right=-1;
		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				if(tonari==false) {
					wturn=true;
					right=i;
				}
				i=8;
				break;
			case 2:
				tonari=false;
				continue;
			default:
				break;
			}
		}
		if(right!=-1) {
			for(int i=x;i<right;i++) {
				cells[y][i]=1;
			}

		}

		//左方向
		tonari=true;
		int left=-1;
		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];

			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				if(tonari==false) {
					wturn=true;
					left=i;
				}
				i=-1;
				break;
			case 2:
				tonari=false;
				continue;
			default:
				break;
			}
		}
		if(left!=-1) {
			for(int i=left+1;i<=x;i++) {
				cells[y][i]=1;
			}
		}


		//左上方向
				tonari=true;
				int lup_pos=-1;
				int lu=1;
				while(x+lu<8&&y+lu<8) {
					int check=cells[y+lu][x+lu];

					switch(check) {
					case 0:
						lu=10;
						break;
					case 1:
						if(tonari==false) {
							wturn=true;
							lup_pos=lu;
						}
						lu=10;
						break;
					case 2:
						lu++;
						tonari=false;
						continue;
					}
					if(lup_pos!=-1) {
					for(int i=0;i<lup_pos;i++) {
							cells[y+i][x+i]=1;
						}
					}
				}

				//左下方向
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
						if(tonari==false) {
							wturn=true;
							ld_pos=ld;
						}
						ld=10;
						break;
					case 2:
						ld++;
						tonari=false;
						continue;
					}
					if(ld_pos!=-1) {
					for(int i=0;i<ld_pos;i++) {
							cells[y-i][x+i]=1;
						}
					}
				}

				//右下方向
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
						if(tonari==false) {
							wturn=true;
							rd_pos=rd;
						}
						rd=10;
						break;
					case 2:
						rd++;
						tonari=false;
						continue;
					}
					if(rd_pos!=-1) {
					for(int i=0;i<rd_pos;i++) {
							cells[y-i][x-i]=1;
						}
					}
				}

				//右上方向
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
						if(tonari==false) {
							wturn=true;
							ru_pos=ru;
						}
						ru=10;
						break;
					case 2:

						ru++;
						tonari=false;
						continue;
					}
					if(ru_pos!=-1) {
					for(int i=0;i<ru_pos;i++) {
							cells[y+i][x-i]=1;
						}
					}
				}

		if(wturn==true) {
			Change();
			wturn=false;
		}
	}



	public void ChangeBlack(int x,int y){
		boolean bturn=false;//ターン
		boolean tonari=true;//変換できない場合、同色のとなりに石は置けない
		//上方向
		int up=-1;
		System.out.println("x,y "+x+" ,"+y);
		for(int i=y-1;i>=0;i--) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				tonari=false;
				continue;
			case 2:
				if(tonari==false) {
					bturn=true;
					up=i;
				}
				i=-1;
				break;
			default:
				break;
			}
		}
		if(up !=-1) {
		for(int i=up+1;i<=y;i++) {
			cells[i][x]=2;
		}
		}

		//下方向
		tonari=true;
		int down=-1;
		for(int i=y+1;i<8;i++) {
			int check=cells[i][x];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				tonari=false;
				continue;
			case 2:
				if(tonari==false) {
					bturn=true;
					down=i;
				}
				i=8;
				break;
			default:
				break;
			}
		}
		if(down !=-1) {
		for(int i=y;i<down;i++) {
			cells[i][x]=2;
		}

		}

		//右方向
		tonari=true;;
		int right=-1;
		for(int i= x+1;i<8;i++) {
			int check=cells[y][i];
			switch(check) {
			case 0:
				i=8;
				break;
			case 1:
				tonari=false;
				continue;
			case 2:
				if(tonari==false) {
					bturn=true;
					right=i;
				}
				i=8;
				break;
			default:
				break;
			}
		}
		if(right!=-1) {
			for(int i=x;i<right;i++) {
				cells[y][i]=2;
			}

		}

		//左方向
		tonari=true;
		int left=-1;
		for(int i= x-1;i>=0;i--) {
			int check=cells[y][i];

			switch(check) {
			case 0:
				i=-1;
				break;
			case 1:
				tonari=false;
				continue;
			case 2:
				if(tonari==false) {
					bturn=true;
					left=i;
				}
				i=-1;
				break;
			default:
				break;
			}
		}
		if(left!=-1) {
			for(int i=left+1;i<=x;i++) {
				cells[y][i]=2;
			}
		}


		//左上方向
				tonari=true;
				int lup_pos=-1;
				int lu=1;
				while(x+lu<8&&y+lu<8) {
					int check=cells[y+lu][x+lu];

					switch(check) {
					case 0:
						lu=10;
						break;
					case 1:
						lu++;
						tonari=false;
						continue;
					case 2:
						if(tonari==false) {
							bturn=true;
							lup_pos=lu;
						}
						lu=10;
						break;
					}
					if(lup_pos!=-1) {
					for(int i=0;i<lup_pos;i++) {
							cells[y+i][x+i]=2;
						}
					}
				}

				//左下方向
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
						ld++;
						tonari=false;
						continue;
					case 2:

						if(tonari==false) {
							bturn=true;
							ld_pos=ld;
						}
						ld=10;
						break;
					}
					if(ld_pos!=-1) {
					for(int i=0;i<ld_pos;i++) {
							cells[y-i][x+i]=2;
						}
					}
				}

				//右下方向
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
						rd++;
						tonari=false;
						continue;
					case 2:

						if(tonari==false) {
							bturn=true;
							rd_pos=rd;
						}
						rd=10;
						break;
					}
					if(rd_pos!=-1) {
					for(int i=0;i<rd_pos;i++) {
							cells[y-i][x-i]=2;
						}
					}
				}

				//右上方向
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
						ru++;
						tonari=false;
						continue;
					case 2:
						if(tonari==false) {
							bturn=true;
							ru_pos=ru;
						}
						ru=10;
						break;
					}
					if(ru_pos!=-1) {
					for(int i=0;i<ru_pos;i++) {
							cells[y+i][x-i]=2;
						}
					}
				}

		if(bturn==true) {
			Change();
			bturn=false;
		}
	}


}






