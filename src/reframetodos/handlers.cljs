(ns reframetodos.handlers
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-handler register-sub dispatch]]
            [reframetodos.functions :as f :refer [adding]]))



(def state {:todos adding
            :all-todos nil
            :active-todos nil 
            :complete-todos nil})


(register-handler
  :initialize
  (fn
    [db _]
    (merge db state)))
  

;;subscribers
(register-sub
  :todos
  (fn [db _]
    (reaction (:todos @db))))

