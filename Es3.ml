type colors = White | Black | Red | Blue | Green ;; (* Define color type *)

(* Es 1 -- Definire una funzione che preso il tipo colors scrive la stringa contenente il nome del colore *)
let string_of_colors c =
    match c with 
    | White -> "White"
    | Black -> "Black"
    | Red -> "Red"
    | Blue -> "Blue"
    | Green -> "Green"
    ;;

(* Es 2 -- Definire un record 'style' che abbia due parametri [stroke], [fill] che 
    possono essere di tipo colors oppure option *)
type style = {
    stroke: colors option;
    fill: colors option;
}

(* Es 3 -- Definiamo un tipo figure che descrive possibili figure da inserire all'interno dell'immagine svg. 
Questo tipo è l'unione di due tipi che descrivono rispettivamente i tag <rect> e <circle>. 
I tipi da unire dovranno essere record i cui campi corrispondono agli attributi dei tag <rect> e <circle>, rispettivamente. 
Usare Rect e Circle come costruttori *)

type rect = {
    x: int; y: int;
    width: int; height:int;
    style: style
}
;;

type circle = {
    cx: int; cy: int; r: int;
    style: style
}

type figure = Rect of rect | Circle of circle

(* Utility function to print a figure info *)
let figure_info (f:figure) =
    let stat = 
        match f with
        | Circle c -> "\nx:"^string_of_int(c.cx)^"\ny:"^string_of_int(c.cy)^"\nradious:"^string_of_int(c.r)
        | Rect rt -> "\nx:"^string_of_int(rt.x)^"\ny:"^string_of_int(rt.y)^"\nwidth:"^string_of_int(rt.width)^"\theight:"^string_of_int(rt.height)
        in
    print_endline stat
;;

(* Definiamo ora un tipo svg_img che descrive un'intera immagine svg.
    Anche questo sarà un tipo record questa volta corrispondente al tag <svg>, 
    e dovrà avere due campi per memorizzare larghezza e altezza dell'immagine, 
    e un campo per memorizzare una lista di figure di tipo figure che corrispondono ai rettangoli e ai cerchi contenuti nell'immagine. *)

type svg_img = {width: int; height: int; figures: figure list} ;;

(* Definiamo una funzione init_svg_img che prende come parametri una larghezza e un'altezza e 
restituisce un elemento di tipo svg_img con quelle dimensioni e lista di figure vuota. *)
let init_svg_img w h = { width=w; height=h; figures=[] } ;;

(* Es 4 -- Definiamo una funzione add_fig che prende una immagine di tipo svg_img, 
una figura di tipo figure e restituisce una nuova immaigine uguale alla precedente ma 
con la nuova figura aggiunta in testa alla lista di figure dell'immagine di partenza. *)

let add_fig (img:svg_img) (fig: figure) =
    let currFigures = img.figures in { img with figures = fig::currFigures }
;;

(* Es 5 -- Definiamo una funzione add_figure_list che prende una immagine e una lista di figure di tipo figure e 
restituisce una nuova immagine uguale alla precedente, ma con in più tutte le figure della lista. 
(nota: usare add_fig dell'esercizio precedente). *)

let add_figure_list (img:svg_img) (fl:figure list) =
    let currFigure = img.figures in { img with figures = fl@currFigure }
;;

(* Cerchiamo ora di riprodurre il file svg presentato come esempio all'inizio. 
Partiamo da creare un'immagine vuota di dimensione 600x400: *)
let img0 = init_svg_img 600 400 ;;

(* Creiamo il cerchio e il rettangolo come nell'esempio iniziale: *)
let circle1 = Circle { cx= 100; cy= 100; r=50; style= {stroke= Some Red; fill= Some Green }} ;;
let rect1 = Rect {x=10; y=10; width=100; height=50; style={stroke= Some Red; fill= Some Green }} ;;

let img1 = add_fig img0 circle1 ;;
let img2 = add_fig img1 rect1 ;;

(* Es 6 -- La funzione `generate_svg_code` prende una immagine di tipo `svg_img` e restituisce una stringa con la rappresentazione testuale del codice svg. 
Purtoppo la definizione seguente non funziona correttamente. 
Comprendiamo il codice e correggiamo l'errore: *)
let generate_svg_code (img:svg_img) = 
    let generate_head img =
        "<svg width=\"" ^ (string_of_int img.width) ^ "\" " ^
        "height=\"" ^ (string_of_int img.height) ^ "\" " ^ 
        "xmlns=\"http://www.w3.org/2000/svg\">\n"
    in let rec generate_figures figs =
        let generate_style s =
            match (s.stroke,s.fill) with
            | (None,None) -> ""
            | (Some c1,None) -> "stroke:" ^ (string_of_colors c1)
            | (None, Some c2) -> "fill:" ^ (string_of_colors c2)
            | (Some c1,Some c2) -> "stroke:" ^ (string_of_colors c1) ^ ";" ^ "fill:" ^ (string_of_colors c2)
        in match figs with
        | [] -> ""
        | (Circle c)::figs' -> "  <circle cx=\"" ^ (string_of_int c.cx) ^ "\" " ^
                               "cy=\"" ^ (string_of_int c.cy) ^ "\" " ^
                               "r=\"" ^ (string_of_int c.r) ^ "\" " ^
                               "style=\"" ^ (generate_style  c.style) ^ "\"" ^
                               "/>\n" ^ (generate_figures figs') (* <-- fixed here *)
        | (Rect r)::figs' -> "  <rect x=\"" ^ (string_of_int r.x) ^ "\" " ^
                               "y=\"" ^ (string_of_int r.y) ^ "\" " ^
                               "width=\"" ^ (string_of_int r.width) ^ "\" " ^
                               "height=\"" ^ (string_of_int r.height) ^ "\" " ^
                               "style=\"" ^ (generate_style  r.style) ^ "\"" ^
                               "/>\n" ^ (generate_figures figs') (* <-- fixed here *)
    in let generate_tail = "</svg>" 
    in (generate_head img) ^ (generate_figures img.figures) ^ (generate_tail) 
;;

print_endline (generate_svg_code img2) ;;

(* Es7 -- Scrivere una funzione `pairs_to_circles` che prende una lista di coppie di interi e genera una lista di cerchi (di tipo `figure`) 
in cui ogni cerchi corrisponde a un elemento della lista di coppie, 
e i due interi di ogni coppia sono usati come coordinate del centro del cerchio corrispondente. 
Tutti i cerchi hanno raggio 50 e nello stile hanno `stroke` nero e `fill` rosso. 
(nota: questa funzione può essere esplicitamente ricorsiva oppure usare le funzioni higher-order su liste) *)

let rec pairs_to_circles list =
    match list with
    | [] -> []
    | (x, y)::[] -> let c = Circle{cx = x; cy = y; r=50; style = {stroke= Some Black; fill= Some Red }} in c::[]
    | (x, y)::tail -> let c = Circle{cx = x; cy = y; r=50; style = {stroke= Some Black; fill= Some Red }} in c::(pairs_to_circles tail)
;;

let circles = pairs_to_circles [(200,200); (100,100); (300,100); (250,150)] ;;
let img = add_figure_list img0 circles;;
print_endline (generate_svg_code img);;

(* Es 8 -- Definire la funzione find che prende come parametri una funzione compare e una lista lis.
Assumiamo che la funzione compare ricevuta dalla find sia in grado di confrontare due elementi x e y
restituendo true se x precede y secondo un certo ordinamento totale conosciuto dalla compare. 
La funzione find deve restituire l'elemento della lista lis che è minimo rispetto a tale ordinamento. 
Se la lista è vuota deve sollevare un'eccezione. 
(nota: vedere gli esempi d'uso sotto per farsi un'idea) *)

let rec find cmp lis =
    match lis with
    | [] -> failwith "Empty list"
    | (x)::[] -> x
    | (x)::tail ->  if (cmp x (List.hd tail)) 
                        then x
                    else (find cmp tail) (* Traverse the list while the condition is true *)
;;

let compare_cerchi fig1 fig2 =
    match (fig1,fig2) with
    | (Circle c1,Circle c2) -> c1.cx<c2.cx 
    | (_,_) -> failwith "figure non cerchi" 
;;
    
let c = find compare_cerchi img.figures ;; (* deve restituire il cerchio più a sinistra *)
(* figure_info c;; -- remove comments to see result of test case c *)

(* Es 9 -- Definire il tipo color_tree che rappresenta l'albero di sintassi astratta di ctree 
usando costruttori Node e Leaf, e il tipo colors già visto per i colori. 

ctree ::= ctree + ctree | color *)

type 'a color_tree =
    | Leaf of colors (* For leaves with no color *)
    | Node of 'a color_tree * 'a color_tree (* For root and its leaves *)
;; 

let ct = Node (Leaf Red, (Node (Leaf Green, Leaf Blue))) 
;;

(* Es 10 -- Definire una funzione ricorsiva lista_bandiera che prende un albero color_tree, 
un valore intero x e una larghezza width e costruisce una lista di tuple con un elemento per ogni foglia dell'albero.

L'idea è che ogni elemento della lista rappresenta un rettangolo
 di una ipotetica bandiera a strisce verticali. 
x è la coordinata da cui inizia l'area in cui disegnare le strisce (nella prima chiamata della funzione solitamente è 0) 
e width è la larghezza di tale area. Scendendo in profondità nell'albero, 
la larghezza da dedicare ai rettangoli da disegnare si dimezza. 

Ossia: l'albero corrispondente a "red + blue" porterà ad una bandiera per metà rossa e metà blu, 
mentre quello di "red + (green + blue)" porterà ad una bandiera per metà rossa, un quarto verde e un quarto blu. 

Nella lista da generare, ogni tupla dirà, per ogni rettangolo colorato da generare, 
la sua coordinata x di partenza, la sua larghezza e il suo colore. 

Ad esempio, partendo da un x pari a 0 e una larghezza complessiva di 600, 
l'albero di "red + (green + blue)" dovrà portare alla lista [(0, 300, Red); (300, 150, Green); (450, 150, Blue)]. *)

let rec lista_bandiera ct (x:int) (width:int) =
    match ct with
    | Leaf c -> [(x, width, c)]::[]
    | Node (c1, c2) -> let l1 = lista_bandiera c1 x (width/2) in
                       let l2 = lista_bandiera c2 (x + width/2) (width/2) in 
                       l1 @ l2
;;

let l = lista_bandiera ct 0 600;;

(* Es 11 -- Definire una funzione pairs_to_rectangle che, 
analogamente alla funzione pairs_to_circles definita in un esercizio precedente, 
genera una lista di rettangoli di tipo figure a partire da una lista di tuple 
come restituite dalla funzione lista_bandiera. *)

let rec pairs_to_rectangle list =
    match list with
    | [] -> []
    | (x, w, c)::[] -> let r = Rect{x = x; y = 0; width = w; height = 50; style = {stroke= c; fill= c }} in r::[]
    | (x, w, c)::tail -> let r = Rect{x = x; y = 0; width = w; height = 50; style = {stroke= c; fill= c }} in r::(pairs_to_rectangle tail)
;;