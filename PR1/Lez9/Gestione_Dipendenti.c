#include <stdio.h>
#include <stdlib.h>

typedef struct employee{

	int mat;
	char nome[20];
	int mm;
	int aa;
	int salary;

}Employee;

void increase(Employee *employeePtr, float perc);
void dump(Employee *employeePtr, int size);

int main(void){
	/* open 'binary' file as 'readable'*/
	FILE *fPtr = fopen("input.txt", "rb");
	if(fPtr != NULL){
	Employee arr[4];
	puts("Matr\tNome\tMM\tAA\tStipendio");
		for (size_t i = 0; i < 4; ++i){

			Employee *employeePtr = calloc(1, sizeof(Employee));

			if (employeePtr != NULL){
				fread(&arr[i], sizeof(Employee), 1, fPtr);
				printf("%d\t%s\t%d\t%d\t%d\n",arr[i].mat, arr[i].nome, arr[i].mm, arr[i].aa, arr[i].salary);
				increase(&arr[i], 22);
			}
			else{
			puts("No memory left");
			exit(EXIT_FAILURE);
			}
		}
		
		fclose(fPtr);
		dump(arr, 4);
	}
	return 0;
}

void increase(Employee *employeePtr, float perc){
	/* increase value of 'salary' if someone got employed before 5/2000 */
	if ((employeePtr->aa == 2000 && employeePtr->mm < 5) || employeePtr->aa < 2000 ){
		employeePtr->salary *= ((perc/100)+1);
	}
}

void dump(Employee *employeePtr, int size){
	/* display employees on screen */
	puts("Matr\tNome\tStipendio");
	for (size_t i = 0; i < 4; ++i){
		if (employeePtr[i].salary > 1200){
			printf("%d\t%s\t%d\n", employeePtr[i].mat, employeePtr[i].nome, employeePtr[i].salary);
		}
	}
}