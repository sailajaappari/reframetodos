(ns reframetodos.functions)

(def todos (atom []))

(def cnt (atom 100))

(defn inc-cnt [c]
  (let [id (swap! c inc)]
    id))

(defn create-todo [text]
  {:id (inc-cnt cnt) :task text :active true})

(defn add-to-vector [item]
  (swap! todos conj item))

(def adding (do
              (add-to-vector (create-todo "Clojure"))
              (add-to-vector (create-todo "Html"))
              (add-to-vector (create-todo "JavaScript"))
              (add-to-vector (create-todo "ClojureScript"))))
