#include <stdio.h>
#include <stdlib.h>
#define ROWS 4
#define COLUMNS 3

/* function prototypes*/
void debug_print(int b[ROWS][COLUMNS]);
void somma(int b[ROWS][COLUMNS], int c[ROWS][COLUMNS]);

/* main function */
int main(void){
	int mat1[ROWS][COLUMNS];
	int mat2[ROWS][COLUMNS];
	for(size_t i = 0; i < ROWS; i++){
		for(size_t j = 0; j < COLUMNS; j++){
			scanf("%d", &mat1[i][j]);
		}
	}	
	for(size_t i = 0; i < ROWS; i++){
		for(size_t j = 0; j < COLUMNS; j++){
			scanf("%d", &mat2[i][j]);
		}
	}
	somma(mat1, mat2);
	fflush(stdin);
	return 0;
}

/* functions body */
void debug_print(int b[ROWS][COLUMNS]){
	for(int i = 0; i < ROWS; i++){
		for(int j = 0; j < COLUMNS; j++){
			printf("DEBUG (%d,%d): %d\n", i, j, b[i][j]);
		}
	}
	return;
}

void somma(int b[ROWS][COLUMNS], int c[ROWS][COLUMNS]){
	for(int i = 0; i < ROWS; i++){
		printf("%d %d %d\n", b[i][0]+c[i][0], b[i][1]+c[i][1], b[i][2]+c[i][2]);
	}
	return;
}