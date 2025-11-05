# 🥖 BreadExchange  
**An innovative marketplace for bakeries, pastry shops, and artisan ovens!**

![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Backend-success?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-Frontend-red?logo=angular&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Container-Docker-2496ED?logo=docker&logoColor=white)
![Node.js](https://img.shields.io/badge/Node.js-18%2B-brightgreen?logo=node.js&logoColor=white)
![Status](https://img.shields.io/badge/Status-Work%20In%20Progress-orange)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-blueviolet)

---

## 📖 Project Overview  

**BreadExchange** is a web platform designed to connect users with local bakeries, pastry shops, and bread artisans.  
It allows users to browse vendor menus, place and track orders, and for businesses to manage their storefronts directly on the platform.  

The project leverages **Java (Spring Boot)** for the back-end, **Angular** for the front-end, and **PostgreSQL** as the main database.

---

## 🎯 Main Features  

### 👤 Guest Users  
- Browse and search among local vendors  
- View available menus and products  

### 🔐 Registered Users  
- Customize personal profiles  
- Place and track orders in real-time  
- Manage a **Favorites** list for preferred vendors  
- Request registration as a vendor  

### 🏪 Vendors  
- Manage store details and product catalog  
- Handle and track incoming orders  

### 🛡️ Administrators  
- Full control and moderation of the platform  
- Manage users and vendor accounts  

---

## 🛠️ Technologies Used  

### **Back-End**  
- **Java + Spring Boot**  
- **PostgreSQL**  
- **JWT Authentication**  
- **Role-Based Authorization**  
- **Custom Error Handlers**  
- **OpenAPI** for API documentation  
- **Entity Pagination** for optimized queries  
- **Docker** for the email delivery service  

### **Front-End**  
- **Angular**  
- **HTML, CSS**  
- **Bootstrap, Tailwind, Flowbite, Angular Material**  
- **Google Maps API** for location management  

---

## ⚙️ System Requirements  

- **Java 17+**  
- **Node.js 18+** and **npm**  
- **Angular CLI**  
- **PostgreSQL**  
- **Docker** (for email service)  

---

## 🚀 Installation Guide  

### **1️⃣ Clone the Repository**  
```bash
git clone https://github.com/tuo-username/BreadExchange.git
cd BreadExchange
```

### **2️⃣ Configure the Database**
> ⚠️ Make sure PostgreSQL is installed on your system.
```bash
CREATE DATABASE BreadExchange
```
- Then update the application.properties file with your PostgreSQL credentials.

### **3️⃣ Configure Docker**
> ⚠️ Ensure Docker is installed and running on your system.
You can install MailDev from DockerHub or directly from its repository:
🔗 https://github.com/maildev/maildev

- Start Docker
- Launch the MailDev container
- Update the application.properties file with the container IP and listening port

### **4️⃣ Run the Back-End**

```bash
cd backend
./mwn spring-boot:run
```

### **5️⃣ Install and Run the Front-End**

```bash
cd frontend
npm install
ng serve
```
- The application will be available at 👉 http://localhost:4200/

---

## 🧩 Future Improvements

- Integration with payment gateways
- Push notifications for order updates
- Advanced analytics dashboard for vendors

## Developed with ❤️ by Passluk

