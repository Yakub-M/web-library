const fs = require('fs');
const request = require('supertest');

const baseUrl = 'http://localhost:8080/api/books';

async function deleteBooks() {
  // Log in as admin to obtain a token
  const loginResponse = await request('http://localhost:8080')
    .post('/api/auth/login')
    .send({ username: 'admin', password: 'password' });
  const adminToken = loginResponse.body.token;

  // Read book IDs from file
  const bookIds = JSON.parse(fs.readFileSync('bookIds.json', 'utf-8'));

  for (const id of bookIds) {
    await request(baseUrl)
      .delete(`/${id}`)
      .set('Authorization', `Bearer ${adminToken}`)
      .expect(204);
  }

  console.log('✅ Books deleted successfully');
}

deleteBooks().catch(err => console.error('Error deleting books:', err));

