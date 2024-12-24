#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <math.h>  

#define ROW_MAXLENGTH 2048

// Beendet das Programm mit entsprechender Fehlernummer. Errno muss gesetzt sein.
static void die(const char* msg) {
    perror(msg);
    exit(EXIT_FAILURE);
}

static unsigned long long  concatLong(unsigned long long a,unsigned long long b) {
    int numDigitsB = (b == 0) ? 1 : (int)log10(b) + 1;
    return a * (unsigned long long)pow(10, numDigitsB) + b;
}


static int checkCombinations(unsigned long long* values, int numValues, int currentOperatorIndex, unsigned long long currentSum, unsigned long long targetSum) {
    // Basisfälle
    if (currentOperatorIndex == numValues) {     
        return currentSum == targetSum;
    }

    // Zielsumme bereits überschritten
    if (currentSum > targetSum) {
        return 0;
    }

    unsigned long long nextValue = values[currentOperatorIndex];

    // Multiplikation inkl Overflowcheck
    if (currentSum <= ULLONG_MAX / nextValue) {
        if (checkCombinations(values, numValues, currentOperatorIndex + 1, currentSum * nextValue, targetSum)) {
            return 1;
        }
    }

    // Addition 
    if (checkCombinations(values, numValues, currentOperatorIndex + 1, currentSum + nextValue, targetSum)) {
        return 1;
    }

    //Concat 
    if (checkCombinations(values, numValues, currentOperatorIndex + 1, concatLong(currentSum, nextValue), targetSum)) {
        return 1;
    }
    
    // Keine passende Lösung
    return 0;
}

int main() {
    const char* fileName = "day_7_input.txt";
    FILE* file = fopen(fileName, "r");
    if (file == NULL) {
        die("fopen");
    }

    unsigned long long totalSum = 0;
    char line[ROW_MAXLENGTH];

    while (fgets(line, sizeof(line), file)) {
        // Check ob Zeile Inhalt hat
        if (strlen(line) == 0) {
            continue;
        }

        // Input zerschneiden
        char* token = strtok(line, " :");
        if (token == NULL) {
            die("strtok");
        }

        // Summe speichern
        unsigned long long sum = strtoull(token, NULL, 10);

        // Argumente auslesen
        unsigned long long* values = NULL;           
        int count = 0;            
        token = strtok(NULL, " "); 

        while (token != NULL) {
            // Speicherplatz reservieren
            values = realloc(values, (count + 1) * sizeof(unsigned long long));
            if (values == NULL) {
                die("realloc");
            }

            // Token in einen Integer konvertieren und speichern
            values[count] = strtoull(token, NULL, 10);
            count++;

            // Nächstes Token abrufen
            token = strtok(NULL, " ");
        }
        
        // Kombinationen testen mit denen sich die Summe erzeugen lässt. Verfügbar: *, +
        if (checkCombinations(values, count, 1, values[0], sum)) {
            totalSum += sum;
        }

        // Aufräumen
        free(values);
    }

    printf("Gesamtsumme: %llu \n", totalSum);
    fclose(file);
    exit(EXIT_SUCCESS);
}
