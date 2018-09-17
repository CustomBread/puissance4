x=6;
y=6;
player = 0;
playersym = ["x", "o"]
matrix = [[0]*(x+1) for i in range(y+1)]
nbjeton = 36
winner = False
col  = 0

def check_dir(dx, dy, ligne, col):
    result=0
    px=ligne
    py=col
    px+=dx
    py+=dy
    while px>=0 and px<x and py>=0 and py<y and matrix[px][py] == matrix[ligne][col]:
        result+=1
        px+=dx
        py+=dy
    return result

def checkwinner(ligne, col):
    return (check_dir(-1, 0, ligne, col)+check_dir(1, 0, ligne, col)+1)>=4 or (check_dir(0, 1, ligne, col)+check_dir(0, -1, ligne, col)+1)>=4 or (check_dir(1, -1, ligne, col)+check_dir(-1, 1, ligne, col)+1)>=4

def initgame():
    for i in range(0, x+1):
        for v in range(0, y+1):
            matrix[i][v]=" "


def showgame():
    print("Tour du joueur :" + playersym[player])
    print("| 0 | 1 | 2 | 3 | 4 | 5 |\n-------------------------")
    for i in range(x):
        for v in range(y):
            print("| " +  matrix[i][v], end=" ")
        print("|\n-------------------------")

def playgame():
    ligne = 0
    global col 
    getint()
    #col = int(input('Please choose a colum : '))
    col%=x;
    while  matrix[ligne+1][col].isspace() and (x-1)>ligne:
        ligne+=1
    matrix[ligne][col]=playersym[player];
    return ligne

def getint():
    while True:
        try:
            col = int(input('Please choose a colum : '))
            break
        except ValueError:
            print("Try Again")
    

    return
def setplayer():
    global player
    player=(player+1)%2

def maingame():
    global winner
    global nbjeton
    initgame()
    while not winner and nbjeton:
        setplayer()
        print("\033[H\033[J");
        showgame()
        ligne = playgame();
        winner = checkwinner(ligne, col)
        nbjeton-=1

maingame()
print("\033[H\033[J");
print("BRAVO : " + playersym[player] + " Tu as GAGNE !!!!!")
showgame()

