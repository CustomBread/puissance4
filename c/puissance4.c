#include <stdio.h>

int x=6;
int y=6;
int player = 0;
char *playersym[2] = {"x\0", "o\0"}; 
char *matrix[6][6];
int nbjeton = 36;
int winner = 0;
int col  = 0;

int check_dir(int dx,int dy, int ligne, int col){
	int result=0;
	int px=ligne;
	int py=col;
	px+=dx;
	py+=dy;
	while ( (px>=0 && px<x) && (py>=0 && py<y) && (matrix[px][py] == matrix[ligne][col]) ){
		result++;
		px+=dx;
		py+=dy;
	}
	return result;
}

int checkwinner(int ligne, int col){
	return ( ((check_dir(-1, 0, ligne, col)+check_dir(1, 0, ligne, col)+1)>=4) || ((check_dir(0, 1, ligne, col)+check_dir(0, -1, ligne, col)+1)>=4) || ((check_dir(1, -1, ligne, col)+check_dir(-1, 1, ligne, col)+1)>=4) );
}

void initgame(){
	for(int i=0; i<x;i++){
		for(int v=0; v<y;v++){ 
			matrix[i][v]=" \0";
		}
	}
}

void showgame(){
	printf("Tour du joueur : %s\n\n", playersym[player]);
	printf("| 0 | 1 | 2 | 3 | 4 | 5 |\n-------------------------\n");
	for(int i=0; i<x;i++){
		for(int v=0; v<y;v++){
			printf("| %s ",matrix[i][v]);
		}
		printf("|\n-------------------------\n");
	}
}

int playgame(){
	int ligne = 0;
	printf("Please choose a colum : ");
	scanf("%d", &col);
	col%=x;
	while( matrix[ligne][col] == " \0" && x>ligne){
		ligne++;
	}
	matrix[ligne-1][col]=playersym[player];
	return ligne-1;
}

void setplayer(){
	player=(player+1)%2;
}

int  main(){
	initgame();
	while (!winner && nbjeton){
		printf("\033[H\033[J");
		showgame();
		int ligne = playgame();
		winner = checkwinner(ligne, col);
		nbjeton--;
		setplayer();
	}
	showgame();
}

