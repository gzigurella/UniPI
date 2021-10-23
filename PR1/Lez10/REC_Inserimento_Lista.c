#include <stdio.h>
#include <stdlib.h>
#define MINUS -1

/* define node */
typedef struct n{
	int info;
	struct n * next;
}Node;

/* define list */
typedef Node * List;

/* function prototypes */
List push			(List ls, int n);
void recursiveMinus		(List * ls);
void dump			(List ls);
void freeList			(List ls);

/* main function */
int main(void){
	List l = NULL;
	int x;
	scanf("%d", &x);
	while(x >-1){
		l = push(l, x);
		scanf("%d", &x);
	}
	recursiveMinus(&l);
	dump(l);
	freeList(l);
	return 0;
}

/* functions body */
	/* adds a -1 before even elements in the list */
void recursiveMinus(List * ls){
	List * head = ls;
	List * tail = NULL;

	if(*ls != NULL){
		if((*ls)->next != NULL){
			tail = &(*head)->next;
			recursiveMinus(tail);
		}
		if( ((*head)->info) %2 == 0){
			Node * Elemento = (Node*)malloc(sizeof(Node));
			if(Elemento != NULL){
				Elemento->info = MINUS;
				Elemento->next = *head;
				(*head) = Elemento;
			}
		}else if (tail != NULL) {
			/* tell the head to point to the tail,
			prevents broken lists and memory loss */
			(*head)->next = *tail;
			return;
		}
	}
	return;
}

List push(List ls, int n){
	Node * Elemento = (Node*)malloc(sizeof(Node));
	if(Elemento != NULL){
		Elemento->info = n;
		Elemento->next = ls;
		ls = Elemento;
	}
	return ls;
}

void dump(List ls){
	Node * aux;
	aux = ls;
	while(aux != NULL){
		printf("%d -> ", aux->info);
		aux = aux->next;
	}
	puts("NULL");
	return;
}

void freeList(List ls){
	Node * temp;
	while(ls != NULL){
		temp = ls;
		ls = ls->next;
		free(temp);
	}
}
