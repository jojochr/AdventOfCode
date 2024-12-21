from pathlib import Path
import os

word = 'XMAS'
directions = [
    (-1, -1),  # oben links
    (0, -1),   # oben mitte
    (+1, -1),  # oben rechts
    (+1, 0),   # rechts
    (+1, +1),  # unten rechts
    (0, +1),   # unten mitte
    (-1, +1),  # unten links
    (-1, 0)    # links
]
# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_4_input.txt')

    with open(file_path) as file:
        return file.read()

def findXMAS(currentWordIndex, currentxPosition, currentYPosition, input, xDirection, yDirection):
    #Basisfall - Ende des Wortes erreicht
    if(currentWordIndex == len(word)):
        return 1
    #Basisfall - Pruefung, ob der Zugriff zulässig ist          
    if (currentxPosition < 0) or (currentxPosition >= len(input[0])) or (currentYPosition < 0) or (currentYPosition >= len(input)):
        return 0   
    #Check, ob das aktuelle Zeichen stimmt
    if(word[currentWordIndex] != input[currentYPosition][currentxPosition]):
        return 0
    return findXMAS(currentWordIndex + 1, currentxPosition + xDirection, currentYPosition + yDirection, input, xDirection, yDirection)

def findAllInstancesOfXMAS():
    input = readInput()
    xmasCount = 0
    # Die 2D-Matrix erstellen
    matrix = []
    for row in input.split("\n"):
        if(len(row) > 0):
            matrix.append(list(row))

    for row in range(len(matrix)):
        for col in range(len(matrix[row])):
            # Nur wenn ein X vorliegt nach dem restlichen Wort schauen
            if(matrix[row][col] == 'X'):
                for dx, dy in directions:
                    xmasCount += findXMAS(1, col + dx, row + dy, matrix, dx, dy)    
    return xmasCount
print(findAllInstancesOfXMAS())