#include <stdio.h>
#include <stdlib.h>
#define NMAX 100

/* function prototypes*/
int read_positive_size(void);
int read_int(void);
int* read_arr(int * a, int s);
void subsequence(int * a, int s);

/* main function */
int main(void){
	int size = read_positive_size();
	int * arr = (int*)malloc(sizeof(int) * size);
	arr = read_arr(arr, size);
	subsequence(arr, size);
	free(arr);
	fflush(stdin);
	return 0;
}

/* functions body */
int read_positive_size(void){
	int num;
	while(scanf("%d", &num) != 1 || num < 0 || num > NMAX){
		puts("Inserisci un intero positivo compreso fra 1 e 100.");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return num;
}

int read_int(void){
	int x;
	while(scanf("%d", &x) != 1){
		puts("Inserisci un intero.");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return x;
}

int* read_arr(int * a,int s){
	for(size_t i = 0; i < s; i++){
		*(a+i) = read_int();
	}
	return a;
}

void subsequence(int * a, int s){
	for(size_t k = 0; k < s ; k++){
		if(*(a+k) >= 0 && *(a+k) % 2 == 0){
			printf("%d\n", *(a+k));
		}
		else if(k<s-1 && *(a+k) < 0 && *(a+k+1) >= 0){
			printf("%d\n", *(a+k));
		}
	}
	return;
}