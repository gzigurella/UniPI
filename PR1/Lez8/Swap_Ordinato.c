#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
void ordered_swap(int * a, int * b, int * c);

/* main function */
int main(void){
	int A, B, C;

	scanf("%d", &A);
	scanf("%d", &B);
	scanf("%d", &C);

	ordered_swap(&A, &B, &C);
	printf("%d\n%d\n%d\n", A, B, C);
	fflush(stdin);
	return 0;
}

/* functions body */
void ordered_swap(int * a, int * b, int * c){
	int min = *a;
	int med = *b;
	int max = *c;

	if(min < med && min < max){
		if(med < max){
			return;
		}else{
			*b = 0;
			*b += max;
			*c = 0;
			*c += med;
			return;
		}
	}
	else if(med < min && med < max){
		*a = 0;
		*a += med;
		if(min < max){
			*b = 0;
			*b += min;
			return;
		}else{
			*b = 0;
			*b += max;
			*c = 0;
			*c += min;
			return;
		}
	}
	else{
		*a = 0;
		*a += max;
		if(min < med){
			*b = 0;
			*b += min;
			*c = 0;
			*c += med;
			return;
		}else{
			*b = 0;
			*b += med;
			*c = 0;
			*c += min;
			return;
		}
	}

	return;
}