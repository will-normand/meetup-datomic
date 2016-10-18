# meetup-datomic

A simple Datomic project.

## Installation

1. Install [Leiningen](http://leiningen.org/)
2. Download the [Datomic Free](https://my.datomic.com/downloads/free) jar version 0.9.5404.
3. Create a lib directory here and put the jar in it.
4. Start the lein repl with `lein repl`, or open the project in your favourite IDE.
5. Log into your Meetup.com account and [copy your API key](https://secure.meetup.com/meetup_api/key/).  Copy the key as
   a string into `resources/api.edn`.

## Populating the database

To populate your local database with the latest Meetup data, run `meetup-datomic.database/populate-db` from the REPL.

## Querying data

Try some queries to find data - e.g. to find all the groups who's had a meeting with exactly 5 attendees: 

    (d/q '[:find [(pull ?g [*]) ...]
           :where
           [?e :event/attendance 5]
           [?e :event/group ?g]]
         (d/db conn))

## Things to try

### Queries

1. Find all the events this week
2. List the websites of groups with more than 50 members
3. Find all the groups which have held an event with more than 20 attendees this year.

### Transactions

1. Add a new event for Edinburgh-Clojurians
2. Try out changing the yes_rsvp_count of today's event using (d/with).  Verify that your real data has not changed,

### Testing

1. Write functions for the earlier queries.
2. Write unit tests in `core_tests.clj` which test your query functions with some example data.  Run them with 
   `lein test`