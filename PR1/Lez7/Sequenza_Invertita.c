#include <stdio.h>
#include <stdlib.h>
#define SIZE 10

/* function prototypes*/
int* read_arr(int * a);
void print_reverse(int * a);

/* main function */
int main(void){
	int * arr = (int*)malloc(sizeof(int) * SIZE);
	arr = read_arr(arr);
	print_reverse(arr);
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

void print_reverse(int * a){
	for(size_t k = SIZE; k > 0; k--){
		if(*(a+k-1)%2 == 0 ){
			printf("%d\n", *(a+k-1)/2);
		}
		else{
			printf("%d\n", *(a+k-1));
		}
	}
	return;
}