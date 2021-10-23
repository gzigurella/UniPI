#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	size_t i;
	int max = 0;
	int temp;
	for( i = 0; i < 3; i++){
		while(scanf("%d", &temp) != 1){
			scanf("%*c");
			puts("ERROR_NAN, input is not a number");
		}
		if(temp > max){
			max = temp; 
		}
	}
	printf("%d\n", max);
	fflush(stdin);
	return 0;
}