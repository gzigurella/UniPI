#include <stdio.h>
#include <stdlib.h>

/* function prototypes*/
void multiplymatr(int A[][2], int B[][3], int C[][3], int n);

/* main function */
int main(void){
	int a[4][2];
	int b[2][3];
	static int c[4][3];
	static int n;

	for(size_t i = 0; i < 4; i++){
		for (size_t j = 0; j < 2; j++) {
			scanf("%d", &a[i][j]);
		}
	}
	for(size_t i = 0; i < 2; i++){
		for (size_t j = 0; j < 3; j++) {
			scanf("%d", &b[i][j]);
		}
	}
	multiplymatr(a, b, c, n);
	fflush(stdin);
	return 0;
}

/* functions body */
void multiplymatr(int A[][2], int B[][3], int C[][3], int n){
	for(size_t i = 0; i < 4; i++){
		for(size_t j = 0; j < 3; j++){
			for(n = 0; n < 2; n++){
				C[i][j] = C[i][j]+ A[i][n] * B[n][j];
			}
		}
	}
	for(size_t i = 0; i < 4; i++){
		printf("%d %d %d\n", C[i][0], C[i][1], C[i][2]);
	}
}