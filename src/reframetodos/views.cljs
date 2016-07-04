(ns reframetodos.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn List-Todo []
  (let [todos (subscribe [:todos])]
     (fn []
       [:div
        [:table
         (for [i (range 0 (count @todos))]
           ^{:key i}
           [:tr
            [:td (get-in @todos [i :task])]])]])))


