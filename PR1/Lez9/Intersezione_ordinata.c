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
void read_list(Lista l);
Lista insert_ordered(Lista l, int val);
Lista intersect(Lista l, Lista q);
int exist_value(Lista l, int d);
void dump(Lista l);

/* main function */
int main(void){
    Lista list1 = NULL;
    Lista list2 = NULL;
    int x;
    scanf("%d", &x);
    while(x > -1){
        list1 = insert_ordered(list1, x);
        scanf("%d", &x);
    }
    scanf("%d", &x);
    while(x > -1){
        list2 = insert_ordered(list2, x);
        scanf("%d", &x);
    }
    Lista list3 = intersect(list1, list2);
    dump(list3);
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

int exist_value(Lista l, int d){
    /* checks if a value exists in a list */
    Nodo * aux;
    aux = l;
    while(aux != NULL){
        if(aux->info == d){
            return 1;
        }
        aux = aux->next;
    }
    return 0;
}

Lista intersect(Lista l, Lista q){
    Nodo *aux1, *aux2, *aux3;
    aux3 = NULL;
    aux1 = l;
    aux2 = q;
    /* check for common values between two lists */
    while(aux1 != NULL){
        if(exist_value(aux2, aux1->info)){
            if(exist_value(aux3, aux1->info) == 0){
                aux3 = insert_ordered(aux3, aux1->info);
            }
        }
        aux1 = aux1->next;
    }
    return aux3;
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