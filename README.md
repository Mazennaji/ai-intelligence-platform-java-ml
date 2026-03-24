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
Detect fraudulent transactions using classification models (Weka J48 Decision Trees) with a rule-based fallback engine for resilience.

### 🟡 Price Prediction
Predict house prices using linear regression trained via gradient descent on real estate data.

### 🟢 NLP Engine
- **Sentiment Analysis** — Lexicon-based approach with negation handling and training data expansion
- **Spam Detection** — Keyword + pattern analysis with weighted scoring
- Built with extensible architecture ready for Apache OpenNLP integration

### 🔵 Recommendation System
Suggest items based on user behavior using collaborative filtering with cosine similarity.

### 🟣 Deep Learning Module
Handwritten digit recognition using a 4-layer Dense Neural Network built with Deeplearning4j (784 → 256 → 128 → 64 → 10).

### ⚙️ REST API (Spring Boot)
Expose all ML models through production-ready APIs:
- `POST /api/fraud/predict` — Fraud detection
- `POST /api/price/predict` — Price prediction
- `POST /api/nlp/sentiment` — Sentiment analysis
- `POST /api/nlp/spam` — Spam detection
- `POST /api/recommend` — Item recommendations
- `POST /api/image/predict` — Image classification
- `GET /api/image/info` — Model architecture info
- `GET /api/*/health` — Health checks per module

---

## 🏗️ Project Architecture

```
ai-intelligence-platform-java-ml/
│
├── src/main/java/com/mazen/aiplatform/
│   ├── Application.java
│   ├── controllers/
│   │   ├── FraudController.java
│   │   ├── PriceController.java
│   │   ├── NlpController.java
│   │   ├── RecommendController.java
│   │   ├── ImageController.java
│   │   └── GlobalExceptionHandler.java
│   ├── services/
│   │   ├── FraudService.java
│   │   ├── PriceService.java
│   │   ├── NlpService.java
│   │   ├── RecommendService.java
│   │   └── ImageService.java
│   ├── ml/
│   │   ├── fraud/FraudDetector.java
│   │   ├── regression/PricePredictor.java
│   │   ├── nlp/SentimentAnalyzer.java
│   │   ├── nlp/SpamDetector.java
│   │   ├── recommender/RecommenderEngine.java
│   │   └── deep_learning/ImageClassifier.java
│   ├── models/
│   │   ├── FraudRequest.java
│   │   ├── PriceRequest.java
│   │   ├── NlpRequest.java
│   │   ├── RecommendRequest.java
│   │   └── PredictionResponse.java
│   └── utils/
│       └── CsvUtils.java
│
├── src/main/resources/
│   └── application.properties
│
├── src/test/java/com/mazen/aiplatform/
│   └── MLModulesTest.java
│
├── datasets/
│   ├── fraud_transactions.csv
│   ├── house_prices.csv
│   ├── sentiment_data.csv
│   ├── spam_emails.csv
│   └── recommendations.csv
│
├── saved_models/
├── pom.xml
├── .gitignore
└── README.md
```

---

## 📊 Datasets

All datasets are inside the `/datasets` folder and loaded automatically on startup.

| File | Records | Purpose |
|------|---------|---------|
| `fraud_transactions.csv` | 30 | Transaction classification (fraud/legitimate) |
| `house_prices.csv` | 20 | Real estate price regression |
| `sentiment_data.csv` | 25 | Sentiment classification (positive/negative/neutral) |
| `spam_emails.csv` | 20 | Email classification (spam/ham) |
| `recommendations.csv` | 32 | User-item ratings for collaborative filtering |

---

## ⚙️ Tech Stack

### 🧠 Machine Learning
- **Weka** — Classification (J48 Decision Trees)
- **Tribuo** — Regression models
- **DL4J** — Deep Learning (Dense Neural Networks)
- **Apache OpenNLP** — NLP pipeline (extensible)

### 🔧 Backend
- **Java 17**
- **Spring Boot 3.2**
- **Maven**
- **REST APIs** with validation and global error handling

### 📦 Model Deployment
- **PMML** — Weka / Tribuo model export
- **ONNX** — Deep Learning model export

---

## 🚀 How to Run

### 1. Clone the repository
```bash
git clone https://github.com/mazen-naji/ai-intelligence-platform-java-ml.git
cd ai-intelligence-platform-java-ml
```

### 2. Build the project
```bash
mvn clean install
```

### 3. Run the backend
```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## 📡 API Usage Examples

### Fraud Detection
```bash
curl -X POST http://localhost:8080/api/fraud/predict \
  -H "Content-Type: application/json" \
  -d '{"amount": 15000, "transactionType": "online"}'
```

### Price Prediction
```bash
curl -X POST http://localhost:8080/api/price/predict \
  -H "Content-Type: application/json" \
  -d '{"size": 120, "rooms": 3, "location": "Beirut", "age": 10}'
```

### Sentiment Analysis
```bash
curl -X POST http://localhost:8080/api/nlp/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "This product is amazing!"}'
```

### Spam Detection
```bash
curl -X POST http://localhost:8080/api/nlp/spam \
  -H "Content-Type: application/json" \
  -d '{"text": "WIN FREE MONEY NOW!!!"}'
```

### Recommendations
```bash
curl -X POST http://localhost:8080/api/recommend \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "maxResults": 5}'
```

---

## 🧪 Testing

```bash
mvn test
```

Includes unit tests for all ML modules covering fraud detection, sentiment analysis, spam detection, price prediction, and recommendation engine.

---

## 📈 Future Improvements

- Docker containerization
- React frontend dashboard
- Real-time streaming with Apache Kafka
- Cloud deployment (AWS / Azure)
- JWT authentication
- Model versioning and A/B testing

---

## 👨‍💻 Author

**Mazen Naji**
Full Stack Developer | AI & ML Engineer