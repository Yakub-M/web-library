const fs = require('fs');
const request = require('supertest');

const baseUrl = 'http://localhost:8080/api/books';

const sampleBooks = [
  { title: 'Book One', author: 'Alice Johnson', publicationDate: '2023-01-01', genre: 'Fiction', isbn: '1111111111' },
  { title: 'Book Two', author: 'Bob Smith', publicationDate: '2022-05-12', genre: 'Mystery', isbn: '2222222222' },
  { title: 'Book Three', author: 'Charlie Davis', publicationDate: '2021-07-23', genre: 'Horror', isbn: '3333333333' },
  { title: 'Book Four', author: 'Diana Lee', publicationDate: '2020-11-15', genre: 'Sci-Fi', isbn: '4444444444' },
  { title: 'Book Five', author: 'Ethan Wright', publicationDate: '2019-08-30', genre: 'Romance', isbn: '5555555555' },
  { title: 'Book Six', author: 'Fiona Green', publicationDate: '2018-12-11', genre: 'Thriller', isbn: '6666666666' },
  { title: 'Book Seven', author: 'George Hill', publicationDate: '2017-04-18', genre: 'Fantasy', isbn: '7777777777' },
  { title: 'Book Eight', author: 'Hannah Carter', publicationDate: '2016-06-27', genre: 'Non-Fiction', isbn: '8888888888' },
  { title: 'Book Nine', author: 'Ian Brown', publicationDate: '2015-09-14', genre: 'Drama', isbn: '9999999999' },
  { title: 'Book Ten', author: 'Jack Wilson', publicationDate: '2014-03-22', genre: 'Biography', isbn: '0000000000' },
];

async function createBooks() {
  // Log in as admin to obtain a token
  const loginResponse = await request('http://localhost:8080')
    .post('/api/auth/login')
    .send({ username: 'admin', password: 'password' });
  const adminToken = loginResponse.body.token;

  let bookIds = [];
  for (const book of sampleBooks) {
    const response = await request(baseUrl)
      .post('/')
      .set('Authorization', `Bearer ${adminToken}`)
      .send(book)
      .expect(201);
    
    bookIds.push(response.body.id);
  }

  fs.writeFileSync('bookIds.json', JSON.stringify(bookIds, null, 2));
  console.log('✅ Books created and IDs saved to bookIds.json');
}

createBooks().catch(err => console.error('Error creating books:', err));

