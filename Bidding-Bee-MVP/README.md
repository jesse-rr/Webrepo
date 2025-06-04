# Bidding Bee - Auction House MVP
Microservices Architecture Overview

## [SERVICES]
- Bidding-service
- Payment-service
- Event-Management-service
- Product-service
- Notification-service

---

## [BIDDING-SERVICE]
- `addBid`: Place a bid on a product.
- `getBidsByProduct`: Retrieve all bids for a specific product.
- `getHighestBid`: Retrieve the highest bid for a specific product.
- `cancelBid`: Cancel a bid (if allowed by auction rules).
- `getBidHistory`: Retrieve the bidding history for a user.
- `validateBid`: Validate a bid (e.g., ensure it’s higher than the current highest bid).

---

## [PAYMENT-SERVICE]
- `createPayment`: Create a new payment for a winning bid.
- `getPaymentById`: Retrieve payment details by ID.
- `processRefund`: Process a refund for a canceled bid or auction.
- `getPaymentsByUser`: Retrieve all payments made by a specific user.
- `updatePaymentStatus`: Update the status of a payment (e.g., pending, completed, failed).
- `validatePayment`: Validate payment details before processing.

---

## [EVENT-MANAGEMENT-SERVICE]
- `createAuctionEvent`: Create a new auction event.
- `startAuction`: Start an auction event.
- `endAuction`: End an auction event and determine the winner.
- `getAuctionDetails`: Retrieve details of a specific auction event.
- `getActiveAuctions`: Retrieve a list of active auctions.
- `cancelAuction`: Cancel an auction event (e.g., due to lack of bids or other issues).

---

## [PRODUCT-SERVICE]
- `addProduct`: Add a new product to the system.
- `alterProduct`: Modify details of an existing product.
- `removeProduct`: Remove a product from the system.
- `addProductToAuction`: Add a product to an auction event.
- `alterProductFromAuction`: Modify a product in an auction event.
- `removeProductFromAuction`: Remove a product from an auction event.
- `getProductDetails`: Retrieve details of a specific product.
- `getProductsByCategory`: Retrieve products by category (e.g., electronics, art, etc.).
- `searchProducts`: Search for products by name, description, or other attributes.
- `getProductsInAuction`: Retrieve all products currently in an auction.
- `getProductBids`: Retrieve all bids for a specific product.

---

## [NOTIFICATION-SERVICE]
- `processEmail`: Send email notifications.
- `email-templates.yml`: Templates for emails (e.g., placing bid, altering product, etc.).
- `sendSMS`: Send SMS notifications (e.g., for bid updates or auction results).
- `sendPushNotification`: Send push notifications to mobile apps.
- `getNotificationHistory`: Retrieve a user’s notification history.
- `updateNotificationPreferences`: Allow users to update their notification preferences (e.g., email, SMS, push).

---

## Additional Notes
- Ensure all services are secured with proper authentication and authorization.
- Use a message broker (e.g., RabbitMQ, Kafka) for communication between microservices.
- Implement logging and monitoring for all services to ensure reliability and debugging.
