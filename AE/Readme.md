## Esercizi del manuale *Sistemi Digitali e Architettura degli Elaboratori*
  ### Tutti gli esercizi mostrati fanno riferimento al capitolo 6 del manuale (Pg. 278)
  * **6.20**
  >Si consideri il seguente frammento di codice
  >di alto livello, assumendo che gli indirizzi base di array1 e array2 siano contenuti rispettivamente in R1 e R2 e che array2
  >sia stato inizializzato prima di essere usato.
  ```
  int i;
  int array1[100];
  int array2[100];
  ...
  for (i=0; i<100; i=i+1)
    array1[i] = array2[i];
  ```
  >\* Per motivi di praticità si è realizzato con un array a 10 valori \*
  >
  >Tradurre in codice assembly di ARM il frammento senza
  >fare uso di pre- o post-indicizzazione né di un registro sca-
  >lato e usando il minimo numero possibile di istruzioni.
  
  * **6.21**
  >Ripetere l’Esercizio 6.20 per il seguente frammento di codice, assumendo che *temp* sia stato inizializzato 
  >prima di essere usato e che R3 contenga l’indirizzo base di *temp*
  ```
  int i;
  int temp[100];
  ...
  for (i=0; i<100; i=i+1)
    temp[i] = temp[i] * 128;
  ```
  >\* Per motivi di praticità si è realizzato con un array a 10 valori \*
  * **6.22**
  >Si considerino i seguenti frammenti di codice
  >di alto livello, dove R1 contiene i e R0 l’indirizzo base dell’array *valori*. Tradurre in linguaggio assembly entrambi i frammenti,
  >usando il minimo numero possibile di istruzioni

  (i)
  ```
  int i;
  int valori[200];
  for (i=0; i < 200; i=i+1)
    valori[i] = i;
  ```
  (ii)
  ```
  int i;
  int valori[200];
  for (i=199; i >= 0; i=i+1)
    valori[i] = i;
  ```
  * **6.23**
  >Ripetere l’Esercizio 6.22 per i seguenti frammenti, assumendo che R1 contenga i , R0 l’indirizzo base
  >dell’array *numeri* , e che *numeri* sia stato inizializzato prima di
  >essere usato.

  (i)
  ```
  int i;
  int numeri[10];
  ...
  for (i=0; i < 10; i = i-1)
    numeri[i] = numeri[i]/2;
  ```
  (ii)
  ```
  int i;
  int numeri[10];
  ...
  for (i=9; i >= 0; i = i-1)
    numeri[i] = numeri[i]/2;
  ```
  * **6.25**
  > La funzione di alto livello strcopia copia la
  >stringa di caratteri *sorg* nella stringa di caratteri *dest* . Tradurre in linguaggio assembly di ARM la funzione strcopia, usando R4 per *i*.
  ```
  // codice C
  void strcopia(char dest[], char sorg[]) {
    int i = 0;
    do {
      dest[i] = sorg[i];
    } while (sorg[i++]);
  }
  ```
  * **6.30**
  > Tradurre il seguente linguaggio di alto livello dell'esercizio 6.30
    
    ```
    // codice di alto livello per le funzioni f e g
     int f(int a, int b) {
       int j;
       j = a;
       return j + a + g(b);
     }
     
     int g(int x) {
       int k;
       k = 3;
       if (x == 0) return 0;
       else return k + g(x − l);
     }  ```
  * **6.33**
  > Si consideri il seguente frammento di programma in linguaggio C. Tradurre il codice C in linguaggio assembly di ARM.
  ```
  // codice C
   void iniArray(int num) {
    int i;
    int array[10];
    for (i = 0; i < 10; i = i + 1){
      array[i] = confronta(num, i);
    }
    
    int confronta(int a, int b) {
      if (sottr(a, b) >= 0)
        return 1;
      else
        return 0;
    }
    
    int sottr(int a, int b) {
    return a − b;
    }
  ```
