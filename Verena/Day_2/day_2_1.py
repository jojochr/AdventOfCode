import operator
from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_2_input.txt')

    with open(file_path) as file:
        return file.read()
    
def checkIsValidLevel(level):
    if(len(level) <= 1):
        return 0
    problemFound = False
    isRising = int(level[0]) <= int(level[len(level) - 1])
    for index in range(len(level) - 1):
        #Wenn aufsteigend und Differenz in Range
        if isRising == True and 0 < int(level[index + 1]) - int(level[index]) < 4:
            continue 
        #Wenn absteigend und Differenz in Range
        elif isRising == False and 0 < int(level[index]) - int(level[index + 1]) < 4:
            continue
        #Es liegt ein Problem vor
        elif(not problemFound):
            index += 1
            problemFound = True
            continue
        else:    
            return 0    
    return 1

def countValidLevels():
    input = readInput()
    token = input.split("\n")
    levelCounter = 0
    for level in token:
        #Check, ob valide
        levelCounter += checkIsValidLevel(level.split(' '))
    return levelCounter    

print("Anzahl Level: ", countValidLevels())
