from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    # Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_2_input.txt')

    with open(file_path) as file:
        return file.read()

def isValidLevel(level):
    isRising = level[0] <= level[-1]
    for i in range(len(level) - 1):
        diff = level[i + 1] - level[i]
        if isRising and not (0 < diff < 4):
            return False
        elif not isRising and not (0 < -diff < 4):
            return False
    return True

def isValidWithProblemDampener(level):
    for i in range(len(level)):
        # Probiere das Entfernen des i-ten Elements
        tempLevel = level[:i] + level[i + 1:]
        if isValidLevel(tempLevel):
            return True
    return False

# Zählt die Anzahl gültiger Level-Reports
def countValidLevels():
    input = readInput()
    token = input.strip().split("\n")
    levelCounter = 0

    for level in token:
        if not level.strip():  
            continue

        # Konvertiere die Zahlen in eine Liste von Integers
        level = list(map(int, level.split()))
        
        # Prüfe Gültigkeit (mit und ohne Problem Dampener)
        if isValidLevel(level) or isValidWithProblemDampener(level):
            levelCounter += 1

    return levelCounter

print("Anzahl Level: ", countValidLevels())
