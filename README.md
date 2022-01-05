# Movie Watchlist (Server)
This is the backend server for my Watchlist application, which lets users find and save movies to watch later. Requires the watchlist-ui repo and a postgres database to have full functionality.

(Note: this is a work in progress. More features will be added over time.)

## Running the Server
(Note: this application needs a database (and ideally a user interface) to run properly. See below for instructions for running the full app.)

To try the server on its own:

1. Install Postgres (https://www.postgresql.org/).
2. Obtain a free api key from https://www.omdbapi.com/.
3. Clone this repository. (For instructions, see https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
4. From the directory you cloned into, run the following command (replacing `<your API key goes here>` with your api key):
   ```
   ./mvnw  spring-boot:run  -Dspring-boot.run.arguments="--API_KEY=<your API key goes here>"
   ```

## Running the whole Watchlist application
The easiest way to try the full application is by using Docker.

Requirements:
- Docker install (https://docs.docker.com/get-docker/).
- A free api key from  https://www.omdbapi.com/. (You can run the application without this step, but you won't be able to search for movies.)

Installation Steps:
1. Create a folder on your computer called `watchlist` (or anything you like) and clone this repository into there. (For instructions, see https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
2. From the `watchlist` folder, clone the Movie Watchlist (Server) repository from my GitHub (https://github.com/fugu137/watchlist-server).
3. Create a file in the `watchlist` folder called `.env` and paste in the following:
    ```
    API_KEY=<Your key goes here>
    ```
   Replace `<Your key goes here>` with your api key.
4. Create a file in the `watchlist` folder called `docker-compose.yml` and paste in the following:
    ```
    services:

    database:
        container_name: database
        image: postgres:14.1
        ports:
        - "5444:5432"
        volumes:
        - database-data:/var/lib/postgres/data
        environment:
        - POSTGRES_USER=admin
        - POSTGRES_PASSWORD=Password123
        - POSTGRES_DB=watchlist

    api:
        build: ./watchlist-server/
        ports:
        - "8080:8080"
        environment:
        - spring.datasource.url=jdbc:postgresql://database:5432/watchlist
        - spring.datasource.username=admin
        - spring.datasource.password=Password123 
        - API_KEY=${API_KEY}
        links:
        - database
        restart: on-failure

    ui: 
        build: ./watchlist-ui
        ports: 
        - "3000:80"
        links:
        - api

    volumes:
    database-data:
    ```
   Save the file.
5. Open a terminal and navigate to the `watchlist` folder. Type:
    ```
    sudo docker-compose up
    ```
   The first time around this will take a while, but subsequent lauches will be quick.
6. Once the application has started, open a web browser and type `localhost:3000` in the address bar. Press 'enter' and you should see the application running.
7. To stop the application, open a terminal, navigate to the `watchlist` folder, then type:
    ```
    sudo docker-compose down
    ```
