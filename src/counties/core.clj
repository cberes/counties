(ns counties.core
  (:use jsoup.soup)
  (:gen-class))

(defmacro any [& v] `(boolean (or ~@v)))
(defmacro all [& v] `(boolean (and ~@v)))

(def url "https://en.wikipedia.org/wiki/List_of_United_States_counties_and_county_equivalents")

(defn names []
  ($ (get! url :max-body-size 0)
     "table.wikitable tr td:eq(1)"
     (text)
     (map #(.replaceAll % "\\[\\d+\\]" ""))))

(defn read-all-names []
  (->>
    (names)
    (map #(.replaceFirst % ",.+$" ""))
    (map #(.replaceFirst % " County$" ""))
    (map #(.replaceFirst % " Parish$" ""))
    (map #(.replaceFirst % " Borough$" ""))
    (map #(.replaceFirst % " Census Area$" ""))))

(defn filter-names []
  (->>
    (names)
    (remove #(any (.endsWith % " County")
                  (.endsWith % " Parish")
                  (.endsWith % " Borough")
                  (.endsWith % ", City of")
                  (.endsWith % " Census Area")))))

(defn print-all [coll]
  (doseq [i coll] (println i)))

(defn -main
  "county counter"
  [& args]
  (print-all (if (= 0 (count args))
               (sort-by val (frequencies (read-all-names)))
               (filter-names))))
