const arrayOfBooks = [
  {
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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
    imageSrc: "./stock/book_rec_image.jpg",
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

let book_index = 0;

document.getElementById("leftBtn").addEventListener("click", () => {
  book_index = (book_index === 0) ? arrayOfBooks.length - 1 : book_index - 1;
  updateBookInfo(book_index);
});

document.getElementById("rightBtn").addEventListener("click", () => {
  book_index = (book_index === arrayOfBooks.length - 1) ? 0 : book_index + 1;
  updateBookInfo(book_index);
});

function updateBookInfo(index) {
  const book = arrayOfBooks[index];

  document.getElementById("feature_bookImg").src = book.imageSrc;
  document.getElementById("feature_bookName").textContent = book.bookName;
  document.getElementById("feature_bookAuthor").textContent = "Author: " + book.author;
  const div_genres = document.getElementById("div_feature_genres");
  div_genres.innerHTML = ''; // CLEAR GENRES
  
  document.getElementById("feature_ranking").textContent = "Ranking: " + book.metadata.ranking;
  document.getElementById("feature_rating").textContent = "Rating: " + book.metadata.rating;
  document.getElementById("feature_chapters").textContent = "Chapters: " + book.metadata.chapters;
  document.getElementById("feature_views").textContent = "Views: " + formatMetadata(book.metadata.views);
  document.getElementById("feature_bookMarks").textContent = "Bookmarks: " + formatMetadata(book.metadata.bookmarks);
  document.getElementById("feature_readLater").textContent = "Read later: " + formatMetadata(book.metadata.readLater);
  document.getElementById("feature_status").textContent = "Status: " + book.metadata.status;

  if (book.genres.length < 5) {
    for (let genre of book.genres) {
      const genreElement = document.createElement("p");
      genreElement.classList.add("feature_genres");
      genreElement.textContent = genre;
      div_genres.appendChild(genreElement);
    }
  } else {
    for (let genre = 0; genre < 5; genre++) {
      const genreElement = document.createElement("p");
      genreElement.classList.add("feature_genres");
      genreElement.textContent = book.genres[genre];
      div_genres.appendChild(genreElement);
    }
    genreElement = document.createElement("p");
    genreElement.classList.add("feature_genres");
    genreElement.textContent = "MORE...";
    div_genres.appendChild(genreElement);
  }
}

function formatMetadata(number) {
  if (number >= 1000000) {
    return parseInt(number / 1000000) + "." + parseInt((number % 1000000) / 100000) + "M";
  } else if (number >= 1000){
    return parseInt(number / 1000) + "." + parseInt((number % 1000) / 100) + "K";
  } 
    return number;
}

updateBookInfo(book_index);