#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/*main function*/
int main(void){
	int r;
	scanf("%d", &r);
	if(r < 0){
		printf("invalid input");
	} 
	else{
		int h = r/3600;
		int m = (r-h*3600)/60;
		int s = r-h*3600-m*60;
		printf("%d h %d min %d s\n", h, m, s);
	}
	fflush(stdin);
	return 0;
}