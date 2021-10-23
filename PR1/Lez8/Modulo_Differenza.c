#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
void diff_abs(float * a, float * b);

/* main function */
int main(void){
	float a;
	scanf("%f", &a);
	float b;
	scanf("%f", &b);
	diff_abs(&a, &b);
	printf("%.2f\n%.2f\n", a, b);
	fflush(stdin);
	return 0;
}

/* functions body */
void diff_abs(float * a, float * b){
	float temp  = *a;
	*a -= *b;
	*b -= temp;
	return;
}