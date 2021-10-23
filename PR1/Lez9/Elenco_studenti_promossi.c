#include <stdio.h>
#include <stdlib.h>
#define MAX_STR 10

/* define struct element */
typedef struct Stud{
    char cognome[MAX_STR];
    char nome[MAX_STR];
    int voto;
}Studente;

/* function prototypes */
void printIfPassed(Studente stud);

/* main functions */
int main(void){
    FILE *fPtr;
    fPtr = fopen("input.txt", "r");
    Studente esaminato;
    while(fscanf(fPtr, "%[^;];%[^;];%d\n", esaminato.cognome, esaminato.nome, &esaminato.voto) != EOF){
        printIfPassed(esaminato);
    }
    return 0;
}

/* functions body */
void printIfPassed(Studente stud){
    if(stud.voto >= 18){
        printf("%10s %10s %4d ESAME SUPERATO\n", stud.cognome, stud.nome, stud.voto);
    }
    return;
}