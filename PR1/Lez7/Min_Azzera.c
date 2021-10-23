#include <stdio.h>
#include <stdlib.h>
#define SIZE 10

/* function prototypes*/
int* read_arr(int * a);
void min_array(int * a);

/* main function */
int main(void){
	int * arr = (int*)malloc(sizeof(int) * SIZE);
	arr = read_arr(arr);
	min_array(arr);
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

void min_array(int * a){
	int min = *(a+0);
	for(size_t k = 0; k < SIZE; k++){
		if(k%2 == 0 && *(a+k) < min){
			min = *(a+k);
		}
		else{
			if(*(a+k) < min){
				min = *(a+k);
			}
			if(k%2 != 0){
				*(a+k) = 0;
			}
		}
	}
	for(size_t j = 0; j < SIZE; j++){
		printf("%d\n", *(a+j));
	}
	printf("%d\n", min);
	return;
}