# Personal Blog Application

A simple yet complete blog application built with Spring Boot, allowing you to create, read, update, and delete blog posts with a commenting system.
This is task from https://roadmap.sh/projects/personal-blog
## Features

- **Public Blog Interface:**

  - View all published blog posts
  - Read individual articles with comments
  - Add comments to articles
  - About page

- **Admin Dashboard:**
  - Secure admin access
  - Create new articles
  - Edit existing articles
  - Delete articles
  - Manage comments

## Tech Stack

- **Backend:**

  - Java 17
  - Spring Boot 3.x
  - Spring Security for authentication
  - Thymeleaf for server-side templating

- **Frontend:**

  - HTML/CSS
  - Tailwind CSS for styling
  - Responsive design

- **Data Storage:**
  - File-based JSON storage (for simplicity)
  - No database configuration required

## Project Structure

```
├── src/main/java/com/mohamed/blog/
│   ├── BlogApplication.java              # Main application class
│   ├── config/                           # Configuration classes
│   │   └── SecurityConfig.java           # Spring Security configuration
│   ├── controller/                       # MVC controllers
│   │   ├── AdminController.java          # Admin dashboard controller
│   │   ├── CommentController.java        # Comment handling controller
│   │   └── GuestController.java          # Public-facing controller
│   ├── model/                            # Data models
│   │   ├── Article.java                  # Article entity
│   │   └── Comment.java                  # Comment entity
│   ├── repository/                       # Data access layer
│   │   ├── ArticleRepository.java        # Article data operations
│   │   └── CommentRepository.java        # Comment data operations
│   └── service/                          # Business logic layer
│       ├── ArticleService.java           # Article business logic
│       └── CommentService.java           # Comment business logic
└── src/main/resources/
    ├── static/                           # Static resources (CSS, JS)
    └── templates/                        # Thymeleaf templates
        ├── admin/                        # Admin templates
        │   ├── article-form.html         # Create/edit article form
        │   └── dashboard.html            # Admin dashboard
        ├── index.html                    # Home page
        ├── article.html                  # Article detail page
        ├── about.html                    # About page
        └── login.html                    # Login page
```

## Setup and Running

### Prerequisites

- Java 17 or higher
- Maven

### Running the Application
The webapp is running here: http://personalblog-9p0w.onrender.com/
1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/personal-blog.git
   cd personal-blog
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Public blog: http://localhost:8080/
   - Admin login: http://localhost:8080/login

## Data Storage

The application uses a simple file-based storage system:

- Articles are stored as JSON files in the `blog_data` directory
- Comments are stored as JSON files in the `blog_data/comments` directory

These directories are created automatically when you run the application.

### Styling

The application uses Tailwind CSS for styling. You can modify the appearance by editing the HTML templates in the `src/main/resources/templates` directory.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Future Enhancements

- User registration and multiple user accounts
- Categories and tags for articles
- Search functionality
- Rich text editor for article content
- Image upload support
- Database integration (MySQL, PostgreSQL)

## Acknowledgments

- Spring Boot documentation
- Thymeleaf documentation
- Tailwind CSS
