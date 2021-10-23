#include <stdio.h>
#include <stdlib.h>

void rettangoli(int a, int b);

/*main function*/
int main(void){
	int h, l;
	while(scanf("%d", &h) != 1 || h <= 0){
		scanf("%*[^\n]");
		scanf("%*c");
		puts("h incorretto.  Introdurre un intero maggiore di 0.");
	}
	while(scanf("%d", &l) != 1 || h <= 0){
		scanf("%*[^\n]");
		scanf("%*c");
		puts("l incorretto.  Introdurre un intero maggiore di 0.");
	}
	rettangoli(h, l);
	fflush(stdin);
	return 0;
}

void rettangoli(int a, int b){
	for(size_t i = 0; i < a; i++){
		if(i == 0 || i == a-1){
			for(size_t c = 0; c < b; c++){
				printf("*");
			}
			puts("");
		}else{
			printf("*");
			for(size_t c = 1; c < b-1; c++){
				printf(" ");
			}
			puts("*");
		}
	}
	return;
}