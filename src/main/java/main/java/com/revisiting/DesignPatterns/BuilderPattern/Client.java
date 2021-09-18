package main.java.com.revisiting.DesignPatterns.BuilderPattern;

public class Client {

    public static void main(String[] args) {
        /*
        Method chaining modifies attributes of one object instances in one single statement
        the methods need to return the reference of the object in that way they can be chained.
        But it has  a problem
        * */

        Person p1 = new Person();
        p1.setName("Javier").setSurname("Armenta Garcia ").setAge(27).setHeight(1.71);
        Person p2 = new Person();
        p2.setName("Nicolas").setSurname("Armenta Garcia ").setAge(31).setHeight(1.80);

        System.out.println(p1);
        System.out.println(p2);

        /**
         * the above example works perfect by using method chaining  but the problem is when
         * we use  threads to modify the same object
         */

        /** Let's use a clas which declares to threads which alter the same object
         */


        ClientThreads clientThreads = new ClientThreads();
        clientThreads.startThreads();


        try{
            clientThreads.th1.join();
            clientThreads.th2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Person person1 = clientThreads.getPerson();
        System.out.println(person1);
        //Person{name='Nicolas', surname= null, age=31, height=1.8}


        /**
         * We can solve the previous issue by using builder pattern
         * it uses method chaining to modify attributes,
         * but creates an Inner class in to the class object subject to creation
         * this is class is known as the builder
         *      it has the same attributes of the subject class
         *      The method chaining is implemented there
         *      basically it is a duplicate from the subject class
         *
         *      it has a method  build() which  creates the instance of the subject class an returns it
         *      to the client
         *In this way two threads are using the builder instances and then
         * one thread calls build() loads the attrs into the class
         * the the other thread calls the build() method which overrides the attrs in the class
         *
         */

        Person.Builder builderP1 = new Person.Builder();
        builderP1.setName("Javier").setSurname("Armenta Garcia ").setAge(27).setHeight(1.71);

        Person.Builder builderP2 = new Person.Builder();
        builderP2.setName("Javier").setSurname("Armenta Garcia ").setAge(27).setHeight(1.71);

        Person pb1 = builderP1.build();
        Person pb2 = builderP2.build();
        System.out.println(pb1);
        System.out.println(pb2);

        //Now let's do the same by using threads

        ClientThreads clientThreadsBuilder = new ClientThreads();
        clientThreadsBuilder.startThreadsWithBuilder();


        try{
            clientThreadsBuilder.th1.join();
            clientThreadsBuilder.th2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Person person = clientThreads.getPerson();
        System.out.println(person);
        //Person{name='Nicolas', surname= null, age=31, height=1.8}


        /**
         * Builder pattern makes the Subject class immutable and consequently thread-safe.
         */
    }
}

