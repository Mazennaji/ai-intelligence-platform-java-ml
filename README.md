# 🚀 AI Intelligence Platform (Java ML System)

An end-to-end **Machine Learning platform built in Java** that combines multiple AI modules into a single unified system.

It includes:
- Fraud Detection (Classification)
- Price Prediction (Regression)
- Sentiment Analysis & Spam Detection (NLP)
- Recommendation System
- Deep Learning Image Classification
- REST API deployment using Spring Boot
- Model saving using PMML / ONNX

---

## 🧠 Features

### 🔴 Fraud Detection
Detect fraudulent transactions using classification models (Weka / Decision Trees).

### 🟡 Price Prediction
Predict values (e.g., house prices) using regression models (Tribuo).

### 🟢 NLP Engine
- Sentiment Analysis (Positive / Negative)
- Spam detection
- Built using Apache OpenNLP

### 🔵 Recommendation System
Suggest items based on user behavior using Apache Mahout.

### 🟣 Deep Learning Module
Handwritten digit recognition or image classification using DL4J.

### ⚙️ REST API (Spring Boot)
Expose ML models through APIs:
- `/api/fraud/predict`
- `/api/price/predict`
- `/api/nlp/sentiment`
- `/api/recommend`
- `/api/image/predict`

---

## 🏗️ Project Architecture

```
AI-Intelligence-Platform/
│
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── ml/
│   │   ├── fraud/
│   │   ├── regression/
│   │   ├── nlp/
│   │   ├── recommender/
│   │   └── deep_learning/
│   │
│   ├── models/
│   ├── utils/
│   └── Application.java
│
├── datasets/
├── saved_models/
└── README.md
```

---

## 📊 Datasets

Place all datasets inside the `/datasets` folder.

### 1. `fraud_transactions.csv`
```
id,amount,transaction_type,location,time,is_fraud
1,5000,online,Beirut,12:30,1
2,200,offline,Tripoli,09:10,0
```

### 2. `house_prices.csv`
```
size,rooms,location,age,price
120,3,Beirut,10,150000
85,2,Tripoli,5,90000
```

### 3. `sentiment_data.csv`
```
text,label
"I love this product",positive
"Very bad experience",negative
```

### 4. `spam_emails.csv`
```
text,label
"Win money now!!!",spam
"Meeting at 5pm",ham
```

### 5. `recommendations.csv`
```
user_id,item_id,rating
1,101,5
1,102,3
2,101,4
```

---

## ⚙️ Tech Stack

### 🧠 Machine Learning
- Weka
- Tribuo
- DL4J
- Apache Mahout
- Apache OpenNLP

### 🔧 Backend
- Java
- Spring Boot
- REST APIs

### 📦 Model Deployment
- PMML (Weka / Tribuo models)
- ONNX (Deep Learning models)

---

## 🚀 How to Run

### 1. Clone the repository
```bash
git clone https://github.com/your-username/ai-intelligence-platform-java-ml.git
```

### 2. Open in IntelliJ IDEA or Eclipse

### 3. Add dependencies
- Spring Boot
- Weka
- DL4J
- Mahout
- OpenNLP

### 4. Run the backend
```bash
mvn spring-boot:run
```

---

## 📡 API Usage Examples

### Fraud Detection
```http
POST /api/fraud/predict
```
```json
{
  "amount": 5000,
  "transaction_type": "online"
}
```

### Price Prediction
```http
POST /api/price/predict
```
```json
{
  "size": 120,
  "rooms": 3,
  "location": "Beirut",
  "age": 10
}
```

### Sentiment Analysis
```http
POST /api/nlp/sentiment
```
```json
{
  "text": "This product is amazing!"
}
```

---

## 🔥 Why This Project Is Powerful

✔ Combines multiple ML domains
✔ Real-world production architecture
✔ Java + AI engineering skills
✔ REST API deployment
✔ Portfolio-ready capstone project

---

## 📈 Future Improvements

- Add Docker support
- Add React frontend dashboard
- Add real-time streaming (Kafka)
- Add cloud deployment (AWS / Azure)
- Add authentication (JWT)

---

## 👨‍💻 Author

**Mazen Naji**
