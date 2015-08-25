(ns counties.core
  (:use jsoup.soup)
  (:gen-class))

(defmacro any [& v] `(boolean (or ~@v)))
(defmacro all [& v] `(boolean (and ~@v)))

(def url "https://en.wikipedia.org/wiki/List_of_United_States_counties_and_county_equivalents")

(def names
  ($ (get! url :max-body-size 0)
     "table.wikitable" tr "td:eq(1)"
     (text)
     (map #(.replaceAll % "\\[\\d+\\]" ""))
     (map #(.replaceFirst % ",.+$" ""))
     (map #(.replaceFirst % " County$" ""))
     (map #(.replaceFirst % " Parish$" ""))
     (map #(.replaceFirst % " Borough$" ""))
     (map #(.replaceFirst % " Census Area$" ""))
     ))

;  (doseq
;    [i (remove #(any (.endsWith % " County")
;                     (.endsWith % " Parish")
;                     (.endsWith % " Borough")
;                     (.endsWith % ", City of")
;                     (.endsWith % " Census Area")) names)]
;    (println i)))

(defn -main
  "county counter"
  [& args]
  (doseq
    [i (sort-by val (frequencies names))]
    (println i)))
