(ns meetup-datomic.database
  (:require [datomic.api :as d]
            [clj-time.coerce :as c]
            [meetup-datomic.meetup-api :as a]))

(def db-uri "datomic:mem://hello")
(d/create-database db-uri)
(def conn (d/connect db-uri))
(d/transact conn (read-string (slurp "resources/schema.edn")))

(defn group->tx-data [group]
  {:db/id         (d/tempid :db.part/user)
   :group/name    (group "name")
   :group/urlname (group "urlname")
   :group/link    (group "link")
   :group/members (group "members")})

(defn event->tx-data [event]
  {:db/id            (d/tempid :db.part/user)
   :event/id         (event "id")
   :event/name       (event "name")
   :event/date       (c/to-date (c/from-long (event "time")))
   :event/attendance (event "yes_rsvp_count")
   :event/group      [:group/urlname (get-in event ["group" "urlname"])]})

(defn save-groups [conn groups]
  (->> (map group->tx-data groups)
       (d/transact conn)))

(defn save-events [conn events]
  (->> (map event->tx-data events)
       (d/transact conn)))

(defn populate-db [conn]
  (let [groups (a/get-similar-groups)
        events (mapcat #(a/get-all-events (% "urlname")) groups)]
    (do (save-groups conn groups)
        (save-events conn events))))