const request = require('supertest');

const baseUrl = 'http://localhost:8080/api/books';

describe('Book API', () => {
  let createdBookId = null;

  it('should create a new book', async () => {
    const response = await request(baseUrl)
      .post('/')
      .send({
        title: 'Test Book',
        author: 'John Doe',
        publicationDate: '2025-02-11',
        genre: 'Science Fiction',
        isbn: '1234567890',
      })    
        .expect(201); // Expect status code 201 (Created)

    createdBookId = response.body.id;
  });

  it('should get the created book by ID', async () => {
    const response = await request(baseUrl)
      .get(`/${createdBookId}`)
      .expect(200); // Expect status code 200 (OK)
  });

  it('should update the book', async () => {
    const response = await request(baseUrl)
      .put(`/${createdBookId}`)
      .send({
        title: 'Updated Test Book',
        author: 'John Doe',
        publicationDate: '2025-02-11',
        genre: 'Science Fiction',
        isbn: '123456789',
      })
      .expect(200); // Expect status code 200 (OK)
  });

  it('should delete the book', async () => {
    const response = await request(baseUrl)
      .delete(`/${createdBookId}`)
      .expect(204); // Expect status code 204 (No content)
  });
});
