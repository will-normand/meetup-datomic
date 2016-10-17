(ns meetup-datomic.meetup-api
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.format :as f]))

(def api-props (read-string (slurp "resources/api.edn")))

(defn get-url-as-json [url & [req]]
  (->> (client/get url req)
       (:body)
       (json/read-str)))

(defn get-upcoming-events []
  (get-url-as-json "https://api.meetup.com/Edinburgh-Clojurians/events?scroll=recent_past"))

(defn get-all-events [group-name]
  (-> (get-url-as-json "https://api.meetup.com/2/events"
                       {:query-params {"group_urlname" group-name
                                       "status"        "past,upcoming"
                                       "key"           (:key api-props)}})
      (get "results")))

(defn get-attendees [event]
  (get-url-as-json (str "https://api.meetup.com/Edinburgh-Clojurians/events/" event "/rsvps")))

(defn get-group [urlname]
  (get-url-as-json (str "https://api.meetup.com/" urlname)
                   {:query-params {"key" (:key api-props)}}))

(defn get-similar-groups []
  (->> (get-url-as-json "https://api.meetup.com/Edinburgh-Clojurians/similar_groups"
                        {:query-params {"key" (:key api-props)}})
       (map #(get % "urlname"))
       (map get-group)))

(defn extract-key-fields [events]
  (map #(vector (% "name") (% "yes_rsvp_count") (f/unparse (f/formatter :date) (% "time"))) events))

(defn convert-timestamp [events]
  (map #(update-in % ["time"] (fn [t] (c/from-long t))) events))
