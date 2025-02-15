const axios = require('axios');

const API_URL = "http://localhost:8080/api/auth";
const ADMIN_CREDENTIALS = {
    username: "admin",
    password: "password"
};
const TEST_USERS = ["testuser", "testadmin"];

async function getAdminToken() {
    try {
        const response = await axios.post(`${API_URL}/login`, ADMIN_CREDENTIALS);
        return response.data.token;
    } catch (error) {
        console.error("Failed to obtain admin token:", error.response?.data || error.message);
        process.exit(1);
    }
}

async function deleteTestUsers(adminToken) {
    for (const username of TEST_USERS) {
        try {
            const response = await axios.delete(`${API_URL}/cleanup/${username}`, {
                headers: { 'Authorization': `Bearer ${adminToken}` }
            });
            console.log(`Deleted ${username}:`, response.data);
        } catch (error) {
            console.error(`Failed to delete ${username}:`, error.response?.data || error.message);
        }
    }
}

async function main() {
    const token = await getAdminToken();
    await deleteTestUsers(token);
}

main();

