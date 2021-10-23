#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	float celsius;
	while(scanf("%f", &celsius) != 1){
		scanf("%*c");
		puts("ERROR_NAN, input is not a number");
	}

	/*Convert Celsius to FDahrenheit*/
	float F = celsius * 1.8 + 32;
	printf("%.2f\n", F);

	fflush(stdin);
	return 0;
}