(ns reframetodos.handlers
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-handler register-sub dispatch]]
            [reframetodos.functions :as f :refer [adding]]))



(def initial-state {:todos adding
                    :temp-todos adding
                    }) 

;;Handlers
(register-handler
  :initialize
  
  (fn
    [db _]
    (merge db initial-state)))

(register-handler
  :todos-to-temp
  (fn [db _]
    (assoc-in db [:temp-todos] (:todos db))))

(register-handler
  :add
  (fn [db [_ item]]
    (assoc-in db [:todos (count (:todos db))] (f/create-todo item))))

(register-handler
  :active-to-complete
  (fn [db [_ index]]
    (assoc-in db [:todos index :active] false)))

(register-handler
  :complete-to-active
  (fn [db [_ index]]
    (assoc-in db [:temp-todos index :active] true)))

(register-handler
  :delete
  (fn [db [_ id]]
    (update-in db [:temp-todos] #(into [] 
                                  (remove (fn [x] 
                                            (= (:id x) id)) 
                                     (:temp-todos db))))))

(register-handler
  :all
  (fn [db _]
    (assoc-in db [:temp-todos] (:todos db))))

(register-handler
  :active
  (fn [db _]
    (update-in db [:temp-todos] #(into [] 
                              (filter (fn [x]
                                        (= (:active x) true)) 
                                (:todos db))))))

(register-handler
  :complete
  (fn [db _]
    (update-in db [:temp-todos] #(into [] 
                              (filter (fn [x]
                                        (= (:active x) false)) 
                                (:todos db))))))
  
(register-handler
  :clear-complete
  (fn [db _]
    (update-in db [:todos] #(into [] 
                              (remove (fn [x] 
                                        (= (:active x) false)) 
                                 (:todos db))))))



;;subscribers
(register-sub
  :todos
  (fn [db _]
    (reaction (:todos @db))))

(register-sub
  :temptodos
  (fn [db _]
    (reaction (:temp-todos @db))))



