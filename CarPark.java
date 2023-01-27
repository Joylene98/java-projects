/*
* Imports the Arraylist
*/
import java.util.ArrayList;

public class CarPark {

    /*
    * An arraylist which takes a collection of cars.
    */
    private ArrayList<Car> cars;
    /*
    * A number of cars the car park can hold
    */
    private int maxCapacity;

    public CarPark(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.cars = new ArrayList<Car>();

    }

    /*
    * The park car method takes a car object and adds the car
    * only if the number of cars in the car park is less
    * than the capacity.
    */

    public boolean parkCar(Car car) {
        if (cars.size() < maxCapacity) {
            cars.add(car);
            return true;
        } else {
            return false;
        }
    }

    /* When a car leaves the car park, it removes
    * the car object from the Arraylist and returns
    * true if the car is removed else false.
    */


    public boolean removeCar(Car car) {
        if (cars.contains(car)) {
            cars.remove(car);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the number of cars in the car park
     *
     */
    public int getNumberOfCars() {
        return cars.size();
    }

    /* Returns the number of avaliable spaces in the car park
    */
    public int getFreeSpaces() {
        return maxCapacity - cars.size();
    }


    public static class Car {

        /* The name of the car
        */
        public String car_name;

        public Car(String car_name) {
            this.car_name = car_name;
        }

        /* Prints the name of car
        */
        public void printCarName(Car car) {
            System.out.println("    " + car.car_name);
        }

    }

    public static void main(String[] args) {

        /* Creates a new Car Park object with maximum capacity of 5
        */
        CarPark carPark = new CarPark(5);

        /* Creates car objects
        */
        Car car1 = new Car("CAR-001");
        Car car2 = new Car("CAR-002");
        Car car3 = new Car("CAR-003");
        Car car4 = new Car("CAR-004");
        Car car5 = new Car("CAR-005");

        System.out.println("Parking car "  + car1.car_name + " : " + carPark.parkCar(car1) + "\n");
        System.out.println("Parking car " + car2.car_name + " : " + carPark.parkCar(car2) + "\n");
        System.out.println("Parking car " + car3.car_name + " : " + carPark.parkCar(car3) + "\n");
        System.out.println("Parking car " + car4.car_name + " : " + carPark.parkCar(car4) + "\n");
        System.out.println("Parking car " + car5.car_name + " : " + carPark.parkCar(car5) + "\n");

        /* Prints out number of cars
        */
        System.out.println("Number of cars: " + carPark.getNumberOfCars()  + "\n");

        /* Prints out number of spaces
        */
        System.out.println("Free spaces: " + carPark.getFreeSpaces()  + "\n");

        System.out.println("Removing car 2: " + carPark.removeCar(car2)  + "\n");

        System.out.println("Number of cars: " + carPark.getNumberOfCars()  + "\n");
        System.out.println("Free spaces: " + carPark.getFreeSpaces()  + "\n");


    }
}