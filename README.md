🏗️ Pyramid

Pyramid is a backend application built with Spring Boot that manages player registrations for weekly tournaments (Magic: The Gathering, TCGs, tabletop games, etc.).
The goal is to replace messy WhatsApp sign-ups with a structured, secure, and scalable system.

🚀 Current Features
👤 Authentication

Secure JWT-based authentication (signup / login endpoints).

Users can register as either:

Player → joins tournaments.

Store → creates and manages tournaments.

Passwords are encrypted using BCrypt.

🧩 Tournament Management

Stores can create tournaments with:

Name, format, date & time, price, max players, and extra info.

Automatic tracking of:

Total prize pool (calculated from player entries).

Available spots.

“Full” status when capacity is reached.

📝 Player Registration

Players can register for tournaments.

If a tournament is full, they are placed on a reserve list.

When a player unregisters:

The first reserve automatically gets promoted to active.

If no reserves exist, the tournament becomes open again.

Registrations include:

Player nickname

Reserve status

Registration time (for ordering and promotion priority)

🧱 Tech Stack
Layer	Technology
Backend	Java 21, Spring Boot 3
Database	PostgreSQL (Dockerized)
Authentication	JWT (JSON Web Token)
Build Tool	Maven
ORM	Spring Data JPA / Hibernate
API Testing	Postman
Containerization	Docker Compose
⚙️ Setup & Run

Clone the repository

git clone https://github.com/DavidCarcelen/pyramid.git
cd pyramid


Start PostgreSQL via Docker

docker compose up -d


Run the application

./mvnw spring-boot:run


or directly from IntelliJ IDEA.

Access the API

http://localhost:8080

📬 API Endpoints (main ones)
Endpoint	Method	Description
/auth/signup	POST	Register new user
/auth/login	POST	Login and get JWT
/api/tournaments	POST	Create new tournament (store only)
/api/registrations	POST	Register player to a tournament
/api/registrations/delete	DELETE	Unregister player from a tournament
/api/tournaments/upcoming	GET	View upcoming tournaments
🧠 Project Architecture

Controller layer → handles HTTP requests.

Service layer → contains business logic (registration, promotion, tournament updates).

Repository layer → JPA interfaces for persistence.

Security layer → JWT filters and configuration.

DTOs → manage clean data transfer between layers.

The project follows a clean layered architecture:

Controller → Service → Repository → Database

🔮 Next Steps

Add favorite stores for players.

Expose tournament history and statistics.

Implement email notifications for promotions or updates.

Add a Flutter frontend (mobile-first approach).

👨‍💻 Author

David Carcelén
Backend Developer (Java / Spring Boot)
🎸 Former professional musician — now composing clean code rhythms.
