(ns counties.core
  (:use jsoup.soup)
  (:use [incanter.core :only [save view]])
  (:use [incanter.charts :only [bar-chart]])
  (:gen-class))

(defmacro any [& v] `(boolean (or ~@v)))
(defmacro all [& v] `(boolean (and ~@v)))

(def url "https://en.wikipedia.org/wiki/List_of_United_States_counties_and_county_equivalents")

(defn names []
  "gets all raw county names"
  ($ (get! url :max-body-size 0)
     "table.wikitable tr td:eq(1)"
     (text)
     (map #(.replaceAll % "\\[\\d+\\]" ""))))

(defn read-all-names []
  "gets all county names with prefixes and suffixes removed"
  (->>
    (names)
    (map #(.replaceFirst % ",.+$" ""))
    (map #(.replaceFirst % " County$" ""))
    (map #(.replaceFirst % " Parish$" ""))
    (map #(.replaceFirst % " Borough$" ""))
    (map #(.replaceFirst % " Census Area$" ""))
    (frequencies)))

(defn filter-names []
  "gets county names, and removes valid names, leaving invalid names"
  (->>
    (names)
    (remove #(any (.endsWith % " County")
                  (.endsWith % " Parish")
                  (.endsWith % " Borough")
                  (.endsWith % ", City of")
                  (.endsWith % " Census Area")))))

(defn to-csv [m]
  "concats map keys and values with a comma"
  (map #(str (key %) "," (val %)) m))

(defn to-histogram [d n filename]
  (let [data (take n (reverse (sort-by val d)))
        plot (bar-chart (keys data) (vals data) :title "Most popular county names" :x-label "counties" :y-label "frequency" :vertical false)]
    (save plot filename :width 561 :height 340)
    (view plot)))

(defn print-all [coll]
  "prints every element of the collection on a new line"
  (doseq [i coll] (println i)))

(defn -main
  "county counter"
  [& args]
  (print-all (case args
               ; filter out valid names to find oddities (for testing)
               (("-f")) (filter-names)
               ; print counties with unique names
               (("-u")) (sort (keys (filter #(= 1 (val %)) (read-all-names))))
               ; generate histogram of frequencies by county
               (("-c")) (to-csv (read-all-names))
               ; print all counties and frequencies as CSV text
               (to-histogram (read-all-names) 16 "counties.png"))))
