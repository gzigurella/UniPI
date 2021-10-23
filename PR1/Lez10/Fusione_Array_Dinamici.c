#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
int  compare		(const void * a, const void *b);
int  ordered		(int * a, int a_dim);
void merge		(int * c, int * a, int * b, int a_dim, int b_dim);
void read		(int * a, int a_dim);
void dump		(int * a, int a_dim);

/* main function */
int main(void){
	int dim1, dim2;

	scanf("%d", &dim1);
	int arr1[dim1];

	/* read input for first array */
	read(arr1, dim1);
	puts("Array A:");
	dump(arr1, dim1);

	scanf("%d", &dim2);
	int arr2[dim2];

	/* read input for second array */
	read(arr2, dim2);
	puts("Array B:");
	dump(arr2, dim2);
	
	int dim3 = dim1+dim2;
	/* merge and sort two arrays into a third one */
	int arr3[dim3];
	merge(arr3, arr1, arr2, dim1, dim2);
	puts("Array risultato C");
	dump(arr3, dim3);

	printf("Ordinato: %d\n", ordered(arr3, dim3));
	return 0;
}

/* functions body */
void merge(int * c, int * a, int * b, int a_dim, int b_dim){
	int i, j;
	for(i = 0; i < a_dim; i++){
		c[i] = a[i];
	}
	for(j = 0; j < b_dim; j++){
		c[a_dim+j] = b[j];
	}
	int c_dim = a_dim+b_dim;
    /* sort array with quicksort */
	qsort(c, c_dim, sizeof(int), compare);
	return;
}

int compare(const void * a, const void *b){
	return (*(int*)a - *(int*)b);
}

void read(int * a, int a_dim){
	int i;
	int temp;
	for(i = 0; i < a_dim; ++i){
		scanf("%d", &temp);
		*(a + i) = temp;
	}
	return;
}

void dump(int * a, int a_dim){
	int i;
	for(i = 0; i < a_dim; i++){
		printf("%d ", *(a+i));
	}
	puts("");
	return;
}

int ordered(int * a, int a_dim){
	int i;
	for(i = 0; i < a_dim-1; ++i){
		if(a[i] > a[i+1])
			return 0;
	}
	return 1;
}
