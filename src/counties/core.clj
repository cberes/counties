(ns counties.core
  (:use jsoup.soup)
  (:gen-class))

(defn -main
  "county counter"
  [& args]
  (println (sort-by val (frequencies
    ($ (get! "https://en.wikipedia.org/wiki/List_of_United_States_counties_and_county_equivalents")
       "table.wikitable tr td:eq(1)" (text))))))
