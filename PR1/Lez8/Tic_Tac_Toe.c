#include <stdio.h>
#include <stdlib.h>
#include <string.h>


char schema[3][6];

void checkvittoria();

int main() {
    char clear;
    int row; //Inizializzo Indici per Righe
    for (row = 0; row < 3; row ++) {
        scanf("%[^\n]s", schema[row]);
        scanf("%c", &clear);
    }
    checkvittoria();
    return 0;
}

void checkvittoria() {
  int row;
  int col;
  for (row = 0; row < 3; row++){
      for (col = 0; col < 6; col+=2){
          if (schema[row][0] == schema[row][2] && schema[row][2] == schema[row][4]) {
              printf("vince %c\n", schema[row][2]); //Controllo vittorie Orizzontali
              exit(EXIT_SUCCESS);
          }
          if (schema[0][col] == schema[1][col] && schema[1][col] == schema[2][col]) {
              printf("vince %c\n", schema[1][col]); //Controllo vittorie Verticali
              exit(EXIT_SUCCESS);
          }
      }
  }
  if (schema[0][0] == schema[1][2] && schema[1][2] == schema[2][4]){  //Controllo diagonali DX-to-SX
        printf("vince %c\n", schema[1][2]);
        exit(EXIT_SUCCESS);
  }
  else if (schema[2][0] == schema[1][2] && schema[1][2] == schema[0][4]){ //Controllo diagonali SX-to-DX
        printf("vince %c\n", schema[1][2]);
        exit(EXIT_SUCCESS);
  }
  else {
        printf("pareggio\n");
        exit(EXIT_SUCCESS);
    }
}