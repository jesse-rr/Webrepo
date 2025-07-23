const arrayOfBooks = [
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "I’m an Infinite Regressor, But I’ve Got Stories to Tell",
    author: "Sinnoa",
    genres: ["ACTION", "DRAMA", "FANTASY", "SEINEN", "TRAGEDY"],
    metadata: {
        ranking: 162,
        rating: 4.9,
        chapters: 268,
        views: 612000,
        bookmarks: 7150,
        readLater: 15230,
        status: "ON_GOING"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "Legends of the Last Horizon",
    author: "Ava Lee",
    genres: ["ADVENTURE", "FANTASY", "MYSTERY"],
    metadata: {
        ranking: 85,
        rating: 4.7,
        chapters: 120,
        views: 432000,
        bookmarks: 5320,
        readLater: 10800,
        status: "ON_GOING"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "The Final Eclipse",
    author: "J.K. Rowan",
    genres: ["SCI-FI", "THRILLER", "MYSTERY"],
    metadata: {
        ranking: 23,
        rating: 4.8,
        chapters: 310,
        views: 1000000,
        bookmarks: 20000,
        readLater: 25000,
        status: "COMPLETED"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "The Shadows Beneath",
    author: "Lucius Thorne",
    genres: ["HORROR", "MYSTERY", "THRILLER"],
    metadata: {
        ranking: 34,
        rating: 4.5,
        chapters: 75,
        views: 220000,
        bookmarks: 12000,
        readLater: 8000,
        status: "COMPLETED"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "Whispers of the Forgotten",
    author: "Elsie Finch",
    genres: ["DRAMA", "ROMANCE", "MYSTERY"],
    metadata: {
        ranking: 12,
        rating: 4.6,
        chapters: 220,
        views: 567000,
        bookmarks: 8900,
        readLater: 11000,
        status: "ON_GOING"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "Into the Abyss",
    author: "Markus J.",
    genres: ["ACTION", "FANTASY", "MYSTERY"],
    metadata: {
        ranking: 70,
        rating: 4.4,
        chapters: 198,
        views: 380000,
        bookmarks: 6000,
        readLater: 14500,
        status: "ON_GOING"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "The Heart of the Ocean",
    author: "Eleanor Kingsley",
    genres: ["ADVENTURE", "ROMANCE", "DRAMA"],
    metadata: {
        ranking: 51,
        rating: 4.7,
        chapters: 135,
        views: 525000,
        bookmarks: 4200,
        readLater: 12500,
        status: "COMPLETED"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "The Iron Throne",
    author: "Gregory Stone",
    genres: ["FANTASY", "ACTION", "DRAMA"],
    metadata: {
        ranking: 9,
        rating: 5.0,
        chapters: 450,
        views: 1200000,
        bookmarks: 50000,
        readLater: 30000,
        status: "COMPLETED"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "Under the Northern Lights",
    author: "Isla Harper",
    genres: ["ROMANCE", "DRAMA", "MYSTERY"],
    metadata: {
        ranking: 57,
        rating: 4.3,
        chapters: 180,
        views: 620000,
        bookmarks: 7000,
        readLater: 16000,
        status: "ON_GOING"
    }
  },
  {
    imageSrc: "/homepage/stock/book_rec_image.jpg",
    bookName: "Fallen Kingdoms",
    author: "Reed T. Grant",
    genres: ["FANTASY", "ACTION", "ADVENTURE"],
    metadata: {
        ranking: 3,
        rating: 4.9,
        chapters: 350,
        views: 2100000,
        bookmarks: 75000,
        readLater: 40000,
        status: "COMPLETED"
    }
  }
];

function createBooks(books, startIndex = 0, count = 4) {
  const secBooks = document.getElementById("sec_books");
  const booksToShow = books.slice(startIndex, startIndex + count);
  const pager = {
    name: "read later"
  }

  const booksHTML = booksToShow.map(book => `
    <div class="book_container bg-animation">
      <img class="bookImage" src="${book.imageSrc}" alt="${book.bookName}" />
      <div class="book-container-inside">
        <p class="book_title">${book.bookName}</p>
        <p class="book_author">Author: ${book.author}</p>
        <p class="book_rating">Ranking: ${book.metadata.ranking}</p>
        <div id='stars'></div>
        <div id='stars2'></div>
        <div id='stars3'></div>
        <div id='stars4'></div>
      </div>
      <div class="pager"><p class="pager-title">${pager.name}</p></div>
    </div>
  `).join('');

  secBooks.innerHTML = booksHTML;
  return startIndex + count;
}

let currentIndex = 0;
currentIndex = createBooks(arrayOfBooks, currentIndex, 20);