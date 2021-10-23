#include <stdio.h>
#include <stdlib.h>

float average(void);

/*main function*/
int main(void){
	printf("%.2f\n", average());
	fflush(stdin);
	return 0;
}

float average(void){
	int sum = 0;
	int count = 1;
	int n, first;
	float avg;
	while(scanf("%d", &first) != 1){
			puts("Inserisci un intero.");
			scanf("%*[^\n]");
			scanf("%*c");
		}
	sum += first;
	for(size_t i = 0; i < 9; i++){
		while(scanf("%d", &n) != 1){
			puts("Inserisci un intero.");
			scanf("%*[^\n]");
			scanf("%*c");
		}
		if(n * first > 0){
			sum += n;	
			count++;
		}
	}
	avg = (float)sum/(float)count;
	return avg;
}