from pathlib import Path
import os

antinode_symbol = '#'
neutral_symbol = '.'
class ListEntry: 
    def __init__(self, symbol): 
        self.symbol = symbol 
        self.coordinates = []
# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_8_input.txt')

    with open(file_path) as file:
        return file.read()
    
def findNodes():
    input = readInput()
    #Input in einer Matrix abspeichern
    matrix = []
    for row in input.split("\n"):
        if(len(row) > 0):
            matrix.append(list(row))

    # Einzigartige Symbole und deren Vorkommen ermitteln
    positions = []
    for y in range(len(matrix)):
        for x in range(len(matrix[y])):
            if(matrix[y][x] != neutral_symbol):# Check, ob das Symbol bereits bekannt ist 
                found = False 
                for entry in positions: 
                    if entry.symbol == matrix[y][x]: 
                        entry.coordinates.append((y, x)) 
                        found = True 
                        break 
                # Wenn das Symbol noch nicht bekannt ist 
                if not found: 
                    new_entry = ListEntry(matrix[y][x])
                    new_entry.coordinates.append((y, x)) 
                    positions.append(new_entry)
    print("Es wurden", len(positions), "einzigartige Antennensymbole gefunden")
    return (matrix, positions)

# Zählt die Anzahl der Antinodes in der Matrix
def countAntinodes(matrix):
    count = 0
    for row in matrix:
        for cell in row:
            if cell == antinode_symbol:
                count += 1
    return count

#Ermittelt für einen Antennetypen die Antinodes und zeichnet sie in die Matrix ein
def calcAntiNodes(matrix, position):
    #Nun für jedes Paar von Positionen die Antinodes einzeichnen
    for firstNode in position.coordinates:
        for secondNode in position.coordinates:
            #Keine Selbstreferenz
            if(firstNode == secondNode):
                continue
            distance_X = secondNode[1] - firstNode[1]
            distance_Y = secondNode[0] - firstNode[0] 
            x_1 = firstNode[1] - distance_X
            y_1 = firstNode[0] - distance_Y
            x_2 = secondNode[1] + distance_X
            y_2 = secondNode[0] + distance_Y
            #Einzeichnen
            if 0 <= x_1 < len(matrix[0]) and 0 <= y_1 < len(matrix):
                matrix[y_1][x_1] = antinode_symbol

            if 0 <= x_2 < len(matrix[0]) and 0 <= y_2 < len(matrix):
                matrix[y_2][x_2] = antinode_symbol            

(matrix, positions) = findNodes()
for position in positions:
    calcAntiNodes(matrix, position)

num_nodes = countAntinodes(matrix)
print("Anzahl der einzigartigen Antinodes: ", num_nodes)

