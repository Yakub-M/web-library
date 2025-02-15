const request = require('supertest');
const baseUrl = 'http://localhost:8080';
let userToken, adminToken;
let createdBookId = null;

beforeAll(async () => {
  // Register a regular user (if not already registered)
  await request(baseUrl)
    .post('/api/auth/register')
    .send({ username: 'user', password: 'password', roles: ["ROLE_USER"] });

  // Register an admin user
  await request(baseUrl)
    .post('/api/auth/register')
    .send({ username: 'admin', password: 'password', roles: ["ROLE_ADMIN"] });

  // Login as user
  const userLoginRes = await request(baseUrl)
    .post('/api/auth/login')
    .send({ username: 'user', password: 'password' });
  userToken = userLoginRes.body.token;

  // Login as admin
  const adminLoginRes = await request(baseUrl)
    .post('/api/auth/login')
    .send({ username: 'admin', password: 'password' });
  adminToken = adminLoginRes.body.token;
});

describe('Book API with Authentication', () => {

  it('should create a new book (authenticated as user)', async () => {
    const response = await request(baseUrl)
      .post('/api/books/')
      .set('Authorization', `Bearer ${userToken}`)
      .send({
        title: 'Test Book',
        author: 'John Doe',
        publicationDate: '2025-02-11',
        genre: 'Science Fiction',
        isbn: '1234567890',
      })
      .expect(201);

    createdBookId = response.body.id;
  });

  it('should get the created book by ID (authenticated as user)', async () => {
    await request(baseUrl)
      .get(`/api/books/${createdBookId}`)
      .set('Authorization', `Bearer ${userToken}`)
      .expect(200);
  });

  it('should search books by title (authenticated as user)', async () => {
    const response = await request(baseUrl)
      .get('/api/books/title/Test Book')
      .set('Authorization', `Bearer ${userToken}`);
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.title).toMatch(/Test Book/i);
    });
  });

  it('should search books by author (authenticated as user)', async () => {
    const response = await request(baseUrl)
       .get('/api/books/author/John Doe')
       .set('Authorization', `Bearer ${userToken}`);
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.author).toMatch(/John Doe/i);
    });
  });

  it('should search books by genre (authenticated as user)', async () => {
    const response = await request(baseUrl)
       .get('/api/books/genre/Science Fiction')
       .set('Authorization', `Bearer ${userToken}`);
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
    response.body.forEach((book) => {
      expect(book.genre).toMatch(/Science Fiction/i);
    });
  });

  it('should search books by ISBN (authenticated as user)', async () => {
    const response = await request(baseUrl)
       .get('/api/books/isbn/1234567890')
       .set('Authorization', `Bearer ${userToken}`);
    expect(response.status).toBe(200);
    expect(response.body[0].isbn).toBe('1234567890');
  });

  it('should return an empty array for a non-existent title (authenticated as user)', async () => {
    const response = await request(baseUrl)
       .get('/api/books/title/NonExistentTitle')
       .set('Authorization', `Bearer ${userToken}`);
    expect(response.status).toBe(200);
    expect(response.body).toEqual([]);
  });

  it('should update the book (authenticated as admin)', async () => {
    await request(baseUrl)
      .put(`/api/books/${createdBookId}`)
      .set('Authorization', `Bearer ${adminToken}`)
      .send({
        title: 'Updated Test Book',
        author: 'John Doe',
        publicationDate: '2025-02-11',
        genre: 'Science Fiction',
        isbn: '123456789',
      })
      .expect(200);
  });

  it('should delete the book (authenticated as admin)', async () => {
    await request(baseUrl)
      .delete(`/api/books/${createdBookId}`)
      .set('Authorization', `Bearer ${adminToken}`)
      .expect(204);
  });
});
