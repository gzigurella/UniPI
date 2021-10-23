#include <stdio.h>
#include <stdlib.h>

/* define node */
typedef struct n{
    int info;
    struct n * next;
}Node;

/* define list */
typedef Node * List;

/* function prototypes */
int  isEmpty            (List l);
int  CalcoloSomma       (List l);
int  CalcoloNElementi   (List l);
List Push               (List l, int n);
List Append             (List l, int n);
void freeList           (List l);

/* functions body */
List Push(List l, int n){
    /* allocate a new element to the list */
    Node * newPtr = (Node*)malloc(sizeof(Node));
    newPtr->info = n;
    newPtr->next = l;
    return newPtr;
}

List Append(List l, int n){
   Node * newPtr =(Node*)malloc(sizeof(Node));
    if(l == NULL){
        l = Push(l, n);
    }
    else{
        for(newPtr = l; newPtr->next != NULL; newPtr = newPtr->next);
        newPtr->next =(Node*)malloc(sizeof(Node));
        newPtr->next->info = n;
        newPtr->next->next = NULL;
    }
    return l;
}

int isEmpty(List l){
    return l == NULL;
}

int CalcoloSomma(List l){
    List aux = l;
    int somma = 0;
    while(aux != NULL){
        somma += aux->info;
        aux = aux->next;
    }
    return somma;
}

int CalcoloNElementi(List l){
    List aux = l;
    int count = 0;
    int search = CalcoloSomma(l)/4;
    while(aux != NULL){
        if(aux->info > search){
            count++;
        }
        aux = aux->next;
    }
    return count;
}

void freeList(List l){
	Node * temp;
	while(l != NULL){
		temp = l;
		l = l->next;
		free(temp);
	}
	return;
}

/* main function */
int main(void){
    int x;
    List lista = NULL;
    scanf("%d", &x);
    while(x > -1){
        lista = Append(lista, x);
        scanf("%d", &x);
    }

    if(isEmpty(lista)){
        printf("%d\n%d\n", 0, 0);
    }else{
        printf("%d\n%d\n", CalcoloSomma(lista), CalcoloNElementi(lista));
    }
    freeList(lista);
    return 0;
}