from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_4_input.txt')

    with open(file_path) as file:
        return file.read()

def findAllInstancesOfCrossedMAS():
    input = readInput()
    xmasCount = 0
    # Die 2D-Matrix erstellen
    matrix = []
    for row in input.split("\n"):
        if(len(row) > 0):
            matrix.append(list(row))

    for row in range(1, len(matrix) - 1):
        for col in range(1, len(matrix[row]) - 1):
            if(matrix[row][col] == 'A'):
                if(((matrix[row-1][col-1] == 'M' and matrix[row+1][col+1] == 'S') or 
                    (matrix[row-1][col-1] == 'S' and matrix[row+1][col+1] == 'M')) and
                    ((matrix[row - 1][col + 1] == 'M' and matrix[row + 1][col - 1] == 'S') or
                       (matrix[row - 1][col + 1] == 'S' and matrix[row + 1][col - 1] == 'M'))):
                        xmasCount += 1
    return xmasCount
print(findAllInstancesOfCrossedMAS())