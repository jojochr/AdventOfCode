#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <sys/stat.h>


#define ROW_MAXLENGTH 2048
//Beendet das Programm mit entsprechender Fehlernummer. Errno muss gesetzt sein
static void die(char* msg){
    perror(msg);
    exit(EXIT_FAILURE);
}

static int checkRecursive(int* values, int numValues, int currentOperatorIndex, int currentSum, int targetSum){

    //Basisfälle
    if(currentOperatorIndex == numValues){     
        return currentSum == targetSum;
    }
    //Zielsumme bereits ueberschritten
    if(currentSum > targetSum){
        return 0;
    }

    // Rekursiver Aufruf: Ausprobieren von * und +
    int nextValue = values[currentOperatorIndex];
    
    // Multiplikation
    if (checkRecursive(values, numValues, currentOperatorIndex + 1, currentSum * nextValue, targetSum)) {
        return 1;
    }
    
    // Addition
    if (checkRecursive(values, numValues, currentOperatorIndex + 1, currentSum + nextValue, targetSum)) {
        return 1;
    }

    return 0;
}



//Prueft, ob sich aus den Zahlen in values mittels + und * die Summe aus sum erzeugen laesst
static int checkCombinations(int targetsum, int* values, int vcount){
    //int* values, int numValues, int currentOperatorIndex, int currentSum, int targetSum
    int test = checkRecursive(values, vcount, 1, values[0], targetsum);
    if(test == 1){
        printf("Gleichung lösbar\n");
    }else{
        printf("Gleichung nicht lösbar\n");
    }
    return test;
}



int main(){
    const char* fileName = "day_7_input.txt";
    FILE* file = fopen(fileName, "r");
    if (file == NULL) {
        die("fopen");
    }

    long totalSum = 0;
    char line[ROW_MAXLENGTH]; 

    while (fgets(line, sizeof(line), file)) {
        // Check ob Zeile Inhalt hat
        if (strlen(line) == 0) {
            continue;
        }
        //Input zerschneiden
        char *token = strtok(line, " :");
        if (token == NULL){
            die("strtok");
        }

        //Summe speichern
        int sum = atoi(token);
        //Argumente auslesen
        int* values = NULL;           
        int count = 0;            
        token = strtok(NULL, " ");   
        while(token != NULL){
            // Speicherplatz reservieren
            values = realloc(values, (count + 1) * sizeof(int));
            if (values == NULL) {
                die("realloc");
            }

            // Token in einen Integer konvertieren und speichern
            values[count] = atoi(token);
            count++;
            
            // Nächstes Token abrufen
            token = strtok(NULL, " ");
        }
        //Kombinationen testen mit denen sich die Summe erzeugen lässt. Verfügbar: *, +
        if(1 == checkCombinations(sum, values, count)){
            if(sum <= 0){
                printf("Test");
            }
            totalSum += sum;
        }

        //Aufräumen
        free(values);
    }
    printf("Gesamtsumme: %d \n", totalSum);
    fclose(file);
    exit(EXIT_SUCCESS);
}