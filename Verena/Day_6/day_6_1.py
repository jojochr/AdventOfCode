from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_6_input.txt')

    with open(file_path) as file:
        return file.read()

def getDirection(currentDirX, currentDirY):
    #Blick nach oben
    if currentDirX == 0 and currentDirY == -1:
        return (1, 0)
    #Blick nach rechts
    elif currentDirX == 1 and currentDirY == 0:
        return (0, 1)
    #Blick nach unten
    elif currentDirX == 0 and currentDirY == 1:
        return (-1, 0)
    #Blick nach links
    else:
        return (0, -1)

def findPath():    
    #Eingabe einlesen in in eine Matrix schreiben. 
    #Dabei Position des Guards merken
    input = readInput()
    #Position
    guardX, guardY = 0,0
    #Orientierung
    guardDirX, guardDirY = 0, -1
    steps = 0
    matrix = []
    for row in input.split("\n"):
        if(len(row) > 0):
            matrix.append(list(row))
        index = row.find("^") 
        if(index != -1):
              guardX, guardY = index, len(matrix) - 1
    
    #Guard laufen lassen, solange er sich noch auf der Map befindet
    while 0 <= guardX <= len(matrix[0]) and 0 <= guardY <= len(matrix):
        #Check auf Kollision
        while matrix[guardY + guardDirY][guardX + guardDirX] == '#':
            guardDirX, guardDirY = getDirection(guardDirX, guardDirY)
        #Guard bewegen
        guardX += guardDirX
        guardY += guardDirY
        # Wenn die Position noch nicht besucht ist hochzählen
        if(matrix[guardY][guardX] != 'X'):
            steps += 1
        #Position als besucht markieren
        matrix[guardY][guardX] = 'X'
    return steps
print("Schritte gesamt: ", findPath())