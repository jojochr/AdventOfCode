from pathlib import Path
import os

trailStart = '0'
trailEnd = '9'
directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_10_input.txt')

    with open(file_path) as file:
        input = file.read()
        matrix = []
        for row in input.split("\n"):
            if(len(row) > 0):
                matrix.append(list(row))   
        return matrix  

def checkCell(matrix, row, column, expected):
    if(row < 0 or row >= len(matrix)):
        return False
    elif column < 0 or column >= len(matrix[row]):
         return False
    return int(matrix[row][column]) == expected

def findDistinctPaths(matrix, currentRow, currentColumn, expected = 1):
  
    # Basisfall: Wenn wir hier mit einer 10 ankommen wurde die 9 auf jeden Fall erreicht
    if expected == 10:
        return 1 
    
    counter = 0
    # Für jede mögliche Richtung testen
    for direction in directions:
        newX = currentRow + direction[0]
        newY = currentColumn + direction[1]
        if checkCell(matrix, newX, newY, expected):
            # Rekursiv Pfade von der neuen Position aus zählen
            counter += findDistinctPaths(matrix, newX, newY, expected + 1)

    return counter

def findTrailHeads():
    matrix = readInput()
    trailheadCounter = 0
    #Für jede Zelle
    for row in range(len(matrix)):
        for cell in range(len(matrix[row])):
            #Wenn wir uns nicht auf einem Ausgangspunkt für einen Wanderweg befinden einfach weiter
            if matrix[row][cell] != trailStart:
                continue

            trailheadCounter += findDistinctPaths(matrix, row, cell, 1)    
    return trailheadCounter

print("Anzahl gefundener einzigartiger Wege: ", findTrailHeads())