# Gestione Scuderia
Progetto personale di Matteo Morello del corso di programmazione ad oggetti e ingegneria del software dell'università di Pavia.
## Goal
Il software deve essere uno strumento in grado di aiutare lo staff di una scuderia di Formula 1 a gestire le risorse disponibili in un possibile weekend di gara. Lo staff è suddiviso in diverse mansioni, tra le quali : i meccanici, gli strateghi, i magazzinieri.
## Tecnlogie utilizzate
- [MySQL Java Connector](https://github.com/mysql/mysql-connector-j)
- [JUnit5](https://junit.org/junit5/)
## Set Up necessario
Per poter compilare l'app GestioneScuderia è necessario l'utilizzo di [Eclipse IDE](https://www.eclipse.org/).
## Clone del repository
```markdown
git clone https://github.com/IngSW-unipv/Progetto-S24.git GestioneScuderia
```
## Creazione del database
Utilizzando un client per MySQL si eseguano in ordine i due script nella cartalla db_scripts, essi creeranno tutte le tables necessarie e le riempiranno con dei dati utili a una demo.

- ```CREATEDATABASE.sql```
- ```ADDDATA.sql```
## Creazione delle proprietà
Il software attingerà dai file nei quali sono specificate le proprietà che dovranno essare create manualmente in una source directory nella root del progetto denominata ```properties```.


#### Database
Nel file denominato ```properties``` sono specificate le seguenti proprietà:
```markdown
DBDRIVER = com.mysql.cj.jdbc.Driver
DBURL = jdbc:mysql://localhost:<port>/database_scuderia?user=<user>&password=<pwd>
```
Rispettivamente:
- `<port>`: porta verso la quale collegarsi
- `<user>`: nome dell'utente
- `<pwd>`: password dell'utente

## Struttura delle cartelle nel progetto
```markdown
GestioneScuderia  
│  
├── src  
│  
├── properties  
│   ├── properties
│
├── db_scripts  
│   ├── CREATEDATABASE
│   ├── ADDDATA
│ 
├── test
│  
└── [...]
