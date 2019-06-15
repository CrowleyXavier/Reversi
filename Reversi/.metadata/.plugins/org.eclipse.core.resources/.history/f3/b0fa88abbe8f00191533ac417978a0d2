package game;

public class game {
	private int[][] cells= new int[8][8];
	private boolean fp=true;//先攻か後攻かを判定
	public game() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				cells[i][j]=0;
			}
		}
		cells[3][3]=2;
		cells[4][3]=2;
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



	public void JudgeSet(int x,int y) {

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






