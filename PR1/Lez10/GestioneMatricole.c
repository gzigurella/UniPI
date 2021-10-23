
#include <stdio.h>
#include <stdlib.h>

/* define node */
typedef struct n{
	int dato;
	struct n* next;
}Node;

/* define list */
typedef Node* List; 

/* function prototypes */
int  RecLength			(List ls, int count);
void add_end			(List *ls);
void append			(List *ls, int n);
void RecStampaInversa		(List ls, int length);
void check			(int *n);
void delete_mult		(int n, List *ls);
void delete_elem		(List *ls);
void freeList			(List ls);
void RecStampa			(List ls);
void getOddEeven		(int *dispari, int *pari, List ls);

/* main functions */
int main(void){
	List ANONIMI = NULL;
	add_end(&ANONIMI);

	int length = RecLength(ANONIMI, 0);
	puts("Stampa l'elenco in ordine inverso di immissione (NULL se vuota):");
	RecStampaInversa(ANONIMI, length);
	puts("Inserisci il numero richiesto:");

	int n;
	check(&n);
	delete_mult(n, &ANONIMI);

	puts("Stampa l'elenco in ordine di immissione (NULL se vuota):");	
	RecStampa(ANONIMI);

	int fst_odd = -1;
	int fst_even = -1;
	getOddEeven(&fst_odd, &fst_even, ANONIMI);

	printf("Dispari:%d\n", fst_odd);
	printf("Pari:%d\n", fst_even);
	freeList(ANONIMI);
	return 0;
}

/* functions body */
void add_end(List *ls){
	int x;
	check(&x);
	while(x != 0){
		append(ls, x);
		check(&x);
	}
	return;
}

void append(List *ls, int n){
	Node *newIntero =(Node*)malloc(sizeof(Node));
	newIntero->dato = n;

	if (*ls == NULL){
		*ls = newIntero;		
	}
	else{
		while((*ls)->next != NULL){
			ls = &(*ls)->next;
		}
		(*ls)->next = newIntero;
	}
	return;
}

int  RecLength(List ls, int count){
	if (ls == NULL)
		return count;
	else{
		return RecLength(ls->next, count+1); 
	}
}

void RecStampaInversa(List ls, int length){
	static int count;
	if(ls != NULL){
		RecStampaInversa(ls->next, length);
		printf("%d->", ls->dato);
		count++;
	}
	if(count == length)
		puts("NULL");
}


void check(int *n){
	/* accept or deny value */
	while(scanf("%d", n) != 1 || *n < 0 ){
		puts("Inserisci intero positivo o 0 per uscire");
		scanf("%*[^\n]");
	}
	return;
}
void delete_mult(int n, List *ls){
	if(*ls != NULL){
		if(((*ls)->dato%n) == 0){
			delete_elem(ls);
			delete_mult(n, ls);
		}
		else{
			delete_mult(n, &(*ls)->next);
		}
	}
	return;
}

void delete_elem(List *ls){
	Node *tmpPtr = *ls;
	*ls = (*ls)->next;
	free(tmpPtr);
	tmpPtr = NULL;
	return;
}

void RecStampa(List ls){
	if (ls != NULL){
		printf("%d->", ls->dato);
		RecStampa(ls->next);
		return;
	}
	puts("NULL");
	return;
}

void getOddEeven(int *odd, int *even, List ls){
	if (ls != NULL){
		if (*odd == -1 && (ls->dato %2 == 1)){
			*odd = ls->dato;
			getOddEeven(odd, even, ls->next);
		}
		if (*even == -1 && (ls->dato %2 == 0)){
			*even = ls->dato;
			getOddEeven(odd, even, ls->next);
		}
	}
}

void freeList(List ls){
	Node * temp;
	while(ls != NULL){
		temp = ls;
		ls = ls->next;
		free(temp);
	}
	return;
}
