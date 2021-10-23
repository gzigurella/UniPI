#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	float a,b;
	char op;
	scanf("%f\n%f\n%c", &a, &b, &op);
	switch(op){
		case '+':
			printf("%.1f\n", (a+b));
			break;
		case '-':
			printf("%.1f\n", (a-b));
			break;
		case '/':
			printf("%.1f\n", (a/b));
			break;
		case '%':
			printf("%.1f\n", (float)((int)a % (int)b));
			break;
		default:
			puts("invalid operator");
			break;
	}
	fflush(stdin);
	return 0;
}