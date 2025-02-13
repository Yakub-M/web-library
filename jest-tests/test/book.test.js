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
    await request(baseUrl)
      .get(`/${createdBookId}`)
      .expect(200); // Expect status code 200 (OK)
  });

  // SEARCH TESTS AFTER CREATION
  it('should search books by title', async () => {
    const response = await request(baseUrl).get('/title/Test Book');
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.title).toMatch(/Test Book/i);
    });
  });

  it('should search books by author', async () => {
    const response = await request(baseUrl).get('/author/John Doe');
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.author).toMatch(/John Doe/i);
    });
  });

  it('should search books by genre', async () => {
    const response = await request(baseUrl).get('/genre/Science Fiction');
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.genre).toMatch(/Science Fiction/i);
    });
  });

  it('should search books by ISBN', async () => {
    const response = await request(baseUrl).get('/isbn/1234567890');
    expect(response.status).toBe(200);
    expect(response.body[0].isbn).toBe('1234567890');
  });

  it('should return an empty array for a non-existent title', async () => {
    const response = await request(baseUrl).get('/title/NonExistentTitle');
    expect(response.status).toBe(200);
    expect(response.body).toEqual([]);
  });

  // UPDATE & DELETE TESTS AFTER SEARCHING
  it('should update the book', async () => {
    await request(baseUrl)
      .put(`/${createdBookId}`)
      .send({
        title: 'Updated Test Book',
        author: 'John Doe',
        publicationDate: '2025-02-11',
        genre: 'Science Fiction',
        isbn: '123456789',
      })
      .expect(200);
  });

  it('should delete the book', async () => {
    await request(baseUrl)
      .delete(`/${createdBookId}`)
      .expect(204); // Expect status code 204 (No content)
  });
});

