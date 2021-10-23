#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
int ** set_matrix		(int r, int c);
void read_matrix 		(int ** mx, int r, int c);
void multiply_matrices		(int **A, int **B, int **C, int n, int m, int s);
void dumpMatrix			(int ** mx, int r, int c);
void freeMatrix			(int ** mx, int r, int c);

/* main functions */
int main(void){
	int N, S, M;
	scanf("%d", &N);
	scanf("%d", &M);
	scanf("%d", &S);
	
	/* allocate memory for matrices */
	int ** A_Matrix = set_matrix(N, M);
	int ** B_Matrix = set_matrix(M, S);

	/* read matrix values */
	read_matrix(A_Matrix, N, M);
	read_matrix(B_Matrix, M, S);

	/* allocate memory for result matrix and do multiplication */
	int ** C_Matrix = set_matrix(N, S);
	multiply_matrices(A_Matrix, B_Matrix, C_Matrix, N, M, S);

	/* print altered matrix */
	dumpMatrix(C_Matrix, N, S);
	/* free matrix */
	freeMatrix(A_Matrix, N, M);
	freeMatrix(B_Matrix, M, S);
	freeMatrix(C_Matrix, N, S);
	return 0;
}

/* functions body */

int ** set_matrix(int r, int c){
	/* allocate memory for 'r' rows of arrays */
	int ** temp = (int**)malloc(sizeof(int*)*r);
	int i;
	/* for each row allocate sufficient memory */
	for(i = 0; i < r; i++){
		temp[i] = (int *)malloc(sizeof(int)*c);
	}
	return temp;
}

void read_matrix (int **mx, int r, int c){
	int i,j;
	for(i = 0; i < r; i++){
		for(j = 0; j < c ; j++){
			scanf("%d", (*(mx + i) + j));
		}
	}
	return;
}

void multiply_matrices(int **A, int **B, int **C, int n, int m, int s){
	int i, j, k, temp = 0;
	for(k = 0; k < s; k++){
		for(j = 0; j < n; j++){
			for(i = 0; i < m; i++){
				temp += (A[j][i])*(B[i][k]);
			}
			C[j][k] = temp;
			temp = 0;
		}
	}
}

void dumpMatrix(int ** mx, int r, int c){
	int i, j;
	for(i = 0; i < r; i++){
		for(j = 0; j < c; j++){
			printf("%d ", *(*(mx + i) + j) );
		}
		puts("");
	}
	return;
}

void freeMatrix(int ** mx, int r, int c){
	int i;
	for(i = 0; i < r; i++){
		free(*(mx + i));
		*(mx + i) = NULL;
	}
	free(mx);
	return;
}
