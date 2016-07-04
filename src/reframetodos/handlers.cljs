(ns reframetodos.handlers
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-handler register-sub dispatch]]
            [reframetodos.functions :as f :refer [adding]]))



(def initial-state {:todos adding}) 

;;Handlers
(register-handler
  :initialize
  (fn
    [db _]
    (merge db initial-state)))

(register-handler
  :add
  (fn [db item]
    (f/add-to-vector (f/create-todo item))))

(register-handler
  :delete
  (fn [db id]
    (remove #(= (:id %) id) (:todos db))))

(register-handler
  :all
  (fn [db _]
    db))

(register-handler
  :active
  (fn [db _]
    (filter #(= (:active %) true) (:todos db))))

(register-handler
  :complete
  (fn [db _]
    (filter #(= (:active %) false) (:todos db))))
  

;;subscribers
(register-sub
  :todos
  (fn [db _]
    (reaction (:todos @db))))

