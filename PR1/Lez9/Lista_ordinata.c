#include <stdio.h>
#include <stdlib.h>

/* Define list element */
typedef struct node{
    int info;
    struct node * next;
}Nodo;

/* Define list pointer */
typedef Nodo* Lista;

/* function prototypes */
Lista insert_ordered(Lista l, int val);
void dump(Lista l);

/* main function */
int main(void){
    Lista list = NULL;
    int x;
    scanf("%d", &x);
    while(x > -1){
        list = insert_ordered(list, x);
        scanf("%d", &x);
    }
    dump(list);
    return 0;
}

/* functions body */
Lista insert_ordered(Lista l, int val){
    Nodo *temp, *prev, *succ;
    prev = NULL;
    /* search for the correct position */
    for(succ = l; succ != NULL && val>succ->info; succ = succ->next){
        prev = succ;
    }
    /* allocate the new node and insert it */
    temp = (Nodo*)malloc(sizeof(Nodo));
    temp->info = val;
    temp->next = succ;
    if(prev != NULL){
        prev->next = temp;
        return l;
    }
    else return temp;
}

void dump(Lista l){
    Nodo * aux;
    aux = l;
    while(aux != NULL){
        printf("%d\n", aux->info);
        aux = aux->next;
    }
    return;
}