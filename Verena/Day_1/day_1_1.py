from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_1_input.txt')

    with open(file_path) as file:
        return file.read()
    
def getTotalDistance():
    input = readInput()
    leftSide = []
    rightSide = []
    for row in input.split("\n"):
        entries = row.split("   ")
        if(len(entries) == 2):
            leftSide.append(int(entries[0]))
            rightSide.append(int(entries[1]))

    #Aufsteigend sortieren
    rightSide.sort()
    leftSide.sort()        
    totalDistance = 0
    #Ueber Listen iterieren
    for i in range(len(rightSide)):
        print("left:", leftSide[i], "right: ", rightSide[i], "diff: ", abs(rightSide[i] - leftSide[i]))
        totalDistance += abs(rightSide[i] - leftSide[i])
    return totalDistance


print(getTotalDistance())    