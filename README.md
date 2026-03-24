<div align="center">

# 🧠 AI Intelligence Platform — Java ML System

**An end-to-end Machine Learning platform combining fraud detection, regression, NLP, recommendation systems, and deep learning into a unified Java system with production-ready REST APIs.**

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Weka](https://img.shields.io/badge/Weka-3.8-005F73?style=for-the-badge)
![DL4J](https://img.shields.io/badge/DL4J-Deep_Learning-FF6F00?style=for-the-badge)

</div>

---

## 📌 About

This project is a **full-stack Java ML platform** that brings together five distinct machine learning domains into a single, cohesive system. Each module is independently trained, served through its own REST endpoint, and designed with graceful fallbacks — making the platform resilient, extensible, and production-aware.

---

## 🏗️ Architecture

```
ai-intelligence-platform-java-ml/
│
├── src/main/java/com/mazen/aiplatform/
│   ├── Application.java                          # Spring Boot entry point
│   │
│   ├── controllers/
│   │   ├── FraudController.java                  # POST /api/fraud/predict
│   │   ├── PriceController.java                  # POST /api/price/predict
│   │   ├── NlpController.java                    # POST /api/nlp/sentiment & /spam
│   │   ├── RecommendController.java              # POST /api/recommend
│   │   ├── ImageController.java                  # POST /api/image/predict
│   │   └── GlobalExceptionHandler.java           # Unified error handling
│   │
│   ├── services/
│   │   ├── FraudService.java                     # Fraud detection orchestration
│   │   ├── PriceService.java                     # Price prediction orchestration
│   │   ├── NlpService.java                       # Sentiment + Spam orchestration
│   │   ├── RecommendService.java                 # Recommendation orchestration
│   │   └── ImageService.java                     # Image classification orchestration
│   │
│   ├── ml/
│   │   ├── fraud/FraudDetector.java              # Weka J48 + rule-based fallback
│   │   ├── regression/PricePredictor.java         # Gradient descent regression
│   │   ├── nlp/SentimentAnalyzer.java            # Lexicon-based sentiment engine
│   │   ├── nlp/SpamDetector.java                 # Keyword + pattern spam scorer
│   │   ├── recommender/RecommenderEngine.java    # Collaborative filtering (cosine)
│   │   └── deep_learning/ImageClassifier.java    # DL4J 4-layer dense network
│   │
│   ├── models/
│   │   ├── FraudRequest.java
│   │   ├── PriceRequest.java
│   │   ├── NlpRequest.java
│   │   ├── RecommendRequest.java
│   │   └── PredictionResponse.java
│   │
│   └── utils/
│       └── CsvUtils.java                         # CSV parsing utility
│
├── datasets/                                      # Auto-loaded on startup
├── saved_models/                                  # PMML / ONNX exports
├── pom.xml
└── README.md
```

---

## 🧠 ML Modules Overview

| Module | Algorithm | Library | Fallback |
|--------|-----------|---------|----------|
| **Fraud Detection** | J48 Decision Tree | Weka | Rule-based risk scoring engine |
| **Price Prediction** | Linear Regression (Gradient Descent) | Custom | Weighted estimation formula |
| **Sentiment Analysis** | Lexicon-based + Negation Handling | Custom | Seed lexicon (45+ words) |
| **Spam Detection** | Keyword + Pattern Scoring | Custom | Keyword matching |
| **Recommendations** | Collaborative Filtering (Cosine Similarity) | Custom | Empty response for unknown users |
| **Image Classification** | Dense Neural Network (784→256→128→64→10) | DL4J | Architecture-only (requires MNIST training) |

---

## ⚙️ How It Works

```
 ┌───────────┐     ┌──────────────┐     ┌───────────────┐     ┌──────────────┐
 │  Request   │ ──▶ │  Controller  │ ──▶ │    Service    │ ──▶ │  ML Module   │
 │ (JSON API) │     │ (Validation) │     │ (Orchestrate) │     │ (Predict)    │
 └───────────┘     └──────────────┘     └───────────────┘     └──────────────┘
                                                                      │
                                                                      ▼
                                                              ┌──────────────┐
                                                              │   Response   │
                                                              │  (JSON + %)  │
                                                              └──────────────┘
```

1. **Receive** a JSON request through the REST API
2. **Validate** input using Bean Validation annotations
3. **Route** to the appropriate service and ML module
4. **Predict** using the trained model (or graceful fallback)
5. **Return** a structured response with prediction, confidence, and metadata

---

## ▶️ Quick Start

**Prerequisites:** Java 17+, Maven

```bash
# Clone
git clone https://github.com/mazen-naji/ai-intelligence-platform-java-ml.git
cd ai-intelligence-platform-java-ml

# Build
mvn clean install

# Run
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## 📡 API Examples

**Fraud Detection**
```bash
curl -X POST http://localhost:8080/api/fraud/predict \
  -H "Content-Type: application/json" \
  -d '{"amount": 15000, "transactionType": "online"}'
```

**Price Prediction**
```bash
curl -X POST http://localhost:8080/api/price/predict \
  -H "Content-Type: application/json" \
  -d '{"size": 120, "rooms": 3, "location": "Beirut", "age": 10}'
```

**Sentiment Analysis**
```bash
curl -X POST http://localhost:8080/api/nlp/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "This product is amazing!"}'
```

**Spam Detection**
```bash
curl -X POST http://localhost:8080/api/nlp/spam \
  -H "Content-Type: application/json" \
  -d '{"text": "WIN FREE MONEY NOW!!!"}'
```

**Recommendations**
```bash
curl -X POST http://localhost:8080/api/recommend \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "maxResults": 5}'
```

---

## 📊 Datasets

All datasets are inside `/datasets` and loaded automatically on startup.

| File | Records | Purpose |
|------|---------|---------|
| `fraud_transactions.csv` | 100 | Transaction classification (fraud / legitimate) |
| `house_prices.csv` | 100 | Real estate price regression |
| `sentiment_data.csv` | 100 | Sentiment classification (positive / negative / neutral) |
| `spam_emails.csv` | 100 | Email classification (spam / ham) |
| `recommendations.csv` | 100 | User-item ratings for collaborative filtering |

---

## 🧪 Testing

```bash
mvn test
```

Unit tests cover all ML modules: fraud detection, sentiment analysis, spam detection, price prediction, and the recommendation engine.

---

## 🔌 Extensibility

Adding a new ML module requires **zero changes** to existing code — just follow the established pattern:

```java
// 1. Create the ML module
@Component
public class AnomalyDetector {
    public Map<String, Object> detect(double[] features) {
        // Your anomaly detection logic
    }
}

// 2. Create the service
@Service
public class AnomalyService {
    private final AnomalyDetector detector;
    // Orchestration logic
}

// 3. Create the controller
@RestController
@RequestMapping("/api/anomaly")
public class AnomalyController {
    @PostMapping("/detect")
    public ResponseEntity<PredictionResponse> detect(@RequestBody ...) {
        // Expose via REST
    }
}
```

---

## 🗺️ Roadmap

- [ ] Docker containerization
- [ ] React frontend dashboard
- [ ] Real-time streaming with Apache Kafka
- [ ] Cloud deployment (AWS / Azure)
- [ ] JWT authentication
- [ ] Model versioning and A/B testing
- [ ] Swagger / OpenAPI documentation

---

## 🛠️ Tech Stack

| | |
|---|---|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 4.0 |
| **ML Libraries** | Weka, Tribuo, Deeplearning4j, Apache OpenNLP |
| **Build Tool** | Maven |
| **Model Export** | PMML (Weka / Tribuo), ONNX (Deep Learning) |
| **Testing** | JUnit 5, Spring Boot Test |

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork and submit a pull request.

1. Fork the repository
2. Create your feature branch — `git checkout -b feature/new-module`
3. Commit your changes — `git commit -m "Add anomaly detection module"`
4. Push to the branch — `git push origin feature/new-module`
5. Open a Pull Request

---


<div align="center">

**Built by [Mazen Naji](https://github.com/Mazennaji)**

⭐ If you found this useful, a star goes a long way!

</div>
