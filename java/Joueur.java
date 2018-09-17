package puissance4;
import java.io.*;
import java.net.*;

public class Joueur {
	
	private String symbole;
	private String nom;
  	private String joueurinterface;
  	private BufferedReader bufferedreader;
	private DataOutputStream dataoutputstream;
	private ServerSocket ssocket;

	public Joueur(String n) {
    		this.nom = n;
  		this.ssocket = null;
    		this.joueurinterface = "local";
  	} 
	public Joueur(String n, String i, ServerSocket s) {
    		this.nom = n;
  		this.ssocket = s;
    		this.joueurinterface = i;
  	} 
  	public void setsoket(ServerSocket s) {
    		this.ssocket = s;
  	}
  	public void setinterface(String i) {
    		this.joueurinterface = i;
  	}
	public void setdataoutputstream(DataOutputStream i) {
                this.dataoutputstream = i;
        }
	public void setbufferedreader(BufferedReader i) {
                this.bufferedreader = i;
        }
  	public ServerSocket getsoket() {
    		return ssocket;
  	}
  	public String getinterface() {
  		return joueurinterface;
  	}
  	public String getnom() {
    		return nom;
  	}
	public DataOutputStream getdataoutputstream() {
                return dataoutputstream;
        }
	public BufferedReader getbufferedreader() {
                return bufferedreader;
        }
}
