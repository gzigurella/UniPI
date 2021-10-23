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
List orderAdd   (List l, int x);
List push       (List l, int x);
List fuse       (List l, List q);
void dump       (List l);
void freeList   (List l);
void readList   (List l);
int  isEmpty    (List l);

/* functions body */
void dump(List l){
    List aux;
    for(aux = l; aux != NULL; aux = aux->next){
        printf("%d --> ", aux->info);
    }printf("NULL\n");
    return;
}

void freeList(List l){
    List temp;
    while(l != NULL){
        temp = l;
        l = l->next;
        free(temp);
    }
    return;
}

void readList(List l){
  
    return;
}

int isEmpty(List l){
    return l == NULL;
}

List push(List l, int x){
    Node * temp = (Node*)malloc(sizeof(Node));
    temp->info = x;
    temp->next = l;
    return temp;
}

List orderAdd(List l, int x){
    Node * temp, * curr, * prev;
    temp = (Node*)malloc(sizeof(Node));
    prev = NULL;
    for(curr = l; curr != NULL && x > curr->info; curr = curr->next){
        prev = curr;
    }
    temp->info = x;
    temp->next = curr;
    if(prev != NULL){
        prev->next = temp;
        return l;
    }
    else return temp;
}

List fuse(List l, List q){
    List aux;
    for(aux = q; aux != NULL; aux = aux->next){
        l = orderAdd(l, aux->info);
    }
    return l;
}

/* main function */
int main(void){
    List Fls = NULL;
    List Sls = NULL;
    
    /* reading first list values */
    int n;
    scanf("%d", &n);
    while(n > -1){
        Fls = orderAdd(Fls, n);
        scanf("%d", &n);
    }

    /* reading second list values */
    scanf("%d", &n);
    while(n > -1){
        Sls = orderAdd(Sls, n);
        scanf("%d", &n);
    }

    /* fusion of lists */
    List Tls = fuse(Fls, Sls);    
    dump(Tls);

    /* free memory and finish program execution */
    freeList(Sls);
    freeList(Tls);
    return 0;
}