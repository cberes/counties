(ns counties.core
  (:use jsoup.soup)
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
    (map #(.replaceFirst % " Census Area$" ""))))

(defn filter-names []
  "gets county names, and removes valid names, leaving invalid names"
  (->>
    (names)
    (remove #(any (.endsWith % " County")
                  (.endsWith % " Parish")
                  (.endsWith % " Borough")
                  (.endsWith % ", City of")
                  (.endsWith % " Census Area")))))

(defn csv [m]
  "concats map keys and values with a comma"
  (map #(str (key %) "," (val %)) m))

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
               (("-u")) (sort (keys (filter #(= 1 (val %)) (frequencies (read-all-names)))))
               ; print all counties and frequencies as CSV text
               (csv (sort-by val (frequencies (read-all-names)))))))
