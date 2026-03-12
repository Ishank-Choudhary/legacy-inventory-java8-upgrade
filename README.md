# legacy-inventory-java-upgrade

Legacy inventory management system split into:

- `legacy-java6` (fully implemented in Java 6 style)
- `upgraded-java8` (empty placeholder for future migration)

## Repository Structure

```text
legacy-inventory-java-upgrade
 ├── legacy-java6
 │   └── src/main/java/com/legacy/inventory/
 │       ├── model
 │       ├── repository
 │       ├── service
 │       ├── util
 │       └── app
 │
 ├── upgraded-java8
 │   └── src/main/java/com/legacy/inventory/
 │
 └── README.md
```

## Implemented Features (legacy-java6)

- Product management (add/update/delete/list/search)
- Inventory stock tracking (increase/decrease/low stock)
- Order management (create/add items/finalize/history)
- Reports (products by price, top selling products, low inventory)
- Console menu-based application

## Run

Compile and run from project root:

```bash
javac $(find legacy-java6/src/main/java -name "*.java")
java -cp legacy-java6/src/main/java com.legacy.inventory.app.InventoryApplication
```
