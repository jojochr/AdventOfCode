from pathlib import Path
import os

# Liest die Inputdatei in einen string. Die einzulesende Datei muss sich im selben Ordner befinden.
def readInput():
    #Dateipfad zusammenbauen
    script_dir = os.path.dirname(__file__) 
    file_path = os.path.join(script_dir, 'day_5_input.txt')

    with open(file_path) as file:
        return file.read()
    
def checkRule(rules, fromPage, toPage):
    for rule in rules:
        if rule[0] == fromPage and rule[1] == toPage:
                return True
    # Keine passende Regel gefunden
    return False    


def correctPrintingTask(rules, printingTask):
    # Nachfolgeseiten für jede Seite im aktuellen Task
    options = []
    # Für jede Seite im aktuellen Auftrag
    for currentPageIndex in range(len(printingTask)):
        currentPageOptions = []
        for potentialNextPage in range(len(printingTask)):
            if checkRule(rules, printingTask[currentPageIndex], printingTask[potentialNextPage]):
               currentPageOptions.append(printingTask[potentialNextPage])                  
        options.append([printingTask[currentPageIndex], currentPageOptions]) 
    # ToDo
    options_sorted = sorted(options, key=lambda x: len(x[1])) 
    return [entry[0] for entry in options_sorted]                  


def checkPages():
    input = readInput()
    rules = []
    printingTasks = []
    isReadingRules = True
    sumOfMiddlePageNumbers = 0
    # Regeln und Druckaufträge einlesen
    for row in input.split("\n"):
        if(len(row) == 0):
            isReadingRules = False
            continue
        if(isReadingRules):
            rules.append(row.split('|'))
        else:
            printingTasks.append(row.split(','))

    # Die einzelnen Druckaufträge prüfen
    for printingTask in printingTasks:
        ruleBroken = False
        #Für jede Seite
        for currentPageIndex in range(len(printingTask)):
            #Check ob die Seiten nach der aktuellen Seite zulässig sind
            for nextPageIndex in range(currentPageIndex + 1, len(printingTask)):
                if not checkRule(rules, printingTask[currentPageIndex], printingTask[nextPageIndex]):
                    ruleBroken = True
                    break 
            #Wenn bereits eine Regel gebrochen wurde diesen Datensatz nicht weiter prüfen
            if(ruleBroken):
                break                   
        #Wenn ein Regelbruch vorliegt ist der Eintrag relevant    
        if ruleBroken:    
            printingTask = correctPrintingTask(rules, printingTask)
            sumOfMiddlePageNumbers += int(printingTask[len(printingTask) // 2])
    return sumOfMiddlePageNumbers

print(checkPages())  