		
		Sima Nicoleta-Lavinia 324CC
		README - Tema POO - Voucher Management Service (VMS)

	Pentru tema Voucher Management Service am considerat drept structura scheletul
propus. Astfel am definit urmatoarele clase:
	1. In clasa VMS, cea care contine colectia de date, am preferat utilizarea
a doua colectii ArrayList in care voi retine campaniile existente si utilizatorii
care vor participa la distribuirea de vouchere. Am folosit design pattern-ul
Singleton pentru a restrictiona o singura instantiere a acestei clase, astfel incat
va exista un singur obiect de tip VMS. Am implementat metodele conform precizarilor
din enuntul temei, cu precizarea ca am adaugat o functie care returneaza un utilizator
din lista users cautandu-l dupa id-ul sau si inca o metoda care cauta utilizatorul dupa
adresa de email. Functia care a reprezentat cel mai mare interes a fost updateCampaign
deoarece la fiecare modificare se trimite si o notificare observatorilor din campania
cu id-ul precizat si am fost nevoita sa apelez la design pattern-ul Observer.
	2. Clasa Campaign mi s-a parut cea mai complexa atat din punct de vedere al
atributelor clasei, cat si al metodelor. La partea de atribute ale clasei
am adaugat un numar de ordine al voucherului generat in campanie si data aplicatiei
pe care o voi citi din fisierul campaigns.txt. De asemenea exista si un dictionar
care retine voucherele distribuite ficarui user din campania respectiva. Functia 
generateVoucher instantiaza un obiect de tip Voucher in functie de tipul sau (Gift 
sau Loyalty) pe care il returneaza. Pentru getVouchers si getVoucher am iterat prin
dictionar pentru a obtine toate voucherele respectiv voucherul cerut. In metoda 
notifyAllObservers am creat un obiect de tip Notification si am apelat la
Observer pattern pentru a trimite notificarea.
	3. Pentru clasele Voucher si GiftVoucher, respectiv LoyaltyVoucher ce mostenesc
clasa Voucher am creat constructori pentru a ma ajuta la instantierea lor prin care
dau valori atributelor, precum si setteri si getteri. Clasele Notification si User 
sunt in acelasi tipar. Am definit atributele si constructori pentru fiecare dintre ele.
	4. Clasa ArrayMap modeleaza dictionarul in care se retin Voucherele. Contine
o clasa interna care modeleaza o intrare de tip cheie, valoare, in cazul meu valoarea
reprezentand un ArrayList de vouchere. Exista 2 tipuri de dictionare. UserVoucherMap
care apartine unui user si retine la campania cu id-ul care este cheia, lista
de vouchere primita si CampaignVoucherMap ce are drept cheie adresa de email a utilizatorului
si pentru fiecare este retinuta lista de vouchere distribuita.
	5. In clasa Test am instantiat obiectul de tip VMS. Am citit din fisierele
campaigns.txt, users.txt si events.txt folosind BufferedReader si FileReader si am 
creat fisierul pentru scriere folosind BufferedWriter si FileWriter. Pe masura ce am 
citit am convertit datele si am creat colectiile de campanii si de utilizatori.
Pentru fisierul events.txt am retinut de la inceput id-ul utilizatorului si apoi
pentru fiecare operatie propusa am creat cate o ramura if unde se vor modificarile. 
Pentru fiecare din ele am apeat la metodele create in clasa VMS sau in clasa Campaign, 
de adaugare a campaniei, editare, inchidere sau generare de vouchere, observatori sau
notificari. Pentru generarea unui Voucher am intors voucherul returnat de metoda 
generateVoucher din clasa Campaign si am facut adaugarea in dictionare (UserVoucherMap
si CampaignVoucherMap) si in colectia de observatori in clasa test. In acelasi timp am 
decrementat numarul de vouchere care pot fi atribuite campaniei. Pentru transformarea
datei citite sub forma de string am folosit DateTimeFormatter pentru a obtine
formatul din fisierele de input. 
	