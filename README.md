# Personal Finance API (Skeleton)

## Run
1. Install JDK 17 and Maven.
2. `mvn spring-boot:run`
3. H2 Console: `/h2-console` (JDBC URL: `jdbc:h2:mem:financedb`)

## Auth
- `POST /api/auth/register` `{ "name":"Pedro", "email":"pedro@mail.com", "password":"123456" }`
- `POST /api/auth/login` `{ "email":"pedro@mail.com", "password":"123456" }` → returns `{ "token": "..." }`
- Use header: `Authorization: Bearer <token>`

## Categories
- `POST /api/categories` `{ "name": "Alimentação" }`
- `GET /api/categories`

## Transactions
- `POST /api/transactions`
```json
{
  "description": "Salário",
  "value": 5000,
  "date": "2025-08-01",
  "type": "INCOME",
  "categoryId": null
}
```
- `GET /api/transactions?type=EXPENSE&from=2025-08-01&to=2025-08-31&page=0&size=10`
- `DELETE /api/transactions/{id}`

## Reports
- `GET /api/reports/monthly-balance?ym=2025-08`
- `GET /api/reports/category-summary?ym=2025-08` → returns labels/values ready for Chart.js
