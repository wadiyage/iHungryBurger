# iHungryBurger

iHungryBurger is a Java-based console application designed to help the iHungry Burger shop efficiently manage its daily order transactions. The system provides essential features for placing, searching, updating, and viewing burger orders, supporting the shop's growing customer base and increasing order volume.

## Features

- **Place Orders:** Add new customer orders with validation for customer details and burger quantity.
- **Search Best Customer:** Identify and display the customer with the highest total purchase value.
- **Search Order:** Retrieve order details by order ID.
- **Search Customer:** View all orders placed by a specific customer.
- **View Orders:** List orders by status (Delivered, Preparing, Canceled).
- **Update Order Details:** Modify order quantity or status for orders that are not yet delivered or canceled.
- **Exit:** Safely exit the application.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Running the Application

1. **Clone the Repository:**
    ```sh
    git clone https://github.com/yourusername/iHungryBurger.git
    cd iHungryBurger
    ```

2. **Compile the Source Code:**
    ```sh
    javac -d bin src/IHungryBurger.java
    ```

3. **Run the Application:**
    ```sh
    java -cp bin IHungryBurger
    ```

## Usage

Upon running, the application presents a menu-driven interface. Enter the number corresponding to the desired operation and follow the on-screen prompts to manage orders.

## Project Structure

- `src/IHungryBurger.java`: Main source code for the application.

## License

This project is licensed under the MIT License.

## Author

- [Sandaruwan Wadiyage](https://github.com/wadiyage)
