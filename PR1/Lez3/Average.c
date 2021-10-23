#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	float val;
	float avg;
	size_t i;
	for(i = 0; i < 3; i++){
		while(scanf("%f", &val) != 1 ){
			scanf("%*c");
			puts("ERROR_NAN, input is not a number");
		}
		avg += val;
	}
	printf("%.3f\n", avg/i);
	fflush(stdin);
	return 0;
}