(ns reframetodos.functions)

(def todos (atom []))

(def id 100)

(defn inc-id [cnt]
  (inc cnt))

(defn add-todo [text]
  (hash-map :id (inc-id id) :task text :active true))

(defn add-to-vector [item]
  (swap! todos conj item))

(def adding (do
              (add-to-vector (add-todo "Clojure"))
              (add-to-vector (add-todo "Html"))
              (add-to-vector (add-todo "JavaScript"))
              (add-to-vector (add-todo "ClojureScript"))))
