package puissance4;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import puissance4.*;

public class Puissance4 {
	public static int x = 6;
	public static int y = 6;
	public static int player = 0;
	public static String[] playersym = {"x", "o"};
	public static String[][] matrix = new String[7][7];
	public static int nbjeton = 6*6;
	public static int winner = 0;
	public static int col = 0;
	public static Joueur joueurs[] = new Joueur[2];

	public static int checkDir(int dx, int dy, int ligne, int col) {
		int result = 0;
		int px = ligne;
		int py = col;
		px += dx;
		py += dy;
		while((px >= 0 && px < x) && (py >= 0 && py < y) && matrix[px][py] == matrix[ligne][col]) {
			result++;
			px += dx;
			py += dy;
		}
		return result;
	}
	public static int checkwinner(int ligne, int col) {
		return checkDir(-1, 0, ligne, col) + checkDir(1, 0, ligne, col) + 1 >= 4 || checkDir(0, 1, ligne, col) + checkDir(0, -1, ligne, col) + 1 >= 4 || checkDir(1, -1, ligne, col) + checkDir(-1, 1, ligne, col) + 1 >= 4 ? 1 : 0;
	}
	public static void initgame() {
		joueurs[0] = new Joueur("x");
		joueurs[1] = new Joueur("o");

		for(int i = 0; i < x; i++) {
			for(int v = 0; v < y; v++) {
				matrix[i][v] = " ";
			}
		}
	}
	public static void showgame() {
		print(true,"Vous etes le joueur  : " + joueurs[player].getnom() + "\n");
		print(true,"| 0 | 1 | 2 | 3 | 4 | 5 |\n-------------------------");
		for(int i = 0; i < x; i++) {
			for(int v = 0; v < y; v++) {
				print(false,"| " + matrix[i][v] + " ");
			}
			print(true ,"|\n-------------------------");
		}
	}
	public static void currplayer() {
		player = (player + 1) % 2;
	}
	public static int playgame() {
		int ligne = 0;
		print(false,"Please choose a colum : ");
		int num = scaner();
		col = num % x;
		while( matrix[ligne][col] == " " && x>ligne ) {
			ligne++;
		}
		if (ligne >= 1){
			matrix[ligne - 1][col] = playersym[player];
			winner = checkwinner(ligne - 1, col);
			nbjeton--;
			print(false,"\033[H\033[J");
			print(true,"Tour de l'autre joueur");
			showgame();
			currplayer();
		}
		return winner;
	}	
	public static void print(boolean isln, String i){
		if (joueurs[player].getinterface() == "local"){
			if(isln)
				System.out.println(i);
			else
				System.out.print(i);
		}else{
			try{ 
				joueurs[player].getdataoutputstream().writeBytes(i);	
				if(isln)joueurs[player].getdataoutputstream().writeBytes("\n"); 
			}catch(IOException e){ 
				System.out.print("error");
			}
		}	
	}
	public static void initclient(){
		for(int i= 0;i<2;i++){
			System.out.print("Welcome "+joueurs[i].getnom() + "\nWould you linke to play [LOCAL]|[remote] ?");
			Scanner in = new Scanner(System.in);
			String ins = in.nextLine();
			System.out.println("getting :" + ins); 
			switch(ins){
				case "remote":
					joueurs[i].setinterface("remote");
					try {
						ServerSocket ws = new ServerSocket(6780+i);
						joueurs[i].setsoket(ws);
						System.out.println("waiting for client on port : " + (6780+i));
						Socket connectionsoc = ws.accept();
						BufferedReader infromclient =
							new BufferedReader(new InputStreamReader(connectionsoc.getInputStream()));
						joueurs[i].setbufferedreader(infromclient);
						DataOutputStream outToClient = 
							new DataOutputStream(connectionsoc.getOutputStream());
						joueurs[i].setdataoutputstream(outToClient);
						joueurs[i].getdataoutputstream().writeBytes("loading,,,,,");	
					}catch(IOException e) {
						e.printStackTrace();
					}

					break;
				default:
					joueurs[i].setinterface("local");
			}
		}	
	}
	public static int scaner(){
		int number;
		String input;
		if (joueurs[player].getinterface() == "remote"){	
			try {
				input = joueurs[player].getbufferedreader().readLine();
			}catch(IOException e) {
				return -1;
			}
		}else{
			Scanner in = new Scanner(System.in);
			String ins = in.nextLine();
			input = ins;
		}
		try
		{
			number = Integer.parseInt(input);
			return number;
		} catch (NumberFormatException ex)
		{
			print(false,"\nPlease choose a colum : ");
			return scaner();
		}


	}
	public static void main(String[] args) {
		initgame();
		initclient();
		while(winner == 0 && nbjeton != 0) {
			print(false,"\033[H\033[J");
			print(true,"C'est votre tour : " + joueurs[player].getinterface() );
			showgame();
			winner = playgame();
		}
		showgame();
	}
}
