#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
void read_matrix 	(int ** a, int a_dim);
void diagonal2zero 	(int ** a, int a_dim);
void dumpMatrix		(int ** a, int a_dim);
void freeMatrix		(int ** a, int a_dim);

/* main functions */
int main(void){
	int R, i;

	/* allocate memory for the RxR matrix */
	scanf("%d", &R);
	int * matrix [R];
	for(i = 0; i < R; i++){
		*(matrix + i) = (int*)malloc(sizeof(int)*R);
	}

	/* read matrix values */
	read_matrix(matrix, R);
	/* set main diagonal values to zero */
	diagonal2zero(matrix, R);
	/* print altered matrix */
	dumpMatrix(matrix, R);
	/* free matrix */
	freeMatrix(matrix, R);
	return 0;
}

/* functions body */
void read_matrix (int **a, int a_dim){
	int i,j;
	for(i = 0; i < a_dim; i++){
		for(j = 0; j < a_dim ; j++){
			scanf("%d", (*(a + i) + j));
		}
	}
	return;
}

void diagonal2zero(int ** a, int a_dim){
	int i,j;
	for(i = 0; i < a_dim; i++){
		for(j = 0; j < a_dim; j++){
			if(i == j || i+j == a_dim-1){
				*(*(a + i) + j) = 0;
			}
		}
	}
	return;
}

void dumpMatrix(int ** a, int a_dim){
	int i, j;
	for(i = 0; i < a_dim; i++){
		for(j = 0; j < a_dim; j++){
			printf("%d ", *(*(a + i) + j) );
		}
		puts("");
	}
	return;
}

void freeMatrix(int ** a, int a_dim){
	int i;
	for(i = 0; i < a_dim; i++){
		free( *(a + i));
		*(a + i) = NULL;
	}
	return;
}
