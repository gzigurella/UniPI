#include <stdio.h>
#include <stdlib.h>
#define SIZE 10

/* function prototypes*/
int* read_arr(int * a);
int verifica_condizione(int * a);

/* main function */
int main(void){
	int * arr = (int*)malloc(sizeof(int) * SIZE);
	arr = read_arr(arr);
	printf("%d\n", verifica_condizione(arr));
	free(arr);
	fflush(stdin);
	return 0;
}

/* functions body */
int* read_arr(int * a){
	for(size_t i = 0; i < SIZE; i++){
		scanf("%d", (a+i));
	}
	return a;
}

int verifica_condizione(int * a){
	for(size_t k = 1; k < SIZE-1; k++){
		if(*(a+k) == *(a+k+1) - *(a+k-1)){
			return k;
		}
	}
	return -1;
}