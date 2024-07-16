curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Tech Solutions Inc.",
    "contactInfo": "hr@techsolutions.com",
    "phoneNumber": "123-456-7890",
    "sendDate": "2024-07-09",
    "kanton": "Aargau AG",
    "postalCode": "5000",
    "additionalNotes": "Followed up via email",
    "notificationTime": 5,
    "status": "Open"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Innovative Tech Ltd.",
    "contactInfo": "contact@innovativetech.com",
    "phoneNumber": "234-567-8901",
    "sendDate": "2024-07-09",
    "kanton": "Basel-Stadt BS",
    "postalCode": "4000",
    "additionalNotes": "Awaiting response",
    "notificationTime": 10,
    "status": "timeToMessage"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Future Enterprises",
    "contactInfo": "info@futureenterprises.com",
    "phoneNumber": "345-678-9012",
    "sendDate": "2024-07-09",
    "kanton": "Bern BE",
    "postalCode": "3000",
    "additionalNotes": "Need to follow up next week",
    "notificationTime": 15,
    "status": "Closed"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "GreenTech Innovations",
    "contactInfo": "support@greentech.com",
    "phoneNumber": "456-789-0123",
    "sendDate": "2024-07-09",
    "kanton": "Freiburg FR",
    "postalCode": "1700",
    "additionalNotes": "Meeting scheduled",
    "notificationTime": 0,
    "status": "Open"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Smart Solutions GmbH",
    "contactInfo": "contact@smartsolutions.com",
    "phoneNumber": "567-890-1234",
    "sendDate": "2024-07-09",
    "kanton": "Genf GE",
    "postalCode": "1200",
    "additionalNotes": "Pending approval",
    "notificationTime": 5,
    "status": "timeToMessage"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Tech Innovators Inc.",
    "contactInfo": "info@techinnovators.com",
    "phoneNumber": "678-901-2345",
    "sendDate": "2024-07-09",
    "kanton": "Graubünden GR",
    "postalCode": "7000",
    "additionalNotes": "Received positive feedback",
    "notificationTime": 10,
    "status": "Closed"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Advanced Systems AG",
    "contactInfo": "contact@advancedsystems.com",
    "phoneNumber": "789-012-3456",
    "sendDate": "2024-07-09",
    "kanton": "Luzern LU",
    "postalCode": "6000",
    "additionalNotes": "Follow-up needed",
    "notificationTime": 15,
    "status": "Open"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "NextGen Tech",
    "contactInfo": "support@nextgentech.com",
    "phoneNumber": "890-123-4567",
    "sendDate": "2024-07-09",
    "kanton": "Schaffhausen SH",
    "postalCode": "8200",
    "additionalNotes": "Interview scheduled",
    "notificationTime": 0,
    "status": "timeToMessage"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Innovative Designs",
    "contactInfo": "info@innovativedesigns.com",
    "phoneNumber": "901-234-5678",
    "sendDate": "2024-07-09",
    "kanton": "Thurgau TG",
    "postalCode": "8500",
    "additionalNotes": "Awaiting feedback",
    "notificationTime": 5,
    "status": "Closed"
}'

curl -X POST http://localhost:8080/applications \
-H "Content-Type: application/json" \
-d '{
    "companyName": "Tech Pioneers",
    "contactInfo": "contact@techpioneers.com",
    "phoneNumber": "012-345-6789",
    "sendDate": "2024-07-09",
    "kanton": "Zug ZG",
    "postalCode": "6300",
    "additionalNotes": "Follow-up in two weeks",
    "notificationTime": 10,
    "status": "Open"
}'
