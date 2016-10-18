(defproject meetup-datomic "0.1.0-SNAPSHOT"
  :description "An example Datomic project"
  :url "https://www.meetup.com/Edinburgh-Clojurians/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.3.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-time "0.12.0"]
                 [com.datomic/datomic-free "0.9.5404"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
