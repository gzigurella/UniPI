#include <stdio.h>
#include <stdlib.h>
#define SIZE 3
#define TRUE 1
#define FALSE 0

/* function prototypes*/
int* read_arr(int * a);
int verify(int * a, int * b);

/* main function */
int main(void){
	int * arr = (int*)malloc(sizeof(int) * SIZE);
	int * snd = (int*)malloc(sizeof(int) * SIZE);
	arr = read_arr(arr);
	snd = read_arr(snd);
	if(verify(arr, snd) == TRUE){
		puts("TRUE");
	}else{
		puts("FALSE");
	}

	free(arr);
	free(snd);
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

int verify(int * a, int * b){
	int s;
	for(size_t k = 0; k < SIZE; k++){
		s = 1;
		for(size_t i = 0; i < SIZE; i++){
			if(*(a+k) >= *(b+i)){
				s = -1;
				break;
			}
		}
	}
	return s;
}