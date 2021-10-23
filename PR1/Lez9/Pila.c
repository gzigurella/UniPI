#include <stdio.h>
#include <stdlib.h>

/* Define list element */
typedef struct elemento{
    int info;
    struct elemento * next;
}ElementoDiLista;

/* Define list pointer */
typedef ElementoDiLista* ListaDiElementi;

/* function prototypes */
void push(ListaDiElementi* l, int val);
void pop(ListaDiElementi* l);
void dump(ListaDiElementi l);

/* main function */
int main(void){
    ListaDiElementi list = NULL;
    int x;
    scanf("%d", &x);
    while(x >= 0){
        if(x>0){
            push(&list, x);
        }else{
            if(list != NULL){
                pop(&list);
            }
        }
        scanf("%d", &x);
    }
    dump(list);
    return 0;
}

/* functions body */
void push(ListaDiElementi* l, int val){
    /* allocate memory for a new element */
    ElementoDiLista * newPtr =(ElementoDiLista *)malloc(sizeof(ElementoDiLista));
    if(newPtr != NULL){
        newPtr->info = val;
        newPtr->next = *l;
        *l = newPtr;
    }
    return;
}

void pop(ListaDiElementi* l){
    ElementoDiLista* tempPtr = *l;
    *l = (*l)->next;
    free(tempPtr);
    return;
}

void dump(ListaDiElementi l){
    ElementoDiLista * aux;
    aux = l;
    while(aux != NULL){
        printf("%d\n", aux->info);
        aux = aux->next;
    }
    return;
}