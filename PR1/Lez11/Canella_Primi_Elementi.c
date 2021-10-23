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
int  SizeList           (List l);
void FreeList           (List l);
void Dump               (List l);
List Push               (List l, int n);
List Append             (List l, int n);
List Delete             (List l);

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

List Delete(List l){
    List aux = l;
    l = l->next;
    free(aux);
    return l;
}

int isEmpty(List l){
    return l == NULL;
}

int SizeList(List l){
    List aux = l;
    int count = 0;
    while(aux != NULL){
        count++;
        aux = aux->next;
    }
    return count;
}

void Dump(List l){
    List aux = l;
    if(isEmpty(l) == 0){
        while(aux != NULL){
            printf("%d -> ", aux->info);
            aux = aux->next;
        }
    }
    puts("NULL");
    return;
}

void FreeList(List l){
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
    scanf("%d", &x);
    if(isEmpty(lista) || x > SizeList(lista)){
        puts("NULL");
    }else{
        int i;
        for(i = 0; i < x; i++){
            lista = Delete(lista);
        }
        Dump(lista);
    }
    FreeList(lista);
    return 0;
}