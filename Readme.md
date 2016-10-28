Simple Spike with Apollo

Dependencies
------------
In order to run this application you must have the Books Project:
  https://github.com/pdincau/books

Compile and Run
---------------

    $ mvn package
    $ java -jar target/book-info-service.jar


Then you can visit:

    http://127.0.0.1:8081/books

and see all the books created, or search for a give book with:

    http://127.0.0.1:8081/books?title=yourbooktitle

Note that if you search for:

    http://127.0.0.1:8081/books?title=bookwithlatency

a delay will be added to trigger fallback response using hystrix.

Next to do
----------

1) Improve rabbitmq event handler
