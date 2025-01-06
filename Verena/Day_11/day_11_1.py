from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_11_input.txt')
    mylist = list()
    with open(file_path) as file:
        row = file.read().split(" ")
        for element in row:
            mylist.append(int(element))
        return mylist
    
def applyRules(number):
    # 0 wird durch 1 ersetzt
    if(number == 0):
        return [1]
    # Gerade Anzahl Ziffern
    if(len(str(number)) % 2 == 0):
        num = str(number)
        return [int(num[:len(num) // 2]), int(num[len(num) // 2:])]
    # Ungerade
    return [number * 2024]

def blink(iterations):
    stones = readInput()
    for _ in range(iterations):
        stoneIndex = 0
        while stoneIndex < len(stones):
            newStone = applyRules(stones[stoneIndex])
            #Wenn Anzahl der Steine gleich geblieben ist einfach ersetzen
            if(len(newStone) == 1):
                stones[stoneIndex] = newStone[0]
            #Stein wurde zerteilt
            else:
                stones[stoneIndex] = newStone[0]
                stones.insert(stoneIndex + 1, newStone[1])
                stoneIndex +=1   
            stoneIndex +=1   
        print("Aktuelle Länge: ", len(stones))       
    return len(stones)

print("Anzahl Steine: ", blink(75))