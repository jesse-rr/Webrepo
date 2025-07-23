# Reading-Platform-MVP
The Reading Platform Backend API provides a set of RESTful endpoints designed to manage user activities, books, reading progress, and recommendations on a digital reading platform. This API serves as the core infrastructure for managing user accounts, book details, reading history, and more, supporting features such as personalized recommendations, bookmarking, etc...

### Key Features
- `User Management`: User registration, authentication, and profile management.
- `Book Management`: Add, update, and retrieve book details (title, author, genre, etc.).
- `Reading Progress`: Track user's reading progress including chapters read and completion status.
- `Bookmarks & Highlights`: Allow users to save bookmarks and highlights for specific book sections.
- `Recommendations`: Personalized book recommendations based on user preferences and reading history.
- `Search`: Search books by title, author, genre, or other metadata.
- `Reviews & Ratings`: Users can leave reviews and ratings for books.
- etc...

### Technologies Used
- `Java 17`
- `Spring Framework`
- `PostgreSQL` - Used a relational db due to many relationships between entities
- `Redis` - Used for email verification (code/implemented) & caching (future impl)
- `Elasticsearch` - Filtering books, with genres, ratings, views, fuzzy-finds for book/author name etc... 
- `JWT` - For user authentication and authorization.
- `Swagger` - `below`

### Instalation
```bash
git clone https://github.com/jesse-rr/Reading-Platform-API-Backend.git
[DOCKER-COMPOSE][BELOW] (docker is required)
```

### Docker-compose
___You may need "sudo"___
```bash
cd Reading-Platform-API-Backend
docker compose up -d
```
This will start up ``redis`` at port __6379:6379__; ``Elasticsearch must be run locally``, or if you`re up to make a docker-compose/configuration, go for it.

### Swagger-ui
To view the available API endpoints, navigate to the following [URL](http://localhost:8080/swagger-ui.html) in your browser, this page will provide an interactive interface to view and test all available API endpoints. [http://localhost:8080/swagger-ui.html][http://localhost:8080/swagger-ui.html]
<details>
  <summary>1. Swagger-images</summary>
  
  ![one](https://github.com/user-attachments/assets/c96c6882-ceea-4948-a167-2a94c4ed50f9)
</details>

<details>
  <summary>2. Swagger-images</summary>
  
  ![two](https://github.com/user-attachments/assets/fb3ec6c3-b3c6-479b-bb53-a0558812bc1e)
</details>

<details>
  <summary>3. Swagger-images</summary>
  
  ![three](https://github.com/user-attachments/assets/2ce2a2a0-4b62-43d5-8a95-45b143286933)
</details>
