const fs = require('fs');
const request = require('supertest');

const baseUrl = 'http://localhost:8080/api/books';

// Read book IDs from file
const bookIds = JSON.parse(fs.readFileSync('bookIds.json', 'utf-8'));

async function deleteBooks() {
  for (const id of bookIds) {
    await request(baseUrl)
      .delete(`/${id}`)
      .expect(204);
  }

  console.log('✅ Books deleted successfully');
}

deleteBooks().catch(err => console.error('Error deleting books:', err));

