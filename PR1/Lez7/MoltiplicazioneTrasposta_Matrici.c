#include <stdio.h>

static int c;

size_t get_int();
void printMatrix_trasp(int matrix[][c], size_t rows, size_t cols);

int main(){
	size_t r;
	size_t i,j;
	r=get_int();
	c=get_int();
	int matrix[r][c];

	for (i = 0; i < r; ++i){
		for (j = 0; j < c; ++j){
			scanf("%d", &matrix[i][j]);
		}
	}
	
	printMatrix_trasp(matrix, r,c);
	return 0;
}

size_t get_int(){
	size_t temp;
	scanf("%lu", &temp);
	return temp;
}

void printMatrix_trasp(int matrix[][c], size_t rows, size_t cols){
	size_t i,j;
	size_t r;
	int temp=0;
	/*RIGA MATRICE*/
	for (i = 0; i < rows; ++i){
		/*RIGA MATRICE TRASPOSTA*/
		for (j= 0; j < rows; ++j){
			/*SCORRIMENTO COLONNE MATRICE NORMALE*/
			for (r=0; r < cols; ++r){
				temp+=matrix[i][r] * matrix[j][r]; 
			}
			printf("%d ",temp); 
			temp=0;
		}
		puts("");	
	}
}
