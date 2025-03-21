# MongoDB Data Manipulation and Aggregation with Java

This Java project demonstrates how to perform CRUD operations and aggregation queries on a MongoDB database using both the legacy `DBCollection` API and the modern `MongoCollection<Document>` API. The application connects to a local MongoDB instance, inserts sample restaurant data, and performs a range of operations to showcase how to interact with a MongoDB collection programmatically.

## Features

- Connects to MongoDB using Java
- Inserts a predefined list of restaurant documents
- Queries and prints all documents from the collection
- Filters restaurants by category (e.g., "Cafe")
- Increments the star rating of a specific restaurant
- Updates a restaurant’s name
- Aggregates restaurants with a specific star rating

## Operations Demonstrated

1. Data Insertion – Inserts restaurant records using JSON strings
2. Read All Records – Prints the entire collection
3. Category Filtering – Finds all restaurants in the "Cafe" category
4. Star Increment – Increments the star count for "XYZ Coffee Bar"
5. Name Update – Renames "456 Cookies Shop" to "123 Cookies Heaven"
6. Aggregation – Lists all restaurants with a 4-star rating using `$match` and `$project`

## Technologies Used

- Java
- MongoDB
- MongoDB Java Driver (`mongo-java-driver`)
- BSON & JSON parsing

## Setup

1. Ensure MongoDB is running locally on port `27017`
2. Clone this repository:
   ```bash
   git clone https://github.com/halimdakir/MongoDB-Java-CRUD-and-Aggregation.git
