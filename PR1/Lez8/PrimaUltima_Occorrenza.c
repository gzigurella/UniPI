#include <stdio.h>
#include <stdlib.h>
#define SIZE 7
#define OCC_NULL -1

/* function prototypes */
void primoultimopari (int arr[], int dim, int* primaocc, int* ultimaocc);

/* main function */
int main(void){
	int * a = (int*)malloc(sizeof(int)*SIZE);
	int * po = NULL;
	int * uo = NULL;
	for(size_t i = 0; i < SIZE; i++){
		scanf("%d", (a+i));
	}
	primoultimopari(a, SIZE, po, uo);
	free(a);
	return 0;
}

/* functions body */
void primoultimopari (int arr[], int dim, int* primaocc, int* ultimaocc){
	int temp_p;
	int temp_u;
	for(int i = 0; i < dim; i++){
		if(primaocc == NULL && *(arr+i)%2 == 0){
			primaocc = &i;
			temp_p = *primaocc;
			temp_u = *primaocc;
		}else if(primaocc != NULL && *(arr+i)%2 == 0){
			ultimaocc = &i;
			temp_u = *ultimaocc;
		}
	}if(primaocc == NULL && ultimaocc == NULL){
		printf("%d %d", OCC_NULL, OCC_NULL);
	}else{
		printf("%d %d", temp_p, temp_u);
	}
	return;
}