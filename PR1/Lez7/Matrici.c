#include <stdio.h>
#include <stdlib.h>
#define ROWS 4
#define COLUMNS 3

/* function prototypes*/
void debug_print(int b[ROWS][COLUMNS]);
void verify_col(int b[ROWS][COLUMNS]);

/* main function */
int main(void){
	int matrix[ROWS][COLUMNS];
	for(size_t i = 0; i < ROWS; i++){
		for(size_t j = 0; j < COLUMNS; j++){
			scanf("%d", &matrix[i][j]);
		}
	}
	verify_col(matrix);
	//debug_print(matrix);
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

void verify_col(int b[ROWS][COLUMNS]){
	int s = -1;
	for (size_t i = 0; i < COLUMNS; i++){
		if(b[0][i]%3 == 0 && b[1][i]%3 == 0 && b[2][i]%3 == 0 && b[3][i]%3 == 0){
			s = i;
		}
	}
	printf("%d\n", s);
	return;
}