# 📌 BreadExchange  
**Un marketplace innovativo per panifici, pasticcerie e forni!**  

---

## 📖 Descrizione del Progetto  
**BreadExchange** è una piattaforma web che permette agli utenti di consultare i menu dei rivenditori locali, effettuare ordini e gestire la propria attività.  

Il progetto utilizza **Java** per il back-end, **Angular** per il front-end e **PostgreSQL** come database.  

### 🎯 Funzionalità principali  

#### 🔹 Utente non loggato  
- Ricerca e navigazione tra i rivenditori  
- Consultazione dei menu disponibili  

#### 🔹 Utente loggato  
- Personalizzazione del profilo  
- Possibilità di effettuare ordini e monitorarne lo stato  
- Sezione "Preferiti" per salvare rivenditori preferiti  
- Richiesta di registrazione come rivenditore  

#### 🔹 Rivenditore  
- Gestione della propria pagina e dei prodotti offerti  
- Gestione degli ordini ricevuti  

#### 🔹 Admin  
- Controllo e moderazione del sito  
- Gestione di utenti e rivenditori  

---

## 🛠 Tecnologie Utilizzate  

### **Back-end**  
- **Java + Spring Boot**  
- **PostgreSQL**  
- **JWT** per autenticazione  
- **Role-Based Authorization**  
- **Error Handler personalizzati**  
- **OpenAPI** per la comunicazione con il front-end  
- **Impaginazione delle entità** per ottimizzare le richieste  
- **Docker** per il servizio di invio email  

### **Front-end**  
- **Angular**  
- **HTML + CSS**  
- **Bootstrap, Tailwind, Flowbite, Angular Material Design**  
- **Google Maps API** per la gestione delle mappe  

---

## ⚙ Requisiti di Sistema  
- **Java 17+**  
- **Node.js 18+** e **npm**  
- **Angular CLI**  
- **PostgreSQL**  
- **Docker** (per il servizio email)  

---

## 🚀 Guida all'Installazione  

### **1️⃣ Clonare il Repository**  
```bash
git clone https://github.com/tuo-username/BreadExchange.git
cd BreadExchange
```

### **2️⃣ Configurare Il Database
> Nota, prima di digitare il comando verifica di avere installato PostgresSql sul tuo dispositivo
```bash
CREATE DATABASE BreadExchange
```
- Dopodiche verifica e aggiora il file **application.properies** con le credenziali corrette del database

### **3️⃣ Configurare Docker
> Nota, Verificare di aver installato docker e dopodiche installa MailDev dal DokerHub o manualmente dalla sua repo
> https://github.com/maildev/maildev

- Avviare docker
- Avviare il container MailDev

- Aggiornare il file **application.properties** inserendo ip del container e la porta di ascolto

### **4️⃣ Avviare Il Back-End

```bash
cd backend
./mwn spring-boot:run
```

### **5️⃣ Installare e Avviare Angular 

```bash
cd frontend
npm install
ng serve
```
- L'app sarà disponibile su http://localhost:4200/


