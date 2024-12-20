
import operator
from pathlib import Path
import os
import re

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_3_input.txt')

    with open(file_path) as file:
        return file.read().replace('\n', '')
    
# Prueft, ob die folgenden 2 Zeichen in [0, 9] sind und erhöht ggf. den LeseIndex. 
# Gibt den aktualisierten LeseIndex zurück
def readOptionalDigits(instruction, readIndex):  
    for i in range (2):
        if instruction[readIndex].isdigit():
            readIndex += 1 
    return readIndex

# Extrahiert gültige Multiplikationsinstruktionen aus dem Inputstring und Summiert die Ergebnisse der einzelnen Multiplikationen auf.
def extractMulInstructions():
    input = readInput()
    # Ob aktuell addiert werden soll
    isEnabled = True
    # Gesamtsumme
    totalSum = 0
    # Reihenfolge hier wichtig. don't vor do, da do in dont enthalten!
    token = re.split(r"(mul|don't|do)", input)
    for tokenIndex in range (len(token)):
        instruction = token[tokenIndex]

        if(instruction == 'do'):
            isEnabled = True
            continue 
        elif (instruction == 'don\'t'):
            isEnabled = False
            continue   
        #Instruction muss hier ein mul sein, sonst ignoriere den Eintrag (nur relevant für Element 0 in token)
        elif(instruction != 'mul'):
            continue     
 
        #Es liegt ein mul vor. Nun die einzulesenden Zahlen holen
        tokenIndex += 1
        instruction = token[tokenIndex]

        #Wenn nicht gerechnet werden soll ist hier nichts weiter zu tun
        if(isEnabled == False):
            continue

        #LeseIndex im Teilstring zurücksetzen
        readIndex = 0
        #Nach dem mul eine öffnende Klammer
        if not instruction[readIndex] == '(':
            continue
        readIndex += 1

        #Die nächsten Ziffer muss eine Zahl sein
        if not (instruction[readIndex].isdigit()):
            continue
        readIndex += 1
        #Die nachfolgenden 2 Ziffern können eine Zahl sein
        readIndex = readOptionalDigits(instruction, readIndex)
 
        #Nun ein Komma
        if not instruction[readIndex] == ',':
            continue
        readIndex += 1 

        #Die nächsten Ziffer muss eine Zahl sein
        if not (instruction[readIndex].isdigit()):
            continue
        readIndex += 1

        #Die nachfolgenden 2 Ziffern können eine Zahl sein
        readIndex = readOptionalDigits(instruction, readIndex)

        #Nach dem mul eine öffnende Klammer
        if not instruction[readIndex] == ')':
            continue  
        readIndex += 1

        #Nun ist sichergestellt, dass die instruktion gültig ist            
        instruction = "operator.mul" + instruction[0:readIndex]
        res = eval(instruction)
        totalSum += res
                 
    return totalSum       
#Aufruf
print(extractMulInstructions())