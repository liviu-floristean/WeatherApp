# Proiect aplicație vreme

## Modul de funcționare:

⋅⋅⋅În acest proiect s-a implementat o aplicație de afișare a vremii în
diverse orașe ale lumii.⋅⋅
⋅⋅⋅Pentru a se adăuga un oraș nou, utilizatorul trebuie să îl adauge din
fișierul de configurare infile.txt după următorul format:⋅⋅

	id/oraș/latitudine/longitudine/codul_țării

⋅⋅⋅Aplicația parsează acel fișier, după care ca popula combobox-ul 
corespunzător țărilor din fișier. În funcție de opțiunea aleasă din 
combobox-ul țărilor se va umple și al doilea combobox cu orașele care
sunt din țara respectivă.⋅⋅

⋅⋅⋅În momentul în care s-au selectat ambele date, aplicația va face automat
o cerere către API-ul care va furniza datele. Datele primite se vor salva 
într-un fișier nou numit json.txt, într-un format JSON, după cum denotă și 
numele acestuia.⋅⋅
⋅⋅⋅După ce s-au primit datele și s-a completat fișierul, acestea vor fi citite
și apoi parsate pentru a se lua datele de interes:⋅⋅

1. Vremea
2. O scurtă descriere a vremii
3. Temperatura (care este convertită din grade Kelvin în grade Celsius)
4. Umiditatea
5. Viteza vântului

Auxiliar, s-a făcut un label care să rețină și ora și data la care s-a
făcut cererea. 

⋅⋅⋅În urma parsării, aplicația va popula toate label-urile corespunzătoare
fiecărei informații și va reține căutarea într-un fișier de istoric al căutărilor
care are următoarea structură:⋅⋅

	ora data: orașul codul_țării - temperatura

## Testarea proiectului:

⋅⋅⋅Întrucât pentru a se rula proiectul este necesară doar completarea valorilor a
două combobox-uri, testarea a avut în vedere verificarea dacă valorile din ambele
combobox-uri este nulă, lucru care denotă dacă fișierul de intrare a fost citit 
corespunzător și că se poate face o cerere către API pentru a obține datele
necesare afișării. Testarea s-a făcut la nivel de clasă care parsează fișierul de
intrare.⋅⋅